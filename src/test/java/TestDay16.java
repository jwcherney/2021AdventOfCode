import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay16 {
    @Test
    void testLiteral1() {
        Day16 day16 = new Day16(testInput1);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(6, packet.version);
        assertEquals(4, packet.type);
        assertEquals(true, packet.isLiteral());
        assertEquals(2021, ((Day16.LiteralPacket)packet).literal);
        assertEquals(6, day16.getVersionSum());
    }

    @Test
    void testTypeOperator1() {
        Day16 day16 = new Day16(testInput2);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(1, packet.version);
        assertEquals(6, packet.type);
        assertEquals(false, packet.isLiteral());
        assertEquals(2, ((Day16.OperatorPacket)packet).packets.size());
        Day16.Packet subPacket = ((Day16.OperatorPacket) packet).packets.get(0);
        assertEquals(true, subPacket.isLiteral());
        assertEquals(6, subPacket.version);
        assertEquals(10, ((Day16.LiteralPacket)subPacket).literal);
        subPacket = ((Day16.OperatorPacket) packet).packets.get(1);
        assertEquals(true, subPacket.isLiteral());
        assertEquals(20, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(2, subPacket.version);
        assertEquals(9, day16.getVersionSum());
    }

    @Test
    void testTypeOperator2() {
        Day16 day16 = new Day16(testInput3);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(7, packet.version);
        assertEquals(3, packet.type);
        assertEquals(false, packet.isLiteral());
        assertEquals(3, ((Day16.OperatorPacket)packet).packets.size());
        Day16.Packet subPacket = ((Day16.OperatorPacket)packet).packets.get(0);
        assertEquals(true, subPacket.isLiteral());
        assertEquals(1, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(2, subPacket.version);
        subPacket = ((Day16.OperatorPacket)packet).packets.get(1);
        assertEquals(true, subPacket.isLiteral());
        assertEquals(2, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(4, subPacket.version);
        subPacket = ((Day16.OperatorPacket)packet).packets.get(2);
        assertEquals(true, subPacket.isLiteral());
        assertEquals(3, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(1, subPacket.version);
        assertEquals(14, day16.getVersionSum());
    }

    @Test
    void testInput4() {
        Day16 day16 = new Day16(testInput4);
        assertEquals(16, day16.getVersionSum());
    }

    @Test
    void testInput5() {
        Day16 day16 = new Day16(testInput5);
        assertEquals(12, day16.getVersionSum());
    }

    @Test
    void testInput6() {
        Day16 day16 = new Day16(testInput6);
        assertEquals(23, day16.getVersionSum());
    }

    @Test
    void testInput7() {
        Day16 day16 = new Day16(testInput7);
        assertEquals(31, day16.getVersionSum());
    }

    String testInput1 = "D2FE28";
    String testInput2 = "38006F45291200";
    String testInput3 = "EE00D40C823060";
    String testInput4 = "8A004A801A8002F478";
    String testInput5 = "620080001611562C8802118E34";
    String testInput6 = "C0015000016115A2E0802F182340";
    String testInput7 = "A0016C880162017C3686B18A3D4780";
}
