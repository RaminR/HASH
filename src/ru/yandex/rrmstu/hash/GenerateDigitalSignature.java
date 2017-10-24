package ru.yandex.rrmstu.hash;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

/**
 * @author Rasuli Ramin
 * @version 1.0
 */
public class GenerateDigitalSignature {

    public static void main(String[] args) {
        try {
            // Get instance and initialize a KeyPairGenerator object.
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);

            // Get a PrivateKey from the generated key pair.
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();

            // Get an instance of Signature object and initialize it.
            Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
            signature.initSign(privateKey);

            // Supply the data to be signed to the Signature object
            // using the update() method and generate the digital
            // signature.
            byte[] bytes = Files.readAllBytes(Paths.get("Test.txt"));
            signature.update(bytes);
            byte[] digitalSignature = signature.sign();

            // Save digital signature and the public key to a file.
            Files.write(Paths.get("sign.txt"), digitalSignature);
            Files.write(Paths.get("pubKey.txt"), keyPair.getPublic().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
