import java.math.BigInteger;

public class Day06 {
    public static void main(String[] args) {
        Day06 day06 = new Day06(input);
        System.out.println("Count: " + day06.getCount(80));
        System.out.println("Count: " + day06.getCount(256));
    }
    /* Output
    Part 1
    353079
    Note: Originally done as ArrayList

    Part 2
    1605400130036
     */

    BigInteger[] fishAgeCount;
    int currentDay;

    public Day06(int[] input) {
        fishAgeCount = new BigInteger[]{
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO,
        };
        currentDay = 0;
        for(int i = 0; i < input.length; i++) {
            fishAgeCount[input[i]] = fishAgeCount[input[i]].add(BigInteger.ONE);
        }
//        System.out.println(printOutput());
    }

    public BigInteger getCount(int day) {
        if(currentDay > day) {
            throw new RuntimeException("Invalid day, can't look back into the past");
        }
        while(currentDay < day) {
            tick();
        }
        BigInteger count = BigInteger.ZERO;
        for(int i = 0; i < fishAgeCount.length; i++) {
            count = count.add(fishAgeCount[i]);
        }
        return count;
    }

    void tick() {
        BigInteger spawning = BigInteger.ZERO;
        for(int i = 0; i < fishAgeCount.length; i++) {
            if(i == 0) {
                spawning = fishAgeCount[i];
            } else {
                fishAgeCount[i - 1] = fishAgeCount[i];
            }
        }
        fishAgeCount[6] = fishAgeCount[6].add(spawning);
        fishAgeCount[8] = spawning;
        currentDay++;
//        System.out.println(printOutput());
    }

//    String printOutput() {
//        String output = "Day " + currentDay + ": ";
//        for(int i = 0; i < fishAgeCount.length; i++) {
//            output += fishAgeCount[i];
//            if(i < fishAgeCount.length - 1) {
//                output += ",";
//            }
//        }
//        return output;
//    }

    static int[] input = new int[] {
        4,5,3,2,3,3,2,4,2,1,2,4,5,2,2,2,4,1,1,1,5,1,1,2,5,2,1,1,4,4,5,5,1,2,1,1,5,3,5,2,4,3,2,4,5,3,2,1,4,1,3,1,2,4,1,1,4,1,4,2,5,1,4,3,5,2,4,5,4,2,2,5,1,1,2,4,1,4,4,1,1,3,1,2,3,2,5,5,1,1,5,2,4,2,2,4,1,1,1,4,2,2,3,1,2,4,5,4,5,4,2,3,1,4,1,3,1,2,3,3,2,4,3,3,3,1,4,2,3,4,2,1,5,4,2,4,4,3,2,1,5,3,1,4,1,1,5,4,2,4,2,2,4,4,4,1,4,2,4,1,1,3,5,1,5,5,1,3,2,2,3,5,3,1,1,4,4,1,3,3,3,5,1,1,2,5,5,5,2,4,1,5,1,2,1,1,1,4,3,1,5,2,3,1,3,1,4,1,3,5,4,5,1,3,4,2,1,5,1,3,4,5,5,2,1,2,1,1,1,4,3,1,4,2,3,1,3,5,1,4,5,3,1,3,3,2,2,1,5,5,4,3,2,1,5,1,3,1,3,5,1,1,2,1,1,1,5,2,1,1,3,2,1,5,5,5,1,1,5,1,4,1,5,4,2,4,5,2,4,3,2,5,4,1,1,2,4,3,2,1
    };
}
