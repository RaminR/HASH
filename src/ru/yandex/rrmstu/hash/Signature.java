package ru.yandex.rrmstu.hash;

import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>HASH</h3>
 *
 * @author Rasuli Ramin
 * @version 1.0
 */


/**
 * TODO: 1. Читать файлы, определить их расширение и записать в начало файла.
 * TODO: 2.
 * TODO: 3.
 * TODO: 4. Переписать метод "Arrays.toString()" - для того, чтобы работать с каждым символом отдельно.
 * TODO: 5.
 * TODO: 6.
 * TODO: 7.
 * TODO: 8.
 * TODO: 9. Поправить доступность методов и переменных.
 */


public class Main {

    public static void main(String[] args) {
        System.out.println("Вариант: 2\n");
        CreatingKey.setPQN();

        System.out.println("Шаг 2. \n" + Arrays.toString(ConvertDataToByte.getArrayForString("Hello")));


        System.out.println("Тип файла: " + WorkingWithFiles.getFileExtension("simple.are.to.my.sstr"));

    }
}

/**
 * <code>SignData</code>
 * <p>Данный класс</p>
 */
class SignData {
    SignData(String fileName, String keyFile) {

    }
}

/**
 *
 *
 */
class VerifyData {
    VerifyData(String fileName, String keyFile) {

    }
}

//class Signature {
//
//    static long H = 0L;
//    static long S = 0L;
//    static long M = 0L;
//
//
//    public static void Encrypt(byte[] data, long d, long n) {
//
//    }
//
//
//    public static boolean Decrypt() {
//
//        return false;
//    }
//}


/**
 * <code>ConvertDataToByte</code>
 * <p>Перевод данных в массив байтов.</p>
 */
class ConvertDataToByte {

    public static byte[] arrayForFile = null;
    public static byte[] arrayForString = null;

    /**
     * <code>getArrayForFile</code>
     * <p>Данный метод переводит файл в массив байтов</p>
     *
     * @param filePath - Путь к файлу, который треубется записать в массив байтов.
     * @return Возвращает массив байтов.
     */
    public static byte[] getArrayForFile(String filePath) {
        try {
            arrayForFile = Files.readAllBytes(Paths.get(filePath));
            System.out.println("Размер файла: " + arrayForFile.length + " байтов. \nПуть: " + Paths.get(filePath));
        } catch (IOException ioe) { //ioe.printStackTrace();
            System.err.println("Файл не найден! Проверьте правильность пути или название файла.");
        }
        return arrayForFile;
    }

    /**
     * <code>getArrayForString</code>
     * <p></p>
     *
     * @param string - Текст который будет переведен в массив байтов
     * @return Возвращет массив байтов.
     */
    public static byte[] getArrayForString(String string) {
        return arrayForString = string.getBytes();
    }
}

/**
 * <cede>WorkingWithFiles</cede>
 * Working with file's
 */
class WorkingWithFiles {

    /**
     * <cede>OpenFile</cede>
     * <p>Данный метод работает только с текстовыми файлами!</p>
     *
     * @param pathToFile - Путь к файлу, который требуется открыть.
     * @return Если файл был найден то возвращает содержание текста, в ином случае ошибку что файл был не найден.
     */

    @Nullable
    public static String OpenFile(String pathToFile) {
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
    public static void SaveFile(String nameFile, byte[] data) {
        try {
            System.out.println("Ваш файл был записан в корень проекта под названием: " + nameFile);
            Files.write(Paths.get(nameFile), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>getFileExtension</code>
     * <p>Получаем расширение файла, для того чтобы можно было его записать в зашифрованый файл.</p>
     *
     * @param extension - На вход получаем имя файла.
     * @return Возвращаем расширение полученого файла.
     */

    public static String getFileExtension(String extension) {

        Pattern pattern = Pattern.compile("\\.\\w+$");
        Matcher matcher = pattern.matcher(extension);
        matcher.find();

        return matcher.group();
    }
}

/**
 * <code>CreatingKey</code>
 */
class CreatingKey {

    public static long p = 1L;
    public static long q = 1L;
    public static long n = 1L;
    public static long fN = 1L;
    public static long d = 1L;
    public static long e = 1L;
    public static int MIN = Integer.MIN_VALUE;
    public static int MAX = Integer.MAX_VALUE;
    public static Random random = new Random();

    /**
     * <code>setPQN</code>
     * <p></p>
     */
    public static void setPQN() {

        long randomBaseForP, randomBaseForQ;

        while (true) {
            randomBaseForP = -1L * (MIN + random.nextInt(MAX));
            randomBaseForQ = -1L * (MIN + random.nextInt(MAX));

            if ((isPrime(p) && isPrime(q)) == true) {
                break;
            } else {
                p = randomBaseForP;
                q = randomBaseForQ;
            }
        }

        n = p * q;
        fN = (p - 1L) * (q - 1L);
        e = SearchE(fN);
        d = SearchD(e, fN);

        System.out.println("Шаг 1. Генерация простых чисел.");
        System.out.printf("p = %d\nq = %d\nn = %d\nf(n) = %d\nd = %d\ne = %d\n", p, q, n, fN, d, e);
    }

    /**
     * <code>SearchE</code>
     *
     * @param fN
     * @return
     */

    public static long SearchE(long fN) {
        long mod = -1L * (MIN + random.nextInt(MAX));
        System.out.println(mod);
        long E = fN / mod;
        while (checkGsd(E, fN) != 1L) {
            E++;
        }

        return E;
    }

    /**
     * <code>SearchD</code>
     *
     * @param E
     * @param fN
     * @return
     */

    public static long SearchD(long E, long fN) {

        double K = 1.0;
        long D = 1L;
        while (true) {
            System.out.println("D: " + D);
            D = (long) ((K * fN + 1L) / (double) E);
            if (D % 1L == 0L) break;
            else
                K++;
        }

        return D;
    }

    /**
     * <code>checkGsd</code>
     * <p>Для быстрого нахождения </p>
     *
     * @param E
     * @param fN
     * @return
     */
    public static long checkGsd(long E, long fN) {
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
     * <code>isPrime</code>
     * <p>Данный метод проверяет число, являетсли оно простым.</p>
     *
     * @param number - Число которое проверяем является ли оно простым
     * @return Возвращает истину или ложь
     */
    public static boolean isPrime(long number) {
        if (number == 1L) return false;
        for (long i = 2L; i * i <= number; i++)
            if (number % i == 0L)
                return false;
        return true;
    }

    public static long publicKey() {


        return 0L;
    }

    public static long privateKey() {

        return 0L;
    }

}