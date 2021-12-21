import java.util.ArrayList;
import java.util.Objects;

public class Day17 {
    public static void main(String[] args) {
        System.out.println("Part 1: MaxY = " + Day17.findMaxY(day17Input, 1, 4));
        System.out.println("  Starting Point: " + Day17.maxYDay17.getStartingPoint());
        System.out.println("    Ending Point: " + Day17.maxYDay17.getPoint());
        /*
        Part 1: MaxY = 7750
          Starting Point: (18, 124)
            Ending Point: (171, -125)
        Brute force:
        Part 1: MaxY = 7750
          Starting Point: (17, 124)
            Ending Point: (153, -125)
         */
//        Day17 bruteForce = null;
//        for(int x = 1; x < 100; x++) {
//            for(int y = 1; y < 200; y++) {
//                Day17 d = new Day17(day17Input);
//                if(d.fire(17, y)) {
//                    if(bruteForce == null || d.getMaxY() > bruteForce.getMaxY()) {
//                        bruteForce = d;
//                    }
//                }
//            }
//        }
//        System.out.println("Brute force:");
//        System.out.println("Part 1: MaxY = " + bruteForce.getMaxY());
//        System.out.println("  Starting Point: " + bruteForce.getStartingPoint());
//        System.out.println("    Ending Point: " + bruteForce.getPoint());
    }
    public static Day17 maxYDay17;
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
        System.out.println("Added first hit: " + day17.getStartingPoint() + " maxY: " + maxY);
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
                        System.out.println("Added adjacent: " + adjacent.getStartingPoint() + " maxY: " + adjacent.getMaxY());
                    }
                }
            }
            for(Day17 d : newMaxYs) {
                newMaxY = Integer.max(newMaxY, d.getMaxY());
            }
            System.out.println("newMaxY: " + newMaxY);
            if(newMaxY > maxY) {
                System.out.println("Found new maxY: " + newMaxY);
                maxUpdated = true;
                for(Day17 d : newMaxYs.toArray(new Day17[0])) {
                    if(d.getMaxY() != newMaxY) {
                        newMaxYs.remove(d);
                    }
                }
                maxYs = newMaxYs;
                maxY = newMaxY;
                maxYDay17 = newMaxYs.get(0);
            }
        }
        System.out.println("to here max = " + maxY);
        return maxY;
    }
    public static final String day17Input = "target area: x=138..184, y=-125..-71";

    int targetXMin, targetXMax, targetYMin, targetYMax;
    int x0, y0;
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
        x0 = startingPoint.x;
        y0 = startingPoint.y;
        maxY = startingPoint.y;
        isBeyond = false;
        if(startingPoint.x > targetXMax) {
            isBeyond = true;
            return false;
        }
        points = new ArrayList<>();
        points.add(startingPoint);
        System.out.println("Fire: " + startingPoint);
        Point p = startingPoint;
        PointState state = getPointState(p);
//        System.out.println(p + state.toString());
        while(state == PointState.APPROACH) {
            p = getNextPoint(p);
            maxY = Integer.max(maxY, p.y);
            points.add(p);
            state = getPointState(p);
//            System.out.println(p + state.toString());
            if(getPointState(p) == PointState.HIT) { return true; }
            if(getPointState(p) == PointState.MISS) { return false; }
        }
        return false;
    }
    public int getStep() { return (points != null ? points.size() : 0); }
    public Point getPoint() { return points.get(points.size() - 1); }
    public Point getStartingPoint() { return points.get(0); }
    public int getMaxY() { return maxY; }
    public boolean isBeyond() { return isBeyond; }
    Point getNextPoint(Point input) {
        if(x0 > 0) { x0--; }
        y0--;
        return new Point(input.x + x0, input.y + y0);
    }
    PointState getPointState(Point p) {
        if(p.x > targetXMax || p.y < targetYMin) {
            return PointState.MISS;
        } else if(p.x >= targetXMin && p.y <= targetYMax) {
            return PointState.HIT;
        } else {
            return PointState.APPROACH;
        }
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
