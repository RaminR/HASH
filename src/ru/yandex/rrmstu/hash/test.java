package ru.yandex.rrmstu.hash;

/**
 * Created by RRM on 10.11.17.
 */

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class test {

    private static long e;
    private static long d;
    private static long n;

    private static long checkGsd(long a, long b) {
        while (a != 0 && b != 0) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }
        return a + b;
    }

    private static long calculateE(long m) {
        long e = m / (long) (Math.random() * 1000);
        //long e = m/1764679240;
        while (checkGsd(m, e) != 1) {
            e++;
        }
        return e;
    }


    private static long calculateD(long e, long m) {
        double d;
        long k = 1;
        while (true) {
            d = (k * m + 1) / (double) e;
            if (d % 1 == 0) break;
            k++;
        }
        return (long) d;
    }


    private static void createKeys() {
        long[] simpleArray = {104677, 104681, 104683, 104693, 104701, 104707, 104711, 104717, 104723, 100981, 100987, 102611, 103333, 103687, 104281, 104369, 100049, 100801, 102019, 102953};
        Random random = new Random();
        int inq = random.nextInt(simpleArray.length);
        int inp = random.nextInt(simpleArray.length);
        while (inp == inq) {
            inp = random.nextInt(simpleArray.length);
        }
        //long q = simpleArray[inq];
        //long p = simpleArray[inp];
        //long q = 1556624921;
        //long p = 2119275461;
        long q = 13;
        long p = 7;
        n = p * q;
        long m = (p - 1) * (q - 1);
        //e = calculateE(m);
        e = 5;
        d = calculateD(e, m);
    }

    private static int getCode(char s) {
        String alph = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя 0123456789";
        return alph.indexOf(s) + 1;
    }

    private static int[] encrypt(String text) {
        text = text.toLowerCase();
        char t[] = text.toCharArray();
        int c[] = new int[t.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = (int) ((Math.pow((double) getCode(t[i]), e)) % n);
        }
        return c;
    }

    private static String deEncrypt(int[] array) {
        String alph = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя 0123456789";
        String s = "";
        int[] ab = new int[array.length]; // массив расшифрованных цифр
        BigInteger big, big1;
        for (int i = 0; i < array.length; i++) {
            big = BigInteger.valueOf(array[i]);
            big = big.pow((int) d);
            big1 = BigInteger.valueOf(n);
            big = big.mod(big1);
            ab[i] = big.intValue();
        }
        //-------------¬ывод расшифрованного текста
        for (int i = 0; i < ab.length; i++) {
            System.out.print(alph.charAt(ab[i] - 1));
        }
        return s;
    }

    public static void main(String[] args) {
        createKeys();
        System.out.println("Открытый ключ(" + e + ", " + n + ");");
        System.out.println("Закрытый ключ(" + d + ", " + n + ");");

        int[] code = encrypt("КАФСИ");
        System.out.println("Ўифр " + Arrays.toString(code));

        deEncrypt(code);
    }
}