package chapter01_basic.signature;

import java.security.*;

import static chapter01_basic.signature.KeyUtil.bytesToHexStr;

public class GenerateKeyPair {

    private String priKey;
    private String pubKey;

    public void run() {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            SecureRandom secrand = new SecureRandom();
            secrand.setSeed("yidao".getBytes()); // 初始化随机产生器
            keygen.initialize(1024, secrand);
            KeyPair keys = keygen.genKeyPair();

            PublicKey pubkey = keys.getPublic();
            PrivateKey prikey = keys.getPrivate();

            pubKey = bytesToHexStr(pubkey.getEncoded());
            priKey = bytesToHexStr(prikey.getEncoded());

            System.out.println("pubKey=" + pubKey);
            System.out.println("priKey=" + priKey);

            System.out.println("写入对象 pubkeys ok");
            System.out.println("生成密钥对成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成密钥对失败");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        GenerateKeyPair n = new GenerateKeyPair();
        n.run();
    }
}
