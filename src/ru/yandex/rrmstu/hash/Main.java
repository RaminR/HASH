package ru.yandex.rrmstu.hash;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * <h3>HASH</h3>
 * <p>
 * Создать консруктор который будет работать для клиента и для сервера
 * Чтобы через сервер можно было бы передать файл шифровоный и открытый ключ
 * Сохрангять файл с открыт ключем и зашифрованый файл
 * <p>
 * Изучить ЭЦП реализацию на Java билиотеке
 * Закинуть все на https://github.com/RaminR
 *
 * @author Rasuli Ramin
 * @version 1.0
 */

public class Main {


    public static void main(String[] args) {
        System.out.println("Вариант: 2\n");
        CreatingKey.setPQN();
    }
}

/**
 *
 *
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


/**
 * <code>ConvertDataToByte</code>
 * <p>Перевод данных в массив байтов.</p>
 */
class ConvertDataToByte {

    public static String message = null;
    public static String error = null;

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

    public static Random random = new Random();

    /**
     * <code>setPQN</code>
     * <p></p>
     */
    public static void setPQN() {

        int MIN = Integer.MIN_VALUE;
        int MAX = Integer.MAX_VALUE;
        long randomBaseForP, randomBaseForQ;

        while (true) {
            randomBaseForP = 7L;
            randomBaseForQ = 13L;
//            randomBaseForP = -1L * (MIN + random.nextInt(MAX));
//            randomBaseForQ = -1L * (MIN + random.nextInt(MAX));

            if ((isPrime(p) && isPrime(q)) == true) {
                break;
            } else {
                p = randomBaseForP;
                q = randomBaseForQ;
            }
        }
        n = p * q;
        fN = (p - 1L) * (q - 1L);
        d = SearchD(fN);
        e = SearchE(d, fN);

        while (checkGsd(fN, e) != 1) {
            e++;
        }

        System.out.println("Шаг 1. Генерация простых чисел.");
        System.out.printf("p = %d\nq = %d\nn = %d\nf(n) = %d\nd = %d\ne = %d\n", p, q, n, fN, d, e);
    }

    public static long SearchD(long m) {
        long d = m - 1L;

        for (long i = 2L; i <= m; i++)
            if ((m % i == 0L) && (d % i == 0L)) //если имеют общие делители
            {
                d--;
                i = 1L;
            }

        return d;
    }

    public static long checkGsd(long a, long b) {
        while (a != 0 && b != 0) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }
        return a + b;
    }

    public static long SearchE(long d, long m) {
        long e = 0L;

        while (true) {
            if ((e * d) % m == 1L)
                break;
            else
                e++;
        }

        return e;
    }

    /**
     * <code>isPrime</code>
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