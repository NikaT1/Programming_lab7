package com.console_project.server.configuration;

import com.console_project.server.command.CommandInvoker;
import com.console_project.server.command.ExitHandler;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.command.collection_command.*;
import com.console_project.server.command.user_command.AddUserCommand;
import com.console_project.server.command.user_command.CheckUserCommand;
import com.console_project.server.file_util.FileReaderWriter;
import com.console_project.server.service.DataService;
import com.console_project.server.service.UserService;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.model.City;
import com.console_project.shared.model.User;

public class CommandConfiguration {
    public static CommandInvoker<City> getCommandsInvoker(DataService<City> dataService, UserService userService, FileReaderWriter<City> fileReaderWriter) {
        CommandInvoker<City> commandInvoker = new CommandInvoker<>();
        commandInvoker.registerCommand(new AddCommand<>(dataService, TypeOfCommand.ADD));
        commandInvoker.registerCommand(new AddIfMaxCommand<>(dataService, TypeOfCommand.ADD_IF_MAX));
        commandInvoker.registerCommand(new AddIfMinCommand<>(dataService, TypeOfCommand.ADD_IF_MIN));
        commandInvoker.registerCommand(new AverageOfMetersAboveSeaLevelCommand<>(dataService,
                TypeOfCommand.AVERAGE_OF_METERS_ABOVE_SEA_LEVEL));
        commandInvoker.registerCommand(new ClearCommand<>(dataService, TypeOfCommand.CLEAR));
        commandInvoker.registerCommand(new ExecuteScriptCommand<>(dataService, TypeOfCommand.EXECUTE_SCRIPT));
        commandInvoker.registerCommand(new GroupCountingByMetersAboveSeaLevelCommand<>(dataService,
                TypeOfCommand.GROUP_COUNTING_BY_METERS_ABOVE_SEA_LEVEL));
        commandInvoker.registerCommand(new HelpCommand<>(TypeOfCommand.HELP));
        commandInvoker.registerCommand(new InfoCommand<>(dataService, TypeOfCommand.INFO));
        commandInvoker.registerCommand(new PrintAscendingCommand<>(dataService, TypeOfCommand.PRINT_ASCENDING));
        commandInvoker.registerCommand(new RemoveByIdCommand<>(dataService, TypeOfCommand.REMOVE_BY_ID));
        commandInvoker.registerCommand(new RemoveHeadCommand<>(dataService, TypeOfCommand.REMOVE_HEAD));
        commandInvoker.registerCommand(new SaveCommand<>(fileReaderWriter, dataService,
                TypeOfCommand.SAVE));
        commandInvoker.registerCommand(new ShowCommand<>(dataService, TypeOfCommand.SHOW));
        commandInvoker.registerCommand(new UpdateIdCommand<>(dataService, TypeOfCommand.UPDATE_ID));
        commandInvoker.registerCommand(new AddUserCommand<>(userService, TypeOfCommand.ADD_USER));
        commandInvoker.registerCommand(new CheckUserCommand<>(userService, TypeOfCommand.CHECK_USER));
        return commandInvoker;
    }

    public static ExitHandler getExitHandler(CommandInvoker commandInvoker) {
        return new ExitHandler(commandInvoker.getCommand(TypeOfCommand.SAVE),
                new UserRequest("", "save", "", new User()));
    }
}
