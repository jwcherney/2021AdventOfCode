import java.util.ArrayList;
import java.util.List;

public class Day16 {
    public static void main(String[] args) {
        Day16 day16 = new Day16(day16Input);
        System.out.println("Part 1 output: Sum of all versions: " + day16.getVersionSum());
        System.out.println("Part 2 output: Value of all packets: " + day16.getValue());
        /*
        Part 1 output: Sum of all versions: 852
        Part 2 output: Value of all packets: 19348959966392
         */
    }

    String input;
    ArrayList<Packet> packets = new ArrayList<>();
    BitStream bitStream;
    public Day16(String input) {
        this.input = input;
        bitStream = new BitStream(input);
        generatePackets();
    }
    public List<Packet> getPackets() {
        return List.copyOf(packets);
    }
    public int getVersionSum() {
        return getVersionSum(packets);
    }
    public int getVersionSum(List<Packet> packets) {
        int returnValue = 0;
        for(Packet packet : packets) {
            returnValue += packet.version;
            if(packet.isOperator()) {
                returnValue += getVersionSum(((OperatorPacket)packet).packets);
            }
        }
        return returnValue;
    }
    public long getValue() {
        if(packets.size() > 1) { throw new RuntimeException("More than one top level packet?!?!"); }
        return packets.get(0).getValue();
    }
    void generatePackets() {
        while(!bitStream.isEmpty()) {
            getNextPacket(packets);
            if(bitStream.bitsRemaining() < BitStream.MIN_PACKET_LENGTH) {
                break;
            }
        }
    }
    int getNextPacket(List<Packet> packetList) {
        int bitCount = 0;
        int version = bitStream.getBits(3);
        int type = bitStream.getBits(3);
        bitCount += 6;
        if(type == Packet.TYPE_LITERAL) {
            LiteralPacket packet = (LiteralPacket)(Packet.getPacket(type));
            packet.version = version;
            while(bitStream.getBits(1) == Packet.LITERAL_CHECK_CONTINUE) {
                packet.literal = (packet.literal << 4) + bitStream.getBits(4);
                bitCount += 5;
            }
            packet.literal = (packet.literal << 4) + bitStream.getBits(4);
            bitCount += 5;
            packetList.add(packet);
        } else {
            OperatorPacket packet = (OperatorPacket) Packet.getPacket(type);
            packet.version = version;
            packet.lengthType = bitStream.getBits(1);
            bitCount += 1;
            if(packet.lengthType == 0) {
                int subPacketLength = bitStream.getBits(15);
                bitCount += 15;
                int subPacketBitCount = 0;
                while(subPacketBitCount < subPacketLength) {
                    subPacketBitCount += getNextPacket(packet.packets);
                }
                bitCount += subPacketBitCount;
            } else {
                int subPacketCount = bitStream.getBits(11);
                bitCount += 11;
                while(subPacketCount > 0) {
                    bitCount += getNextPacket(packet.packets);
                    subPacketCount--;
                }
            }
            packetList.add(packet);
        }
        return bitCount;
    }

    abstract public static class Packet {
        int version, type;
        public Packet() {}
        public boolean isOperator() { return type != TYPE_LITERAL; }
        abstract public long getValue();
        static final int TYPE_SUM = 0;
        static final int TYPE_PRODUCT = 1;
        static final int TYPE_MIN = 2;
        static final int TYPE_MAX = 3;
        static final int TYPE_LITERAL = 4;
        static final int TYPE_GT = 5;
        static final int TYPE_LT = 6;
        static final int TYPE_EQ = 7;
        static final int LITERAL_CHECK_CONTINUE = 1;
        static Packet getPacket(int type) {
            switch(type) {
                case TYPE_SUM: return new SumOperatorPacket();
                case TYPE_PRODUCT: return new ProductOperatorPacket();
                case TYPE_MIN: return new MinOperatorPacket();
                case TYPE_MAX: return new MaxOperatorPacket();
                case TYPE_LITERAL: return new LiteralPacket();
                case TYPE_GT: return new GTOperatorPacket();
                case TYPE_LT: return new LTOperatorPacket();
                case TYPE_EQ: return new EQOperatorPacket();
                default: throw new IllegalStateException("Unknown Packet Type: " + type);
            }
        }
    }
    public static class LiteralPacket extends Packet {
        long literal;
        public LiteralPacket() { super(); type = TYPE_LITERAL; }
        public long getValue() { return literal; }
    }
    abstract public static class OperatorPacket extends Packet {
        int lengthType;
        List<Packet> packets;
        public OperatorPacket() { super(); packets = new ArrayList<>(); }
    }
    public static class SumOperatorPacket extends OperatorPacket {
        public SumOperatorPacket() { super(); type = TYPE_SUM; }
        public long getValue() {
            long returnValue = 0;
            for(Packet p : packets) { returnValue += p.getValue(); }
            return returnValue;
        }
    }
    public static class ProductOperatorPacket extends OperatorPacket {
        public ProductOperatorPacket() { super(); type = TYPE_PRODUCT; }
        public long getValue() {
            long returnValue = 1;
            for(Packet p : packets) { returnValue *= p.getValue(); }
            return returnValue;
        }
    }
    public static class MinOperatorPacket extends OperatorPacket {
        public MinOperatorPacket() { super(); type = TYPE_MIN; }
        public long getValue() {
            long returnValue = Integer.MAX_VALUE;
            for(Packet p : packets) { returnValue = Math.min(returnValue, p.getValue()); }
            return returnValue;
        }
    }
    public static class MaxOperatorPacket extends OperatorPacket {
        public MaxOperatorPacket() { super(); type = TYPE_MAX; }
        public long getValue() {
            long returnValue = Long.MIN_VALUE;
            for(Packet p : packets) { returnValue = Math.max(returnValue, p.getValue()); }
            return returnValue;
        }
    }
    public static class GTOperatorPacket extends OperatorPacket {
        public GTOperatorPacket() { super(); type = TYPE_GT; }
        public long getValue() { return (packets.get(0).getValue() > packets.get(1).getValue()) ? 1 : 0; }
    }
    public static class LTOperatorPacket extends OperatorPacket {
        public LTOperatorPacket() { super(); type = TYPE_LT; }
        public long getValue() { return (packets.get(0).getValue() < packets.get(1).getValue()) ? 1 : 0; }
    }
    public static class EQOperatorPacket extends OperatorPacket {
        public EQOperatorPacket() { super(); type = TYPE_EQ; }
        public long getValue() { return (packets.get(0).getValue() == packets.get(1).getValue()) ? 1 : 0; }
    }

    public static class BitStream {
        String[] nibbles;
        byte nibble = 0;
        int nibbleOffset = -1;
        int bitOffset = 0;
        int[] MASK_OFFSET = new int[] { 8, 4, 2, 1 };
        int[] SHIFT_OFFSET = new int[] { 3, 2, 1, 0 };
        static final int MIN_PACKET_LENGTH = 11;
        public BitStream(String data) {
            nibbles = data.split("");
            nextNibble();
        }
        int getBits(int number) {
            if(isEmpty()) { throw new IllegalStateException("No more data"); }
            int returnValue = 0;
            for(int i = 0; i < number; i++) {
                if(returnValue != 0) { returnValue <<= 1; }
                returnValue += (nibble & MASK_OFFSET[bitOffset]) >> SHIFT_OFFSET[bitOffset];
                bitOffset = (bitOffset + 1) % 4;
                if(bitOffset == 0) { nextNibble(); }
            }
            return returnValue;
        }
        void nextNibble() { nibbleOffset++; if(!isEmpty()) {nibble = Byte.parseByte(nibbles[nibbleOffset], 16); } }
        int bitsRemaining() { return 4*(nibbles.length - nibbleOffset) - bitOffset; }
        boolean isEmpty() {
            return (nibbleOffset >= nibbles.length);
        }
    }

    private final static String day16Input = "C20D718021600ACDC372CD8DE7A057252A49C940239D68978F7970194EA7CCB310088760088803304A0AC1B100721EC298D3307440041CD8B8005D12DFD27CBEEF27D94A4E9B033006A45FE71D665ACC0259C689B1F99679F717003225900465800804E39CE38CE161007E52F1AEF5EE6EC33600BCC29CFFA3D8291006A92CA7E00B4A8F497E16A675EFB6B0058F2D0BD7AE1371DA34E730F66009443C00A566BFDBE643135FEDF321D000C6269EA66545899739ADEAF0EB6C3A200B6F40179DE31CB7B277392FA1C0A95F6E3983A100993801B800021B0722243D00042E0DC7383D332443004E463295176801F29EDDAA853DBB5508802859F2E9D2A9308924F9F31700AA4F39F720C733A669EC7356AC7D8E85C95E123799D4C44C0109C0AF00427E3CC678873F1E633C4020085E60D340109E3196023006040188C910A3A80021B1763FC620004321B4138E52D75A20096E4718D3E50016B19E0BA802325E858762D1802B28AD401A9880310E61041400043E2AC7E8A4800434DB24A384A4019401C92C154B43595B830002BC497ED9CC27CE686A6A43925B8A9CFFE3A9616E5793447004A4BBB749841500B26C5E6E306899C5B4C70924B77EF254B48688041CD004A726ED3FAECBDB2295AEBD984E08E0065C101812E006380126005A80124048CB010D4C03DC900E16A007200B98E00580091EE004B006902004B00410000AF00015933223100688010985116A311803D05E3CC4B300660BC7283C00081CF26491049F3D690E9802739661E00D400010A8B91F2118803310A2F43396699D533005E37E8023311A4BB9961524A4E2C027EC8C6F5952C2528B333FA4AD386C0A56F39C7DB77200C92801019E799E7B96EC6F8B7558C014977BD00480010D89D106240803518E31C4230052C01786F272FF354C8D4D437DF52BC2C300567066550A2A900427E0084C254739FB8E080111E0";
}
