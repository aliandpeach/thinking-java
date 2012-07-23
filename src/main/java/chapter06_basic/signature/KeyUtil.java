package chapter06_basic.signature;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午9:07
 * To change this template use File | Settings | File Templates
 */
public class KeyUtil {
    /**
     * Transform the specified byte into a Hex String form.
     */
    public static String bytesToHexStr(byte[] bcd) {
        StringBuilder s = new StringBuilder(bcd.length * 2);
        for (byte aBcd : bcd) {
            s.append(bcdLookup[(aBcd >>> 4) & 0x0f]);
            s.append(bcdLookup[aBcd & 0x0f]);
        }
        return s.toString();
    }

    /**
     * Transform the specified Hex String into a byte array.
     */
    public static byte[] hexStrToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
