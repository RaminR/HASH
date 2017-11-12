package ru.yandex.rrmstu.hash;

import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RRM on 01.11.17.
 */
public class Hash {

    public static long P = 7L, Q = 13L, N = 1L, FN = 1L, D = 1L, E = 5L;
    private static double H, S;
    private static int MIN = Integer.MIN_VALUE, MAX = Integer.MAX_VALUE;
    private static long randomBaseP, randomBaseQ;
    private static byte[] arrayForFile;

    private static String extension;
    private static String PUBLIC_KEY_FILE = "public.key";
    private static String PRIVATE_KEY_FILE = "private.key";
    private static Random random = new Random();

    /**
     * Метод <code>getInstance</code> треубется для иницилизации всех переменых, применяется в начале.
     */
    public static void getInstance() {
        getP();
        getQ();
        getN();
        getFN();
//        setE(FN);
        setD(E, FN);

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("P: %d; Q: %d; N: %d; FN: %d; E: %d; D: %d;", P, Q, N, FN, E, D);
        System.out.println("\n------------------------------------------------------------------------------------------------");

    }

    /**
     * @return
     */
    private static long getP() {
        while (true) {
            randomBaseP = -1L * (MIN + random.nextInt(MAX));
            if (isPrime(P) == true) break;
            else {
                P = randomBaseP;
            }
        }
        return P;
    }

    /**
     * @return
     */
    private static long getQ() {
        while (true) {
            randomBaseQ = -1L * (MIN + random.nextInt(MAX));
            if (isPrime(Q) == true) break;
            else {
                Q = randomBaseQ;
            }
        }
        return Q;
    }

    /**
     * @return
     */
    private static long getN() {
        return N = getP() * getQ();
    }

    /**
     * @return
     */
    private static long getFN() {
        return FN = (getP() - 1L) * (getQ() - 1L);
    }

    /**
     * @param FN
     * @return
     */
    private static long setE(long FN) {
        long mod = -1L * (MIN + random.nextInt(MAX));
        E = FN / mod;
        while (checkGsd(E, FN) != 1L)
            E++;
        return E;
    }

    /**
     * @param E
     * @param FN
     * @return
     */
    private static long setD(long E, long FN) {
        double K = 1.0;
        while (true) {
            D = (long) ((K * FN + 1L) / (double) E);
            if (D % 1L == 0L) break;
            else
                K++;
        }
        return D;
    }

    /**
     * @param E
     * @param fN
     * @return
     */
    private static long checkGsd(long E, long fN) {
        while (E != 0 && fN != 0) {
            if (E > fN) {
                E = E % fN;
            } else {
                fN = fN % E;
            }
        }
        return E + fN;
    }

    /**
     * @param number
     * @return
     */
    private static boolean isPrime(long number) {
        if (number == 1L) return false;
        for (long i = 2L; i * i <= number; i++)
            if (number % i == 0L)
                return false;
        return true;
    }

    /**
     * <cede>OpenFile</cede>
     * <p>Данный метод работает только с текстовыми файлами!</p>
     *
     * @param pathToFile - Путь к файлу, который требуется открыть.
     * @return Если файл был найден то возвращает содержание текста, в ином случае ошибку что файл был не найден.
     */
    @Nullable
    private static String openFile(String pathToFile) {
        String line = null;
        try (InputStream in = Files.newInputStream(Paths.get(pathToFile));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            while ((line = reader.readLine()) != null)
                return line;

        } catch (IOException ioe) {
            return "Файл был не найден!\nОшибка {" + String.valueOf(ioe) + "}";
        }
        return null;
    }

    /**
     * <code>SaveFile</code>
     *
     * @param nameFile - Название файла с его раширением.
     * @param data     - Данные которые будут записаны в файл.
     */
    private static void saveFile(String nameFile, byte[] data) {
        try {
            System.out.println("Ваш файл был записан в корень проекта под названием: " + nameFile);
            Files.write(Paths.get(nameFile), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>getFileExtension</code>
     * <p>
     * <p>Получаем расширение файла, для того чтобы можно было его записать в зашифрованый файл.</p>
     *
     * @param extension - На вход получаем имя файла.
     * @return Возвращаем расширение полученого файла.
     */
    private static String getFileExtension(String extension) {

        Pattern pattern = Pattern.compile("\\.\\w+$");
        Matcher matcher = pattern.matcher(extension);
        matcher.find();

        return matcher.group();
    }

    /**
     * <code>getArrayForFile</code>
     * <p>Данный метод переводит файл в массив байтов</p>
     *
     * @param filePath - Путь к файлу, который треубется записать в массив байтов.
     * @return Возвращает массив байтов.
     */
    private static byte[] getArrayForFile(String filePath) {
        try {
            extension = getFileExtension(filePath);
            arrayForFile = Files.readAllBytes(Paths.get(filePath));
//            System.out.printf("Путь к файлу: %s\nРасширение: %s\nРазмер файла: %s байтов\n", Paths.get(filePath), extension, arrayForFile.length);
        } catch (IOException ioe) { //ioe.printStackTrace();
            System.err.println("Файл не найден! Проверьте правильность пути или название файла.");
        }
        return arrayForFile;
    }

    /**
     * @param filePath
     */
    public static void encryptData(String filePath) {
        getArrayForFile(filePath);

        getH();
        getS();
        System.out.printf("Путь к файлу: %s\nРасширение: %s\nРазмер файла: %s байтов\n", Paths.get(filePath), extension, arrayForFile.length);

        savePublicKey();
    }

    /**
     * @param filePath
     * @param key
     */
    public static void decryptData(String filePath, String key) {

    }


    public static void savePublicKey() {
        System.out.println();
        saveFile(PUBLIC_KEY_FILE, toByte(H));
        BigInteger k = new BigDecimal(H).toBigInteger();

        BigDecimal decimal = null;
        System.out.println(decimal.valueOf(H) + "\n" + k);

        System.out.println(fromByte(toByte(1L)));

        byte[] a = encodeUTF8("Привет");

        System.out.println(Arrays.toString(a));

        System.out.println(decodeUTF8(a));

    }

    private static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    private static byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    public static double fromByte(byte[] b) {
        long l = ((long) b[0] << 56) +
                ((long) (b[1] & 0xFF) << 48) +
                ((long) (b[2] & 0xFf) << 40) +
                ((long) (b[3] & 0xFF) << 32) +
                ((long) (b[4] & 0xFF) << 24) +
                ((b[5] & 0xFf) << 16) +
                ((b[6] & 0xFf) << 8) +
                ((b[7] & 0xFF) << 0);
        return Double.longBitsToDouble(l);
    }

    public static byte[] toByte(double d) {
        long l = Double.doubleToLongBits(d);
        byte[] b = new byte[16];
        for (int i = 0; i < 15; i++) {
            b[i] = (byte) (l >>> (56 - (8 * i)));
        }
        return b;
    }

    /**
     * Метод <code>getH</code> получает хеш образ данных
     *
     * @return
     */
    @Nullable
    private static double getH() {
        byte[] newArrayFile = new byte[arrayForFile.length];

        for (int i = 0; i < arrayForFile.length; i++) {
            H = Math.pow((H + arrayForFile[i]), 2) % N;

            newArrayFile[i] = (byte) H;
        }
        return H;
    }

    /**
     * @return
     */
    private static double getS() {
        S = Math.pow(H, D) % N;
        return S;
    }
