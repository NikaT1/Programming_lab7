package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;
import sharedClasses.UserInput;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс для команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */

public class ExecuteScript extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Поле, содержащее список файлов.
     */
    private final HashSet<String> paths;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public ExecuteScript() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", 1, false);
        paths = new HashSet<>();
    }

    public HashSet<String> getPaths() {
        return paths;
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            if (!paths.add(getArgument())) {
                paths.clear();
                throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, так как выявлена рекурсия");
            } else {
                String path = getArgument();
                FileInputStream fileInputStream = new FileInputStream(path);
                BufferedInputStream file = new BufferedInputStream(fileInputStream);
                Scanner scanner = new Scanner(file);
                Scanner primaryScanner = ioForClient.getScanner();
                boolean printMessages = ioForClient.getPrintMessages();
                ioForClient.setPrintMessages(false);
                ioForClient.setScanner(scanner);
                UserInput userInput = new UserInput(ioForClient);
                while (scanner.hasNext()) {
                    String[] s = scanner.nextLine().split(" ");
                    if (commandsControl.getCommands().containsKey(s[0])) {
                        if (s[0].equals("save")) {
                            paths.clear();
                            throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, так как найдена недоступная команда");
                        } else {
                            Command currentCommand = commandsControl.getCommands().get(s[0]);
                            if (currentCommand.getAmountOfArguments() > 0) {
                                currentCommand.setArgument(s[1]);
                            }
                            if (currentCommand.isNeedCity()) {
                                currentCommand.setCity(userInput.readCity());
                            }
                            currentCommand.doCommand(ioForClient, commandsControl, priorityQueue);
                        }
                    } else {
                        paths.clear();
                        throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, так как найдена несуществующая команда");
                    }
                }
                paths.remove(path);
                ioForClient.setScanner(primaryScanner);
                ioForClient.setPrintMessages(printMessages);
                result.append("Скрипт исполнен.").append('\n');
            }
        } catch (FileNotFoundException e) {
            paths.clear();
            result.append("Файл не существует или не хватает прав на чтение файла").append('\n');
        }
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}
