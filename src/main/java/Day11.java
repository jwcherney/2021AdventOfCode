public class Day11 {
    public static void main(String[] args) {
        Day11 day11 = new Day11(part1Input);
        do {
            day11.tick();
        } while(day11.getStep() < 100);
        System.out.println("Step " + day11.getStep() + ": FlashCount = " + day11.getFlashCount());
        /*
        Part 1:
        Step 100: FlashCount = 1571
        Part 2:
        Step 387: ALL FLASHED!
         */
        do {
            day11.tick();
        } while(!day11.didAllFlash());
        System.out.println("Step " + day11.getStep() + ": ALL FLASHED!");
    }

    Octopus[][] octopi;
    int step = 0;
    int flashCount = 0;
    boolean flashOccurred = false;
    boolean didAllFlash = false;
    public Day11(String input) {
        String[] lines = input.trim().split("\n");
        octopi = new Octopus[lines.length][];
        for(int i = 0; i < lines.length; i++) {
            String[] octs = lines[i].trim().split("");
            octopi[i] = new Octopus[octs.length];
            for(int j = 0; j < octs.length; j++) {
                octopi[i][j] = new Octopus(i, j, Integer.parseInt(octs[j]), this);
            }
        }
    }
    public int getStep() {
        return step;
    }
    public int getFlashCount() {
        return flashCount;
    }
    public boolean didAllFlash() {
        didAllFlash = true;
        doAction(DID_ALL_FLASH);
        return didAllFlash;
    }
    public void tick() {
        startTick();
        do {
            flashOccurred = false;
            checkFlash();
        } while(flashOccurred);
        endTick();
        step++;
    }
    public String toString() {
        String returnValue = "";
        for(int i = 0; i < octopi.length; i++) {
            for(int j = 0; j < octopi[i].length; j++) {
                returnValue += octopi[i][j].toString();
            }
            if(i < octopi.length - 1) {
                returnValue += "\n";
            }
        }
        return returnValue;
    }
    public void handleFlash(int x, int y) {
        if(x > 0) { octopi[x-1][y].startTick(); }
        if(x < octopi.length - 1) { octopi[x+1][y].startTick(); }
        if(y > 0) { octopi[x][y-1].startTick(); }
        if(y < octopi[x].length - 1) {octopi[x][y+1].startTick(); }
        if((x > 0) && (y > 0)) { octopi[x-1][y-1].startTick(); }
        if((x > 0) && (y < octopi[x].length - 1)) { octopi[x-1][y+1].startTick(); }
        if((x < octopi.length - 1) && (y > 0)) { octopi[x+1][y-1].startTick(); }
        if((x < octopi.length - 1) && (y < octopi[x].length - 1)) { octopi[x+1][y+1].startTick(); }
    }
    void startTick() {
        doAction(START_TICK);
    }
    void endTick() {
        doAction(END_TICK);
    }
    void checkFlash() {
        doAction(CHECK_FLASH);
    }
    void doAction(int action) {
        for(int i = 0; i < octopi.length; i++) {
            for(int j = 0; j < octopi[i].length; j++) {
                switch(action) {
                    case START_TICK:
                        octopi[i][j].startTick();
                        break;
                    case END_TICK:
                        octopi[i][j].endTick();
                        break;
                    case CHECK_FLASH:
                        if(octopi[i][j].canFlash()) {
                            octopi[i][j].flash();
                            flashOccurred = true;
                            flashCount++;
                        }
                        break;
                    case DID_ALL_FLASH:
                        if(didAllFlash) {
                            if(!octopi[i][j].didFlash()) {
                                didAllFlash = false;
                            }
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unknown Action: " + action);
                }
            }
        }
    }
    static final int START_TICK = 0;
    static final int END_TICK = 1;
    static final int CHECK_FLASH = 2;
    static final int DID_ALL_FLASH = 3;

    public static class Octopus {
        int x, y, value;
        boolean hasFlashed;
        Day11 flashHandler;
        public Octopus(int x, int y, int value, Day11 flashHandler) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.flashHandler = flashHandler;
            hasFlashed = false;
        }
        public boolean canFlash() {
            return (!hasFlashed && value > FLASHPOINT);
        }
        public void flash() {
            if(flashHandler != null) {
                flashHandler.handleFlash(x, y);
            }
            hasFlashed = true;
        }
        public boolean didFlash() {
            return value == 0;
        }
        public void startTick() {
            if(!hasFlashed) {
                value += 1;
            }
        }
        public void endTick() {
            if(value > FLASHPOINT) {
                value = 0;
            }
            hasFlashed = false;
        }
        public String toString() {
            return String.valueOf(value);
        }
        static final int FLASHPOINT = 9;
    }

    static String part1Input = "4585612331\n" +
            "5863566433\n" +
            "6714418611\n" +
            "1746467322\n" +
            "6161775644\n" +
            "6581631662\n" +
            "1247161817\n" +
            "8312615113\n" +
            "6751466142\n" +
            "1161847732";
}
