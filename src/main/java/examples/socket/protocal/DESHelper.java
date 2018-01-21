package examples.socket.protocal;

import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DESHelper {
    public static byte[] mac(byte[] key, byte[] data) {
        int group = (data.length + 7) / 8;
        int offset = 0;

        byte[] edata = new byte[8];
        for (int i = 0; i < group; ++i) {
            byte[] temp = new byte[8];
            if (i != group - 1) {
                System.arraycopy(data, offset, temp, 0, 8);
                offset += 8;
            } else {
                System.arraycopy(data, offset, temp, 0, data.length - offset);
            }

            if (i != 0)
                temp = XOR(edata, temp);

            edata = encrypt(key, temp);
        }
        return edata;
    }

    private static byte[] XOR(byte[] edata, byte[] temp) {
        byte[] result = new byte[8];
        int i = 0;
        for (int j = result.length; i < j; ++i)
            result[i] = (byte) (edata[i] ^ temp[i]);

        return result;
    }

    private static int[] byte2Int(byte[] data) {
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0)
                result[i] = (data[i] + 256);
            else
                result[i] = data[i];

        }

        return result;
    }

    public static byte[] encrypt(byte[] key, byte[] data) {
        byte[] result = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding", "BC");
            cipher.init(1, secretKey, new IvParameterSpec(new byte[8]));
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] decrypt(byte[] key, byte[] data) {
        byte[] result = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding", "BC");
            cipher.init(2, secretKey, new IvParameterSpec(new byte[8]));
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static SecretKey getSecretKeySpec(byte[] keyB) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("Des");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyB, "Des");
        return secretKeyFactory.generateSecret(secretKeySpec);
    }

    public static void main(String[] args) {

        byte[] mac = encrypt(ProtocalUtils.getBytes(8), "88888888".getBytes());
        System.out.println(ByteUtil.toHexString0x(mac));
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}