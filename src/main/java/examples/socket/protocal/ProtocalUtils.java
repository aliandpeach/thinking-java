package examples.socket.protocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProtocalUtils {

    public static byte[] number2CBCD(BigDecimal f, int len, int dec) {
        f.setScale(dec, 4);
        long fee = f.multiply(new BigDecimal(Math.pow(10.0D, dec))).longValue();
        int slen = len / 2;
        byte[] ret = new byte[slen];
        if (fee < 1071755560539389952L) ret[(slen - 1)] = 16;
        fee = Math.abs(fee);
        NumberFormat formatter = new DecimalFormat(zeroFormat(len - 1));
        String s = formatter.format(fee);
        byte[] bcb = s.getBytes();
        int lo1 = bcb[0] - 48;
        ret[3] = (byte) (ret[(slen - 1)] | lo1);
        System.arraycopy(STR2CBCD(s.substring(1, len - 1)), 0, ret, 0, slen - 1);
        return ret;
    }

    public static byte[] number2CBCD122(BigDecimal f) {
        f.setScale(2, 4);
        long fee = f.multiply(new BigDecimal(100)).longValue();
        byte[] ret = new byte[6];
        if (fee < 1071755560539389952L) ret[5] = 16;
        fee = Math.abs(fee);
        NumberFormat formatter = new DecimalFormat("00000000000");
        String s = formatter.format(fee);
        byte[] bcb = s.getBytes();
        int lo1 = bcb[0] - 48;
        ret[5] = (byte) (ret[5] | lo1);
        System.arraycopy(STR2CBCD(s.substring(1, 11)), 0, ret, 0, 5);
        System.out.println(CBCD2STR(ret));
        return ret;
    }

    public static BigDecimal CBCD2BigDecimal(byte[] f, int len, int dec) {
        int slen = len / 2;
        int l = (f[(slen - 1)] & 0xF) + 48;
        String s = String.valueOf((char) l);
        byte[] bcb = new byte[slen - 1];
        System.arraycopy(f, 0, bcb, 0, slen - 1);
        s = s + CBCD2STR(bcb);
        int fh = (byte) (f[(slen - 1)] >> 4 & 0xFF);
        if (fh == 1)
            s = "-" + s;
        return new BigDecimal(s).divide(new BigDecimal(Math.pow(10.0D, dec)));
    }

    public static BigDecimal CBCD2BigDecimal122(byte[] f) {
        int l = (f[5] & 0xF) + 48;
        String s = String.valueOf((char) l);
        byte[] bcb = new byte[5];
        System.arraycopy(f, 0, bcb, 0, 5);
        s = s + CBCD2STR(bcb);
        int fh = (byte) (f[5] >> 4 & 0xFF);
        if (fh == 1)
            s = "-" + s;
        return new BigDecimal(s).divide(new BigDecimal(100));
    }

    public static BigDecimal CBCD2BigDecimal84(byte[] f) {
        int l = (f[3] & 0xF) + 48;
        String s = String.valueOf((char) l);
        byte[] bcb = new byte[3];
        System.arraycopy(f, 0, bcb, 0, 3);
        s = s + CBCD2STR(bcb);
        int fh = (byte) (f[3] >> 4 & 0xFF);
        if (fh == 1)
            s = "-" + s;
        return new BigDecimal(s).divide(new BigDecimal(10000));
    }

    public static byte[] number2CBCD84(BigDecimal f) {
        f.setScale(4, 4);
        long fee = f.multiply(new BigDecimal(10000)).longValue();
        byte[] ret = new byte[4];
        if (fee < 1071755560539389952L) ret[3] = 16;
        fee = Math.abs(fee);
        NumberFormat formatter = new DecimalFormat("0000000");
        String s = formatter.format(fee);
        byte[] bcb = s.getBytes();
        int lo1 = bcb[0] - 48;
        ret[3] = (byte) (ret[3] | lo1);
        System.arraycopy(STR2CBCD(s.substring(1, 7)), 0, ret, 0, 3);
        return ret;
    }

    public static String CBCD2STR(byte[] bcd) {
        StringBuffer sb = new StringBuffer();
        for (int i = bcd.length - 1; i >= 0; --i) {
            int h = ((bcd[i] & 0xFF) >> 4) + 48;
            sb.append((char) h);
            int l = (bcd[i] & 0xF) + 48;
            sb.append((char) l);
        }
        return sb.toString();
    }

    public static byte[] STR2CBCD(String s) {
        byte[] ret;
        if (null == s) s = "";

        int len = s.length();
        if (len % 2 != 0)
            s = "0" + s;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            char[] cs = s.toCharArray();
            len = cs.length;
            for (int i = len - 1; i >= 0; i -= 2) {
                int low = cs[i] - '0';
                int high = cs[(i - 1)] - '0';
                int d = high << 4 | low;
                baos.write(d);
            }
            ret = baos.toByteArray();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
            }
        }
        return ret;
    }

    public static String zeroFormat(int len) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < len; ++i)
            sb.append("0");

        return sb.toString();
    }

    public static byte[] readBytes(int len, ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return bytes;
    }

    public static int readByte2Int(ByteArrayInputStream instream) throws Exception {
        return (instream.read() & 0xFF);
    }

    public static int readInt(int len, ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        String s = new String(bytes);
        return Integer.parseInt(s.trim());
    }

    public static BigDecimal readBigDecimal(int len, int dec, ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[len / 2];
        instream.read(bytes, 0, len / 2);
        return CBCD2BigDecimal(bytes, len, dec);
    }

    public static BigDecimal readBigDecimal122(ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[6];
        instream.read(bytes, 0, 6);
        return CBCD2BigDecimal122(bytes);
    }

    public static BigDecimal readBigDecimal84(ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[4];
        instream.read(bytes, 0, 4);
        return CBCD2BigDecimal84(bytes);
    }

    public static String readString(int len, ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return new String(bytes, "GBK").trim();
    }

    public static String readBCDString(int len, ByteArrayInputStream instream) throws Exception {
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return CBCD2STR(bytes);
    }

    public static String readVariableString(ByteArrayInputStream instream) throws Exception {
        int len = instream.read();
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return new String(bytes, "GBK").trim();
    }

    public static byte[] readVariableBytes(ByteArrayInputStream instream) throws Exception {
        int len = instream.available();
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return bytes;
    }

    public static byte[] readLongVariableBytes1(ByteArrayInputStream instream) throws Exception {
        byte[] blen = new byte[2];
        instream.read(blen, 0, 2);
        int len = ByteUtil.byte2Short(blen);
        byte[] bytes = new byte[len];
        instream.read(bytes, 0, len);
        return bytes;
    }

    public static void write(byte[] bytes, ByteArrayOutputStream outstream) throws Exception {
        outstream.write(bytes);
    }

    public static void write(byte b, ByteArrayOutputStream outstream) throws Exception {
        outstream.write(b);
    }

    public static void writeString(String s, int len, ByteArrayOutputStream outstream) throws Exception {
        if (null == s) s = "";
        byte[] bytes = s.getBytes("GBK");
        int slen = bytes.length;
        int l = len - slen;

        if (l < 0) {
            outstream.write(bytes, 0, len);
        } else {
            outstream.write(bytes);
            for (int i = 0; i < l; ++i)
                outstream.write(0);
        }
    }

    public static void writeBCDString(String s, int len, ByteArrayOutputStream outstream) throws Exception {
        if (null == s) s = "";
        byte[] bytes = STR2CBCD(s);
        int l = len - bytes.length;
        if (l > 0) {
            outstream.write(bytes);
            for (int i = 0; i < l; ++i)
                outstream.write(new byte[]{0});
        } else {
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeVariableString(String s, ByteArrayOutputStream outstream) throws Exception {
        writeVariableString(s, 255, outstream);
    }

    public static void writeVariableString(String s, int len, ByteArrayOutputStream outstream) throws Exception {
        if (null == s) s = "";
        byte[] bytes = s.getBytes("GBK");
        int slen = bytes.length;
        int l = len - slen;
        if (l > 0) {
            write((byte) slen, outstream);
            outstream.write(bytes, 0, slen);
        } else {
            write((byte) len, outstream);
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeLongVariableString(String s, int len, ByteArrayOutputStream outstream) throws Exception {
        if (null == s) s = "";
        byte[] bytes = s.getBytes("GBK");
        int slen = bytes.length;
        int l = len - slen;
        if (l > 0) {
            write(ByteUtil.short2Byte4C((short) slen), outstream);
            outstream.write(bytes);
        } else {
            write(ByteUtil.short2Byte4C((short) len), outstream);
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeVariableBytes(byte[] bytes, int len, ByteArrayOutputStream outstream) throws Exception {
        int slen = bytes.length;
        int l = len - slen;
        if (l > 0) {
            write((byte) slen, outstream);
            outstream.write(bytes, 0, slen);
        } else {
            write((byte) len, outstream);
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeLongVariableString(String s, ByteArrayOutputStream outstream) throws Exception {
        if (null == s) s = "";
        byte[] bytes = s.getBytes("GBK");
        int slen = bytes.length;
        int len = 32765;
        int l = len - slen - 2;
        if (l > 0) {
            write(ByteUtil.short2Byte4C((short) slen), outstream);
            outstream.write(bytes);
        } else {
            write(ByteUtil.short2Byte4C((short) len), outstream);
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeLongVariableBytes(byte[] bytes, int len, ByteArrayOutputStream outstream) throws Exception {
        int slen = bytes.length;
        int l = len - slen;
        if (l > 0) {
            write(ByteUtil.short2Byte4C((short) slen), outstream);
            outstream.write(bytes);
        } else {
            write(ByteUtil.short2Byte4C((short) len), outstream);
            outstream.write(bytes, 0, len);
        }
    }

    public static void writeBigDecimal(BigDecimal f, int len, int dec, ByteArrayOutputStream outstream) throws Exception {
        byte[] bytes = number2CBCD(f, len, dec);
        outstream.write(bytes);
    }

    public static void writeBigDecimal122(BigDecimal f, ByteArrayOutputStream outstream) throws Exception {
        byte[] bytes = number2CBCD122(f);
        System.out.println(new String(bytes) + "================");
        outstream.write(bytes);
    }

    public static void writeBigDecimal84(BigDecimal f, ByteArrayOutputStream outstream) throws Exception {
        byte[] bytes = number2CBCD84(f);
        outstream.write(bytes);
    }

    public static byte[] getBytes(int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; ++i)
            bytes[i] = 0;

        return bytes;
    }

    public static byte[] getDefaultMacKey() {
        return getBytes(8);
    }

    public static String formatNum(String s, int len) {
        if (s.length() > len)
            return s.substring(0, len - 1);

        StringBuffer str = new StringBuffer(s);
        int l = len - s.length();
        for (int i = 0; i < l; ++i)
            str.insert(0, "0");

        return str.toString();
    }

    public static String trim(String s) {
        int idx = s.indexOf(0);
        if (idx != -1)
            s = s.substring(0, idx);

        return s;
    }

}
