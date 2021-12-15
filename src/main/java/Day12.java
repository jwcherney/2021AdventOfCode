import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) {
        Day12 day12 = new Day12(part1Input);
        System.out.println("Part 1 Output: " + day12.getPathCount());
        day12.reset();
        System.out.println("Part 2 Output: " + day12.getPathCount(true));
        /*
        Part 1 Output: 3576
        Part 2 Output: 84271
         */
    }

    String input;
    List<String> completePaths;
    List<String> buildingPaths;
    ArrayList<PathSegment> pathSegments;
    Day12(String input) {
        this.input = input;
    }
    public void reset() {
        completePaths.clear();
    }
    public int getPathCount() {
        return getPathCount(false);
    }
    public int getPathCount(boolean repeatOneSmallCave) {
        if(completePaths == null || completePaths.size() == 0) {
            generatePaths(repeatOneSmallCave);
        }
        return completePaths.size();
    }

    void generatePaths(boolean repeatOneSmallCave) {
        // Initialize
        completePaths = new ArrayList<>();
        buildingPaths = new ArrayList<>();
        pathSegments = new ArrayList<>();
        // Process input, make list of paths
        for(String line : input.split("\n")) {
            String[] endpoints = line.split("-");
                pathSegments.add(new PathSegment(endpoints[0], endpoints[1]));
//                System.out.println(pathSegments.get(pathSegments.size() - 1).toFullSegmentString());
                pathSegments.add(new PathSegment(endpoints[1], endpoints[0]));
//                System.out.println(pathSegments.get(pathSegments.size() - 1).toFullSegmentString());
        }
        // Initialize buildingPaths array
        for(PathSegment pathSegment : pathSegments) {
            if(pathSegment.startsWith(START)) {
                buildingPaths.add(pathSegment.toFullSegmentString());
            }
        }
//        System.out.println("buildingPaths size: " + buildingPaths.size());
//        System.out.println("completePaths size: " + completePaths.size());

        // Iterate over possible paths
        while(buildingPaths.size() > 0) {
            buildingPaths = explorePathSegments(buildingPaths, repeatOneSmallCave);
//            System.out.println("buildingPaths size: " + buildingPaths.size());
//            System.out.println("completePaths size: " + completePaths.size());
        }
    }
    List<String> explorePathSegments(List<String> inputPaths, boolean repeatOneSmallCave) {
        ArrayList<String> outputPaths = new ArrayList<>();
        for(String inputPath : inputPaths) {
            int lastCommaIndex = inputPath.lastIndexOf(COMMA);
            String lastCave = inputPath.substring(lastCommaIndex+1);
            List<PathSegment> nextSegments = pathSegments.stream()
                    .filter(pathSegment -> pathSegment.startsWith(lastCave))
                    .filter(pathSegment -> !pathSegment.endsWith(START))
                    .collect(Collectors.toList());
            for(PathSegment nextSegment : nextSegments) {
                if(nextSegment.endsWith(END)) {
                    // Reached an end
                    String newPath = inputPath + nextSegment.toShortSegmentString();
                    if(!completePaths.contains(newPath)) {
                        completePaths.add(newPath);
                    }
                    continue;
                }
                if(repeatOneSmallCave) {
                    // Can repeat one small cave
                    if(nextSegment.toShortSegmentString().toLowerCase().equals(nextSegment.toShortSegmentString())) {
                        // Found a small cave
                        if(inputPath.contains(nextSegment.toShortSegmentString()) && pathHasRepeatedSmallCaves(inputPath)) {
                            // But have already repeated a small cave
                            continue;
                        }
                    }
                    // Whether small cave or big cave, it's ok to add it at this point
                    String newPath = inputPath + nextSegment.toShortSegmentString();
                    if(!outputPaths.contains(newPath)) {
                        outputPaths.add(newPath);
                    }
                } else {
                    if(nextSegment.toShortSegmentString().toLowerCase().equals(nextSegment.toShortSegmentString())) {
                        // Found a small cave
                        if(inputPath.contains(nextSegment.toShortSegmentString())) {
                            // Already has this small cave. Skip to next segment
                            continue;
                        }
                    }
                    // Whether small cave or big cave, it's ok to add it at this point
                    String newPath = inputPath + nextSegment.toShortSegmentString();
                    if(!outputPaths.contains(newPath)) {
                        outputPaths.add(newPath);
                    }
                }
            }
        }
        return outputPaths;
    }
    public String getCompletePaths() {
        StringBuilder returnValue = new StringBuilder();
        for(String path : completePaths.stream().sorted().collect(Collectors.toList())) {
            returnValue.append(path.trim()).append("\n");
        }
        return returnValue.toString().trim();
    }
    boolean pathHasRepeatedSmallCaves(String path) {
        String[] caves = path.split(",");
        for(String cave : caves) {
            if(cave.toLowerCase().equals(cave)) {
                // Found a small cave
                if(path.indexOf(cave) != path.lastIndexOf(cave)) {
                    return true;
                }
            }
        }
        return false;
    }

    static class PathSegment {
        String from, to;
        public PathSegment(String from, String to) {
            this.from = from;
            this.to = to;
        }
        public boolean startsWith(String target) {
            return from.equals(target);
        }
        public boolean endsWith(String target) {
            return to.equals(target);
        }
        public String toFullSegmentString() {
            return from+","+to;
        }
        public String toShortSegmentString() {
            return ","+to;
        }
    }

    static final String START = "start";
    static final String END = "end";
    static final String BIGSTART = "START";
    static final String COMMA = ",";

    static final String part1Input = "ax-end\n" +
            "xq-GF\n" +
            "end-xq\n" +
            "im-wg\n" +
            "ax-ie\n" +
            "start-ws\n" +
            "ie-ws\n" +
            "CV-start\n" +
            "ng-wg\n" +
            "ng-ie\n" +
            "GF-ng\n" +
            "ng-av\n" +
            "CV-end\n" +
            "ie-GF\n" +
            "CV-ie\n" +
            "im-xq\n" +
            "start-GF\n" +
            "GF-ws\n" +
            "wg-LY\n" +
            "CV-ws\n" +
            "im-CV\n" +
            "CV-wg";
}
