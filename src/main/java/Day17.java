import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Day17 {
    public static void main(String[] args) {
        System.out.println("Part 1 and 2: MaxY = " + Day17.sweepFindMaxY(day17Input));
        System.out.println("  Starting Point: " + Day17.maxYPoint);
        System.out.println("      Total Hits: " + Day17.getHitCount());
        /*
        Part 1 and 2: MaxY = 7750
          Starting Point: (17, 124)
              Total Hits: 4120
         */
    }

    static Point maxYPoint = null;
    static int maxMaxY = 0;
    static HashSet<Point> hitSet = new HashSet<>();
    public static final String day17Input = "target area: x=138..184, y=-125..-71";

    public static void resetStatic() {
        maxYPoint = null;
        maxMaxY = 0;
        hitSet = new HashSet<>();
    }
    public static int getHitCount() {
        return hitSet.size();
    }

    public static int sweepFindMaxY(String input) {
        resetStatic();
        Day17 runner = new Day17(input);
        int xOmega = runner.targetXMax;
        int yOmega = runner.targetYMin;
        for(int i = 1; i <= xOmega; i++) {
           for(int j = yOmega; j <= Math.abs(yOmega); j++) {
               runner.fire(i, j);
           }
           System.out.print(".");
           if(i%20==0) { System.out.println(); }
        }
        System.out.println();
        return maxMaxY;
    }

    public static int findMaxY(String input) {
        return findMaxY(input, 1, 1);
    }
    public static int findMaxY(String input, int dx, int dy) {
        int x=0, y=0, maxY;
        ArrayList<Day17> maxYs = new ArrayList<>();
        Day17 day17 = new Day17(input);
        boolean keepLooking = true;
        while(keepLooking) {
            boolean isHit = day17.fire(x+=dx, y+=dy);
            if(!isHit) {
                if(day17.isBeyond()) {
                    keepLooking = false;
                }
            } else {
                keepLooking = false;
            }
        }
        if(day17.isBeyond()) { return -1; }
        maxYs.add(day17);
        maxY = day17.getMaxY();
//        System.out.println("Added first hit: " + day17.getStartingPoint() + " maxY: " + maxY);
        boolean maxUpdated = true;
        while(maxUpdated) {
            maxUpdated = false;
            int newMaxY = maxY;
            ArrayList<Day17> newMaxYs = new ArrayList<>();
            for(Day17 d : maxYs) {
                Point startingPoint = d.getStartingPoint();
                Point[] adjacentPoints = new Point[] {
                        new Point(startingPoint.x-1, startingPoint.y-1),
                        new Point(startingPoint.x, startingPoint.y-1),
                        new Point(startingPoint.x+1, startingPoint.y-1),
                        new Point(startingPoint.x-1, startingPoint.y),
                        new Point(startingPoint.x+1, startingPoint.y),
                        new Point(startingPoint.x-1, startingPoint.y+1),
                        new Point(startingPoint.x, startingPoint.y),
                        new Point(startingPoint.x+1, startingPoint.y+1),
                };
                for(Point p : adjacentPoints) {
                    Day17 adjacent = new Day17(input);
                    boolean isHit = adjacent.fire(p);
                    if(isHit && adjacent.getMaxY() >= maxY) {
                        newMaxYs.add(adjacent);
//                        System.out.println("Added adjacent: " + adjacent.getStartingPoint() + " maxY: " + adjacent.getMaxY());
                    }
                }
            }
            for(Day17 d : newMaxYs) {
                newMaxY = Integer.max(newMaxY, d.getMaxY());
            }
//            System.out.println("newMaxY: " + newMaxY);
            if(newMaxY > maxY) {
//                System.out.println("Found new maxY: " + newMaxY);
                maxUpdated = true;
                for(Day17 d : newMaxYs.toArray(new Day17[0])) {
                    if(d.getMaxY() != newMaxY) {
                        newMaxYs.remove(d);
                    }
                }
                maxYs = newMaxYs;
                maxY = newMaxY;
            }
        }
        return maxY;
    }

    int targetXMin, targetXMax, targetYMin, targetYMax;
    int dx, dy;
    int maxY;
    boolean isBeyond;
    ArrayList<Point> points;

    public Day17(String input) {
        String[] words = input.split(" ");
        String[] temp = words[2].split("[.=,]");
        targetXMin = Integer.parseInt(temp[1]);
        targetXMax = Integer.parseInt(temp[3]);
        temp = words[3].split("[.=,]");
        targetYMin = Integer.parseInt(temp[1]);
        targetYMax = Integer.parseInt(temp[3]);
    }
    public boolean fire(int x, int y) {
        return fire(new Point(x, y));
    }
    public boolean fire(Point startingPoint) {
        dx = startingPoint.x;
        dy = startingPoint.y;
        maxY = Integer.max(0, startingPoint.y);
        isBeyond = false;
        points = new ArrayList<>();
        points.add(startingPoint);
//        System.out.println("Fire: " + startingPoint);
        Point p = startingPoint;
        PointState state = getPointState(p);
        if(state == PointState.MISS) { isBeyond = true; return false; }
        if(state == PointState.HIT) { handleHit(); return true;}
//        System.out.println(p + state.toString());
        while(state == PointState.APPROACH) {
            p = getNextPoint(p);
            maxY = Integer.max(maxY, p.y);
            points.add(p);
            state = getPointState(p);
//            System.out.println(p + state.toString());
            if(getPointState(p) == PointState.MISS) { return false; }
            if(getPointState(p) == PointState.HIT) { handleHit(); return true;
            }
        }
        return false;
    }
    public int getStep() { return (points != null ? points.size() : 0); }
    public Point getPoint() { return points.get(points.size() - 1); }
    public Point getStartingPoint() { return points.get(0); }
    public int getMaxY() { return maxY; }
    public boolean isBeyond() { return isBeyond; }
    void handleHit() {
        if(Day17.maxYPoint == null || this.getMaxY() > maxMaxY) {
            maxYPoint = getStartingPoint();
            maxMaxY = getMaxY();
//            System.out.println("New maxY: " + this.getMaxY() + " at " + maxYPoint);
        }
    }
    Point getNextPoint(Point input) {
        if(dx > 0) { dx--; }
        dy--;
        return new Point(input.x + dx, input.y + dy);
    }
    PointState getPointState(Point p) {
        if(p.x > targetXMax || p.y < targetYMin) {
            return PointState.MISS;
        } else if(p.x >= targetXMin && p.y <= targetYMax) {
//            System.out.print("Adding run for " + getStartingPoint());
            boolean added = hitSet.add(this.getStartingPoint());
//            if(added) { System.out.println(": Done (" + hitSet.size() + ")"); }
//            else { System.out.println(": Duplicate (" + hitSet.size() + ")"); }
            return PointState.HIT;
        } else {
            return PointState.APPROACH;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day17 day17 = (Day17) o;
        return targetXMin == day17.targetXMin
                && targetXMax == day17.targetXMax
                && targetYMin == day17.targetYMin
                && targetYMax == day17.targetYMax
                && dx == day17.dx && dy == day17.dy
                && maxY == day17.maxY
                && isBeyond == day17.isBeyond
                && points.equals(day17.points)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetXMin, targetXMax, targetYMin, targetYMax, dx, dy, maxY, isBeyond, points);
    }

    public enum PointState {
        APPROACH("A"),
        HIT("H"),
        MISS("M");
        private final String state;
        PointState(String state) { this.state = state; }
        @Override
        public String toString() { return state; }
    }

    public static class Point {
        int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }
    }
}
