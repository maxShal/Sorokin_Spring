package org.example.operation;

public interface OperationCommand
{
    void execute();
    ConsoleOperationType getOperationType();
}
