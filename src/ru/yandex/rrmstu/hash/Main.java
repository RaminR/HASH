package ru.yandex.rrmstu.hash;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by RRM on 01.11.17.
 */
public class Main {

    public static Hash hash = new Hash();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Вариант 2. «Хэширование данных»");

        hash.getInstance();
        hash.encryptData("data.txt");

    }

}
