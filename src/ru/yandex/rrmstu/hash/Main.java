package ru.yandex.rrmstu.hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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


    Main() {

    }

    Main(String pathToFile, String pathToPublicKey) {

    }

    Main(String localhost) {

    }

    public static void main(String[] args) {

        System.out.println("Вариант: 2");
        System.out.println(Arrays.toString(ConvertDataToByte.getArrayForFile("D://message.txt")));

        WorkingWithFiles.SaveFile("Test.txt", ConvertDataToByte.arrayForFile);

        WorkingWithFiles.OpenFile("D://mыessage.txt");
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
     * <p>Данный метод работает только с текстовыми файлами!</p>
     *
     * @param pathToFile
     */

    public static void OpenFile(String pathToFile) {

        try (InputStream in = Files.newInputStream(Paths.get(pathToFile));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null)
                System.out.println(line);

        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    /**
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

    public static long p = 0L;
    public static long q = 0L;
    public static long n = 0L;

    public static void setPQN() {


        p = 1L;
        System.out.printf("P = %d\nQ = %d\nN = %d\n", p, q, n);
    }

    /**
     * <code>isPrime</code>
     *
     * @param number - Число которое проверяем является ли оно простым
     * @return Возвращает истину или ложь
     */

    public static boolean isPrime(long number) {
        if (number == 1L) return false;
        else
            for (long i = 2L; i * i <= Math.sqrt(number); i++)
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