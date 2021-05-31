package sharedClasses.utils;

import java.util.Scanner;

public interface IOInterface {
    Scanner getScanner();

    void setScanner(Scanner scanner);

    void output(String s);

    boolean getPrintMessages();

    void setPrintMessages(boolean printMessages);
}
