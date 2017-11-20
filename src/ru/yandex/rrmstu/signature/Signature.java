package ru.yandex.rrmstu.signature;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Rasuli Ramin
 * @version 1.0
 */
public class Signature {

    private static final int N = 512;

    private static BigInteger p, q, n, module, fN;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger ZERO = BigInteger.ZERO;

    private static final SecureRandom rnd = new SecureRandom();

    Signature(int N) {
        p = BigInteger.probablePrime(N, rnd);
        q = BigInteger.probablePrime(N, rnd);
        module = p.multiply(q);
        fN = (p.subtract(ONE).multiply(q.subtract(ONE)));


    }




    @Override
    public String toString() {
        String message = "";

        message += "public  = " + p  + "\n";
        message += "private = " + q + "\n";
        message += "modulus = " + module;

        return message;
    }


    public static void main(String[] args) {

        Signature signature = new Signature(N);


        System.out.println(signature);

    }


}
