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
        boolean isLeftGroup, isRightGroup;
        boolean gotComma;
        int left, right;
        SNumber sNumberLeft, sNumberRight;
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
                            if(sNumbers.peek().gotComma) {
                                sNumbers.peek().sNumberRight = snumber;
                                sNumbers.peek().isRightGroup = true;
                            } else {
                                sNumbers.peek().sNumberLeft = snumber;
                                sNumbers.peek().isLeftGroup = true;
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
            n.sNumberLeft = incrementDepth(left);
            n.isLeftGroup = true;
            n.sNumberRight = incrementDepth(right);
            n.isRightGroup = true;
            return n;
        }
        public static SNumber incrementDepth(SNumber n) {
            n.depth++;
            if(n.isLeftGroup) {
                incrementDepth(n.sNumberLeft);
            }
            if(n.isRightGroup) {
                incrementDepth(n.sNumberRight);
            }
            return n;
        }
    }
}
