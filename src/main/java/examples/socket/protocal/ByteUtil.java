package examples.socket.protocal;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

public class ByteUtil {
    public static byte[] int2Byte4C(int v) {
        byte[] b = new byte[4];

        b[3] = (byte) (v >>> 24 & 0xFF);
        b[2] = (byte) (v >>> 16 & 0xFF);
        b[1] = (byte) (v >>> 8 & 0xFF);
        b[0] = (byte) (v >>> 0 & 0xFF);
        return b;
    }

    public static byte[] short2Byte4C(short v) {
        byte[] b = new byte[2];

        b[1] = (byte) (v >>> 8 & 0xFF);
        b[0] = (byte) (v >>> 0 & 0xFF);
        return b;
    }

    public static int byte2Int4C(byte[] b) {
        return byte2Int4C(b, 0);
    }

    public static int byte2Int4C(byte[] b, int index) {
        return ((b[(index + 3)] & 0xFF) << 24 | (b[(index + 2)] & 0xFF) << 16 | (b[(index + 1)] & 0xFF) << 8 | b[(index + 0)] & 0xFF);
    }

    public static short byte2Short4C(byte[] b) {
        return byte2Short4C(b, 0);
    }

    public static short byte2Short4C(byte[] b, int index) {
        return (short) ((b[(index + 1)] & 0xFF) << 8 | b[(index + 0)] & 0xFF);
    }

    public static byte[] int2Byte(int v) {
        byte[] b = new byte[4];
        b[0] = (byte) (v >>> 24 & 0xFF);
        b[1] = (byte) (v >>> 16 & 0xFF);
        b[2] = (byte) (v >>> 8 & 0xFF);
        b[3] = (byte) (v >>> 0 & 0xFF);
        return b;
    }

    public static byte[] short2Byte(short v) {
        byte[] b = new byte[2];
        b[0] = (byte) (v >>> 8 & 0xFF);
        b[1] = (byte) (v >>> 0 & 0xFF);
        return b;
    }

    public static int byte2Int(byte[] b) {
        return byte2Int(b, 0);
    }

    public static int byte2Int(byte[] b, int index) {
        return ((b[(index + 0)] & 0xFF) << 24 | (b[(index + 1)] & 0xFF) << 16 | (b[(index + 2)] & 0xFF) << 8 | b[(index + 3)] & 0xFF);
    }

    public static short byte2Short(byte[] b) {
        return byte2Short(b, 0);
    }

    public static short byte2Short(byte[] b, int index) {
        return (short) ((b[(index + 0)] & 0xFF) << 8 | b[(index + 1)] & 0xFF);
    }

    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] intToBytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >> 24);
        result[1] = (byte) (num >> 16);
        result[2] = (byte) (num >> 8);
        result[3] = (byte) (num /*>> 0*/);
        return result;
    }

    public static String printBytes(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static String toHexString0x(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
            buffer.append(",0x");
        }
        return buffer.toString();
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1)
            return "0" + s;

        return s;
    }

    public static String Bytes2HexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        byte[] arr$ = bytes;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(b)}));
        }
        return sb.toString();
    }

    public static byte[] get16to8Bytes(String s) {
        byte[] result = new byte[8];
        int j = 0;
        for (int i = 0; i < 7; ++i) {
            result[i] = Integer.decode("0x" + s.substring(j, j + 2)).byteValue();
            j += 2;
        }
        return result;
    }

    public static byte checkData(byte[] data) {
        byte b = 0;
        for (int i = 0; i < data.length; ++i)
            b = (byte) (b ^ data[i]);

        return b;
    }

    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
}