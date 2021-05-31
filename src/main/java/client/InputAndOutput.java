package client;

import sharedClasses.utils.IOInterface;

import java.util.Scanner;

/**
 * Класс, осуществляющий ввод/вывод.
 */

public class InputAndOutput implements IOInterface {
    /**
     * Ввод пользователя.
     */
    private Scanner scanner;
    private boolean printMessages;

    /**
     * Конструктор.
     *
     * @param scanner       ввод пользователя
     * @param printMessages флаг, отвечающий за вид взаимодействия с пользователем.
     */
    public InputAndOutput(Scanner scanner, boolean printMessages) {
        this.scanner = scanner;
        this.printMessages = printMessages;
    }

    /**
     * Метод, устанавливающий вид взаимодействия с пользователем.
     *
     * @param printMessages флаг, отвечающий за вид взаимодействия с пользователем.
     */
    public void setPrintMessages(boolean printMessages) {
        this.printMessages = printMessages;
    }

    /**
     * Метод, возвращающий сканнер.
     *
     * @return сканнер.
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Метод, устанавливающий сканнер.
     *
     * @param scanner сканнер.
     */
    public void setScanner(Scanner scanner) {

        this.scanner = scanner;
    }

    /**
     * Метод, считывающий ответ пользвателя.
     *
     * @param message сообщение пользователю.
     * @return ответ пользователя.
     */
    public boolean readAnswer(String message) {
        String s;
        System.out.println(message);
        while (true) {
            if (!scanner.hasNextLine()) System.exit(1);
            s = scanner.nextLine();
            switch (s) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Неверный ввод! Введите yes/no");
                    break;
            }
        }
    }

    /**
     * Метод, отвечающий за вывод строки на экран.
     *
     * @param s строка для вывода.
     */
    public void output(String s) {
        System.out.println(s);
    }

    public String inputLine() {
        return scanner.nextLine();
    }

    public boolean getPrintMessages() {
        return printMessages;
    }
}
