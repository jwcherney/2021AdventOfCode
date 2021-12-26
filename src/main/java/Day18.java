import java.util.ArrayList;
import java.util.Stack;

public class Day18 {

    SNumber number = null;

    public Day18(String input) {
        String[] lines = input.split("\n");
        ArrayList<SNumber> sNumbers = new ArrayList<>();
        for(String line: lines) {
            if(number == null) {
                number = SNumber.factory(0, line);
            } else {
                number = SNumber.add(number, SNumber.factory(0, line));
            }
        }
    }

    public String toString() {
        return number.toString();
    }

    public int magnitude() {
        return number.magnitude();
    }

    public static class SNumber {
        int depth;
        boolean isLeftGroup, isRightGroup, isSelfLeft;
        boolean gotComma, didExplode, didSplit;
        int left, right;
        SNumber sNumberLeft, sNumberRight, parent;
        public SNumber(int depth) { this.depth = depth; }
        public String toString() {
            if(isLeftGroup && isRightGroup) {
                return "[" + sNumberLeft.toString() + "," + sNumberRight.toString() + "]";
            } else if(!isLeftGroup && !isRightGroup) {
                return "[" + left + "," + right + "]";
            } else if(isLeftGroup) {
                return "[" + sNumberLeft.toString() + "," + right + "]";
            } else if(isRightGroup) {
                return "[" + left + "," + sNumberRight.toString() + "]";
            } else {
                throw new RuntimeException("Invalid configuration for a SNumber");
            }
        }
        public int magnitude() {
            int returnValue = 0;
            if(isLeftGroup) {
                returnValue += 3*sNumberLeft.magnitude();
            } else {
                returnValue += 3*left;
            }
            if(isRightGroup) {
                returnValue += 2*sNumberRight.magnitude();
            } else {
                returnValue += 2*right;
            }
            return returnValue;
        }

        public static SNumber factory(int initialDepth, String input) {
            int depth = initialDepth;
            Stack<SNumber> sNumbers = new Stack<>();
            SNumber snumber;
            for(String c : input.split("")) {
                switch(c) {
                    case "[":
                        snumber = new SNumber(depth);
                        sNumbers.push(snumber);
                        depth++;
                        break;
                    case ",":
                        sNumbers.peek().gotComma = true;
                        break;
                    case "]":
                        depth--;
                        if(depth == initialDepth) {
                            return sNumbers.pop();
                        } else {
                            snumber = sNumbers.pop();
                            snumber.parent = sNumbers.peek();
                            if(sNumbers.peek().gotComma) {
                                sNumbers.peek().sNumberRight = snumber;
                                sNumbers.peek().isRightGroup = true;
                                snumber.isSelfLeft = false;
                            } else {
                                sNumbers.peek().sNumberLeft = snumber;
                                sNumbers.peek().isLeftGroup = true;
                                snumber.isSelfLeft = true;
                            }
                        }
                        break;
                    default:
                        if(sNumbers.peek().gotComma) {
                            sNumbers.peek().right = Integer.parseInt(c);
                        } else {
                            sNumbers.peek().left = Integer.parseInt(c);
                        }
                        break;
                }
            }
            return null;
        }
        public static SNumber add(SNumber left, SNumber right) {
            SNumber n = new SNumber(0);
            n.sNumberLeft = left.incrementDepth();
            n.isLeftGroup = true;
            n.sNumberRight = right.incrementDepth();
            n.isRightGroup = true;
            left.parent = n;
            right.parent = n;
            left.isSelfLeft = true;
            right.isSelfLeft = false;
            return reduce(n);
        }
        public static SNumber reduce(SNumber n) {
            boolean isReduceDone = false;
            SNumber initialValue = n, explodeValue = null, splitValue = null;
            while(!isReduceDone) {
                explodeValue = initialValue.explode();
                if(initialValue.equals(explodeValue)) {
                    splitValue = initialValue.split();
                    if(initialValue.equals(splitValue)) {
                        isReduceDone = true;
                    } else {
                        initialValue = splitValue;
                    }
                } else {
                    initialValue = explodeValue;
                }
            }
            return initialValue;
        }
        public SNumber incrementDepth() {
            depth++;
            if(isLeftGroup) {
                sNumberLeft.incrementDepth();
            }
            if(isRightGroup) {
                sNumberRight.incrementDepth();
            }
            return this;
        }
        void setDidExplodeUp(boolean value) {
            didExplode = value;
            if(parent != null) {
                parent.setDidExplodeUp(value);
            }
        }
        void setDidExplodeDown(boolean value) {
            didExplode = value;
            if(isLeftGroup) {
                sNumberLeft.setDidExplodeDown(value);
            }
            if(isRightGroup) {
                sNumberRight.setDidExplodeDown(value);
            }
        }
        void setDidSplitUp(boolean value) {
            didSplit = value;
            if(parent != null) {
                parent.setDidSplitUp(value);
            }
        }
        void setDidSplitDown(boolean value) {
            didSplit = value;
            if(isLeftGroup) {
                sNumberLeft.setDidSplitDown(value);
            }
            if(isRightGroup) {
                sNumberRight.setDidSplitDown(value);
            }
        }
        public SNumber explode() {
            if(depth == 0) {
                setDidExplodeDown(false);
            }
            if(depth == 3) {
                SNumber temp = null;
                if(isLeftGroup) {
                    temp = sNumberLeft;
                    sNumberLeft = null;
                    left = 0;
                    isLeftGroup = false;
                    setDidExplodeUp(true);
                    parent.handleExplodeLeftUp(temp.left);
                    handleExplodeRightDown(temp.right);
                } else if(isRightGroup) {
                    temp = sNumberRight;
                    sNumberRight = null;
                    right = 0;
                    isRightGroup = false;
                    setDidExplodeUp(true);
                    handleExplodeLeftDown(temp.left);
                    parent.handleExplodeRightUp(temp.right);
                }
            } else {
                if(!didExplode && isLeftGroup) {
                    sNumberLeft.explode();
                }
                if(!didExplode && isRightGroup) {
                    sNumberRight.explode();
                }
            }
            return this;
        }
        public void handleExplodeLeftUp(int value) {
            if(parent != null) {
                if(isSelfLeft) {
                    parent.handleExplodeLeftUp(value);
                } else {
                    if(isLeftGroup) {
                        sNumberRight.handleExplodeRightDown(value);
                    } else {
                        left += value;
                    }
                }
            }
        }
        public void handleExplodeLeftDown(int value) {
            if(isLeftGroup) {
                sNumberLeft.handleExplodeRightDown(value);
            } else {
                left += value;
            }
        }
        public void handleExplodeRightUp(int value) {
            if(parent != null) {
                if(!isSelfLeft) {
                    parent.handleExplodeRightUp(value);
                } else {
                    if(isRightGroup) {
                        sNumberRight.handleExplodeLeftDown(value);
                    } else {
                        right += value;
                    }
                }
            }
        }
        public void handleExplodeRightDown(int value) {
            if(isRightGroup) {
                sNumberRight.handleExplodeLeftDown(value);
            } else {
                right += value;
            }
        }
        public SNumber split() {
            if(depth == 0) {
                setDidSplitDown(false);
            }
            return this;
        }
    }
}
