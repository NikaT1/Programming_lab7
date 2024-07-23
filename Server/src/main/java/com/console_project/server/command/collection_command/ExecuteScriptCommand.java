package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */

@AllArgsConstructor
public class ExecuteScriptCommand<T> implements Command<T> { //todo add execute script logic

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }
    public CommandResponse execute(UserRequest<T> request) {
//        try {
//            if (!paths.add(inputAndOutput.getArgument())) {
//                throw new InvalidAlgorithmParameterException();
//            } else {
//                String path = inputAndOutput.getArgument();
//                FileInputStream fileInputStream = new FileInputStream(path);
//                BufferedInputStream file = new BufferedInputStream(fileInputStream);
//                Scanner scanner = new Scanner(file);
//                Scanner primaryScanner = inputAndOutput.getScanner();
//                boolean printMessages = inputAndOutput.getPrintMessages();
//                inputAndOutput.setPrintMessages(false);
//                inputAndOutput.setScanner(scanner);
//                UserInput userInput = new UserInput(inputAndOutput, commandsControl, priorityQueue);
//                userInput.input();
//                paths.remove(path);
//                inputAndOutput.setScanner(primaryScanner);
//                inputAndOutput.setPrintMessages(printMessages);
//            }
//        } catch (FileNotFoundException e) {
//            inputAndOutput.output("Файл не существует или не хватает прав на чтение файла");
//        }
        return new CommandResponse("","");
    }
}
