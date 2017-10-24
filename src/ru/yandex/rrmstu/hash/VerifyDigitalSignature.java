package ru.yandex.rrmstu.hash;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Rasuli Ramin
 * @version 1.0
 */
public class VerifyDigitalSignature {
    public static void main(String[] args) {
        try {
            byte[] publicKeyEncoded = Files.readAllBytes(Paths.get("pubKey.txt"));
            byte[] digitalSignature = Files.readAllBytes(Paths.get("sign.txt"));

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
            signature.initVerify(publicKey);

            byte[] bytes = Files.readAllBytes(Paths.get("Test.txt"));
            signature.update(bytes);

            boolean verified = signature.verify(digitalSignature);
            if (verified) {
                System.out.println("Data verified.");
            } else {
                System.out.println("Cannot verify data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
