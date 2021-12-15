import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Day14 {
    public static void main(String[] args) {
        Day14 day14 = new Day14(part1Input);
        while(day14.getStepCount() < 10) {
            day14.step();
            System.out.print(".");
        }
        System.out.println();
        System.out.println("Part 1 output (day " + day14.getStepCount() + "): " + day14.getMostLessLeast());
        while(day14.getStepCount() < 40) {
            day14.step();
            System.out.print(".");
            if(day14.getStepCount() % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Part 2 output (day " + day14.getStepCount() + "): " + day14.getMostLessLeast());
        /*
        Part 1 output (day 10): 3058
        Part 2 output (day 40): 3447389044530
         */
    }

    String input;
    String data;
    ArrayList<Polymer> polymers = new ArrayList<>();
    ArrayList<Counter> counters = new ArrayList<>();
    HashMap<Polymer, Counter> polymerMap = new HashMap<>();
    int stepCount = 0;
    Day14(String input) {
        this.input = input;
        processInput();
    }
    public String getData() {
        return data;
    }
    public BigInteger getLength() {
        BigInteger length = BigInteger.ZERO;
        for(Counter c : counters) {
            length = length.add(c.getCount());
        }
        return length;
    }
    public int getStepCount() {
        return stepCount;
    }
    void processInput() {
        String[] lines = input.split("\n");
        data = lines[0];
        for(int i = 2; i < lines.length; i++) {
            String[] elements = lines[i].split(" -> ");
            Polymer polymer = new Polymer(elements[0], elements[1]);
            if(!polymers.contains(polymer)) {
                polymers.add(polymer);
            }
            Counter counter = new Counter(polymer.getFirstChar());
            if(!counters.contains(counter)) {
                counters.add(counter);
            }
        }
        for(int i = 0; i < data.length() - 1; i++) {
            String c0 = String.valueOf(data.charAt(i));
            String c1 = String.valueOf(data.charAt(i+1));
            Polymer polymer = new Polymer(c0+c1, "");
            polymer = polymers.get(polymers.indexOf(polymer));
            updateMap(polymerMap, polymer);
            incrementCountInMap(polymerMap, polymer);
            counters.get(counters.indexOf(new Counter(polymer.getFirstChar()))).increment();
        }
        String end = String.valueOf(data.charAt(data.length()-1));
        counters.get(counters.indexOf(new Counter(end))).increment();
    }
    void updateMap(HashMap<Polymer, Counter> map, Polymer polymer) {
        Counter counter = new Counter(polymer.getFirstChar());
        if(!map.containsKey(polymer)) {
            map.put(polymer, counter);
        }
    }
    void incrementCountInMap(HashMap<Polymer, Counter> map, Polymer polymer) {
        updateCountInMap(map, polymer, BigInteger.ONE);
    }
    void updateCountInMap(HashMap<Polymer, Counter> map, Polymer polymer, BigInteger value) {
        map.get(polymer).add(value);
    }
    public int step() {
        HashMap<Polymer, Counter> newPolymerMap = new HashMap<>();
        for(Polymer polymer : polymerMap.keySet()) {
            Counter counter = polymerMap.get(polymer);
            String insert = polymer.getInsert();
            counters.get(counters.indexOf(new Counter(insert))).add(counter.getCount());
            Polymer firstPolymer = polymers.get(polymers.indexOf(new Polymer(polymer.getFirstLink(), "")));
            Polymer secondPolymer = polymers.get(polymers.indexOf(new Polymer(polymer.getSecondLink(), "")));
            updateMap(newPolymerMap, firstPolymer);
            updateCountInMap(newPolymerMap, firstPolymer, counter.getCount());
            updateMap(newPolymerMap, secondPolymer);
            updateCountInMap(newPolymerMap, secondPolymer, counter.getCount());
        }
        stepCount++;
        polymerMap = newPolymerMap;
        return getStepCount();
    }
    public BigInteger getMostLessLeast() {
        BigInteger most = BigInteger.ZERO, least = new BigInteger("-1");
        for(Counter c : counters) {
            BigInteger count = c.getCount();
            most = count.max(most);
            if(least.equals(new BigInteger("-1"))) {
                least = count;
            } else {
                least = count.min(least);
            }
        }
        return most.subtract(least);
    }

    public static class Polymer {
        String code, insert;
        Polymer(String code, String insert) {
            this.code = code;
            this.insert = insert;
        }
        public String getFirstChar() {
            return String.valueOf(code.charAt(0));
        }
        public String getInsert() {
            return insert;
        }
        public String getFirstLink() {
            return getFirstChar() + insert;
        }
        public String getSecondLink() {
            return insert + String.valueOf(code.charAt(1));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Polymer polymer = (Polymer) o;
            return code.equals(polymer.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }
    }
    public static class Counter {
        String code;
        BigInteger count;
        Counter(String code) {
            this.code = code;
            count = BigInteger.ZERO;
        }
        public String getCode() {
            return code;
        }
        public BigInteger getCount() {
            return count;
        }
        public Counter increment() {
            return add(BigInteger.ONE);
        }
        public Counter add(BigInteger value) {
            count = count.add(value);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Counter counter = (Counter) o;
            return code.equals(counter.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }
    }

    static final String part1Input = "SVCHKVFKCSHVFNBKKPOC\n" +
            "\n" +
            "NC -> H\n" +
            "PK -> V\n" +
            "SO -> C\n" +
            "PH -> F\n" +
            "FP -> N\n" +
            "PN -> B\n" +
            "NP -> V\n" +
            "NK -> S\n" +
            "FV -> P\n" +
            "SB -> S\n" +
            "VN -> F\n" +
            "SC -> H\n" +
            "OB -> F\n" +
            "ON -> O\n" +
            "HN -> V\n" +
            "HC -> F\n" +
            "SN -> K\n" +
            "CB -> H\n" +
            "OP -> K\n" +
            "HP -> H\n" +
            "KS -> S\n" +
            "BC -> S\n" +
            "VB -> V\n" +
            "FC -> B\n" +
            "BH -> C\n" +
            "HH -> O\n" +
            "KH -> S\n" +
            "VF -> F\n" +
            "PF -> P\n" +
            "VV -> F\n" +
            "PP -> V\n" +
            "BO -> H\n" +
            "BF -> B\n" +
            "PS -> K\n" +
            "FO -> O\n" +
            "KF -> O\n" +
            "FN -> H\n" +
            "CK -> B\n" +
            "VP -> V\n" +
            "HK -> F\n" +
            "OV -> P\n" +
            "CS -> V\n" +
            "FF -> P\n" +
            "OH -> N\n" +
            "VS -> H\n" +
            "VO -> O\n" +
            "CP -> O\n" +
            "KC -> V\n" +
            "KV -> P\n" +
            "BK -> B\n" +
            "VK -> S\n" +
            "NF -> V\n" +
            "OO -> V\n" +
            "FH -> H\n" +
            "CN -> O\n" +
            "SP -> B\n" +
            "KN -> V\n" +
            "OF -> H\n" +
            "NV -> H\n" +
            "FK -> B\n" +
            "PV -> N\n" +
            "NB -> B\n" +
            "KK -> P\n" +
            "VH -> P\n" +
            "CC -> B\n" +
            "HV -> V\n" +
            "OC -> H\n" +
            "PO -> V\n" +
            "NO -> O\n" +
            "BP -> C\n" +
            "NH -> H\n" +
            "BN -> O\n" +
            "BV -> S\n" +
            "CV -> B\n" +
            "HS -> O\n" +
            "NN -> S\n" +
            "NS -> P\n" +
            "KB -> F\n" +
            "CO -> H\n" +
            "HO -> P\n" +
            "PB -> B\n" +
            "BS -> P\n" +
            "SH -> H\n" +
            "FS -> V\n" +
            "SF -> O\n" +
            "OK -> F\n" +
            "KP -> S\n" +
            "BB -> C\n" +
            "PC -> B\n" +
            "OS -> C\n" +
            "SV -> N\n" +
            "SK -> K\n" +
            "KO -> C\n" +
            "SS -> V\n" +
            "CF -> C\n" +
            "HB -> K\n" +
            "VC -> B\n" +
            "CH -> P\n" +
            "HF -> K\n" +
            "FB -> V";
}
