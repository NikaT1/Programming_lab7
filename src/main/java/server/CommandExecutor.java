package server;

import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.commands.Command;
import sharedClasses.commands.CommandsControl;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class CommandExecutor implements Runnable {
    private final Command command;
    private final IOForClient ioForClient;
    private final CommandsControl commandsControl;
    private final PriorityQueueStorage priorityQueue;
    private final ExecutorService newCachedThreadPool;

    public CommandExecutor(Command command, IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        this.command = command;
        this.priorityQueue = priorityQueue;
        this.ioForClient = ioForClient;
        this.commandsControl = commandsControl;
        newCachedThreadPool = Executors.newCachedThreadPool();
    }


    @Override
    public void run() {
        try {
            byte[] commandResult = command.doCommand(ioForClient, new CommandsControl(command.getUser()), priorityQueue);
            boolean flag = newCachedThreadPool.submit(() -> ioForClient.output(commandResult)).get();
            if (flag) Server.log.log(Level.INFO, "Отправка результата пользователю успешно завершена");
            else Server.log.log(Level.SEVERE, "Возникла ошибка при попытке отправки результата пользователю.");
        } catch (NoSuchElementException | NumberFormatException e) {
            ioForClient.output("Неверный формат введенных данных");
        } catch (Exception e) {
            Server.log.log(Level.SEVERE, "Возникла ошибка при попытке исполнения команды.");
        }

    }
}
