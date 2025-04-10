package org.example.Service;

import org.example.operation.ConsoleOperationType;
import org.example.operation.OperationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class OperationsConsoleListener implements Runnable{

    private final Map<ConsoleOperationType, OperationCommand> commandMap = new HashMap<>();
    @Autowired
    public OperationsConsoleListener(List<OperationCommand> commands)
    {
        commands.forEach(cmd ->commandMap.put(cmd.getOperationType(), cmd));
    }

    @Override
    public void run() {
        System.out.println("Please enter one of operation type:");
        System.out.println("1) -ACCOUNT_CREATE");
        System.out.println("2) -SHOW_ALL_USERS");
        System.out.println("3) -ACCOUNT_CLOSE");
        System.out.println("4) -ACCOUNT_WITHDRAW");
        System.out.println("5) -ACCOUNT_DEPOSIT");
        System.out.println("6) -ACCOUNT_TRANSFER");
        System.out.println("7) -USER_CREATE");
        Scanner scanner = new Scanner(System.in);

        while (true) {

            int command = scanner.nextInt();

            ConsoleOperationType type = ConsoleOperationType.fromInt(command);
            OperationCommand cmd = commandMap.get(type);
            if (cmd != null) {
                cmd.execute();
            } else {
                System.out.println("Команда не найдена.");
            }
            System.out.println("Please enter one of operation type:");
            System.out.println("1) -ACCOUNT_CREATE");
            System.out.println("2) -SHOW_ALL_USERS");
            System.out.println("3) -ACCOUNT_CLOSE");
            System.out.println("4) -ACCOUNT_WITHDRAW");
            System.out.println("5) -ACCOUNT_DEPOSIT");
            System.out.println("6) -ACCOUNT_TRANSFER");
            System.out.println("7) -USER_CREATE");
        }
    }
}
