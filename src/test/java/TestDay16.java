import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDay16 {
    @Test
    void testLiteral1() {
        Day16 day16 = new Day16(testInput1);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(6, packet.version);
        assertEquals(4, packet.type);
        assertFalse(packet.isOperator());
        assertEquals(2021, ((Day16.LiteralPacket)packet).literal);
        assertEquals(6, day16.getVersionSum());
        assertEquals(2021, day16.getValue());
    }

    @Test
    void testTypeOperator1() {
        Day16 day16 = new Day16(testInput2);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(1, packet.version);
        assertEquals(6, packet.type);
        assertTrue(packet.isOperator());
        assertEquals(2, ((Day16.OperatorPacket)packet).packets.size());
        Day16.Packet subPacket = ((Day16.OperatorPacket) packet).packets.get(0);
        assertFalse(subPacket.isOperator());
        assertEquals(6, subPacket.version);
        assertEquals(10, ((Day16.LiteralPacket)subPacket).literal);
        subPacket = ((Day16.OperatorPacket) packet).packets.get(1);
        assertFalse(subPacket.isOperator());
        assertEquals(20, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(2, subPacket.version);
        assertEquals(9, day16.getVersionSum());
        assertEquals(1, day16.getValue());
    }

    @Test
    void testTypeOperator2() {
        Day16 day16 = new Day16(testInput3);
        List<Day16.Packet> packets = day16.getPackets();
        assertEquals(1, packets.size());
        Day16.Packet packet = packets.get(0);
        assertEquals(7, packet.version);
        assertEquals(3, packet.type);
        assertTrue(packet.isOperator());
        assertEquals(3, ((Day16.OperatorPacket)packet).packets.size());
        Day16.Packet subPacket = ((Day16.OperatorPacket)packet).packets.get(0);
        assertFalse(subPacket.isOperator());
        assertEquals(1, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(2, subPacket.version);
        subPacket = ((Day16.OperatorPacket)packet).packets.get(1);
        assertFalse(subPacket.isOperator());
        assertEquals(2, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(4, subPacket.version);
        subPacket = ((Day16.OperatorPacket)packet).packets.get(2);
        assertFalse(subPacket.isOperator());
        assertEquals(3, ((Day16.LiteralPacket)subPacket).literal);
        assertEquals(1, subPacket.version);
        assertEquals(14, day16.getVersionSum());
        assertEquals(3, day16.getValue());
    }

    @Test
    void testInput4() {
        Day16 day16 = new Day16(testInput4);
        assertEquals(16, day16.getVersionSum());
        assertEquals(15, day16.getValue());
    }

    @Test
    void testInput5() {
        Day16 day16 = new Day16(testInput5);
        assertEquals(12, day16.getVersionSum());
        assertEquals(46, day16.getValue());
    }

    @Test
    void testInput6() {
        Day16 day16 = new Day16(testInput6);
        assertEquals(23, day16.getVersionSum());
        assertEquals(46, day16.getValue());
    }

    @Test
    void testInput7() {
        Day16 day16 = new Day16(testInput7);
        assertEquals(31, day16.getVersionSum());
        assertEquals(54, day16.getValue());
    }

    @Test
    void testInput8() {
        Day16 day16 = new Day16(testInput8);
        assertEquals(3, day16.getValue());
    }

    @Test
    void testInput9() {
        Day16 day16 = new Day16(testInput9);
        assertEquals(54, day16.getValue());
    }

    @Test
    void testInput10() {
        Day16 day16 = new Day16(testInput10);
        assertEquals(7, day16.getValue());
    }

    @Test
    void testInput11() {
        Day16 day16 = new Day16(testInput11);
        assertEquals(9, day16.getValue());
    }

    @Test
    void testInput12() {
        Day16 day16 = new Day16(testInput12);
        assertEquals(1, day16.getValue());
    }

    @Test
    void testInput13() {
        Day16 day16 = new Day16(testInput13);
        assertEquals(0, day16.getValue());
    }

    @Test
    void testInput14() {
        Day16 day16 = new Day16(testInput14);
        assertEquals(0, day16.getValue());
    }

    @Test
    void testInput15() {
        Day16 day16 = new Day16(testInput15);
        assertEquals(1, day16.getValue());
    }

    String testInput1 = "D2FE28";
    String testInput2 = "38006F45291200";
    String testInput3 = "EE00D40C823060";
    String testInput4 = "8A004A801A8002F478";
    String testInput5 = "620080001611562C8802118E34";
    String testInput6 = "C0015000016115A2E0802F182340";
    String testInput7 = "A0016C880162017C3686B18A3D4780";
    String testInput8 = "C200B40A82";
    String testInput9 = "04005AC33890";
    String testInput10 = "880086C3E88112";
    String testInput11 = "CE00C43D881120";
    String testInput12 = "D8005AC2A8F0";
    String testInput13 = "F600BC2D8F";
    String testInput14 = "9C005AC2F8F0";
    String testInput15 = "9C0141080250320F1802104A08";
}
