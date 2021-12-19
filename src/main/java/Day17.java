import java.util.ArrayList;
import java.util.Objects;

public class Day17 {
    int targetXMin, targetXMax, targetYMin, targetYMax;
    int dx, dy, y0;
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
        dx = x;
        dy = y;
        y0 = y;
        points = new ArrayList<>();
        Point p = new Point(x, y);
        System.out.println(p.toString());
        points.add(p);
        while(getPointState(p) == PointState.APPROACH) {
            p = getNextPoint(p);
            System.out.println(p.toString());
            points.add(p);
            if(getPointState(p) == PointState.HIT) { return true; }
            if(getPointState(p) == PointState.MISS) { return false; }
        }
        return false;
    }
    public int getStep() { return (points != null ? points.size() : 0); }
    public Point getPoint() { return points.get(points.size() - 1); }
    Point getNextPoint(Point input) {
        if(dx > 0) { dx--; }
        dy--;
        return new Point(input.x + dx, input.y + dy);
    }
    PointState getPointState(Point p) {
        if(p.x > targetXMax || p.y < targetYMin) {
            return PointState.MISS;
        } else if(p.x >= targetXMin && p.x <= targetXMax
            && p.y >= targetYMin && p.y <= targetYMax) {
            return PointState.HIT;
        } else {
            return PointState.APPROACH;
        }
    }
    public enum PointState {
        APPROACH,
        HIT,
        MISS,
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
