package server;

import sharedClasses.IOInterface;
import sharedClasses.Serialization;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class IOForClient implements IOInterface {
    /**
     * Ввод пользователя.
     */
    private Scanner scanner;
    private int port;
    private InetAddress addr;

    private DatagramSocket datagramSocket;
    /**
     * Флаг, отвечающий за вид взаимодействия с пользователем.
     */
    private boolean printMessages;

    /**
     * Конструктор.
     *
     * @param scanner       ввод пользователя
     * @param printMessages флаг, отвечающий за вид взаимодействия с пользователем.
     */
    public IOForClient(Scanner scanner, boolean printMessages) {
        this.scanner = scanner;
        this.printMessages = printMessages;
    }

    public IOForClient(boolean printMessages) {
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

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    /**
     * Метод, возвращающий сканнер.
     *
     * @return сканнер.
     */
    public Scanner getScanner() {
        if (scanner != null) return scanner;
        else return null;
    }

    /**
     * Метод, устанавливающий сканнер.
     *
     * @param scanner сканнер.
     */
    public void setScanner(Scanner scanner) {

        this.scanner = scanner;
    }

    public byte[] input(byte[] bytes) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(bytes, 100000);
        datagramSocket.receive(datagramPacket);
        port = datagramPacket.getPort();
        addr = datagramPacket.getAddress();
        return bytes;
    }

    /**
     * Метод, отвечающий передачу информации пользователю.
     *
     * @param commandResult информация для вывода.
     */
    public void output(byte[] commandResult) throws IOException {
        DatagramPacket result = new DatagramPacket(commandResult, commandResult.length, addr, port);
        datagramSocket.send(result);
    }

    public void output(String s) {
        byte[] bytes = Serialization.serializeData(s);
        DatagramPacket result = new DatagramPacket(bytes, bytes.length, addr, port);
        try {
            datagramSocket.send(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getPrintMessages() {
        return printMessages;
    }

}
