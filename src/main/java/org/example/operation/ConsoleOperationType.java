package org.example.operation;

public enum ConsoleOperationType
{
    ACCOUNT_CREATE(1),
    SHOW_ALL_USERS(2),
    ACCOUNT_CLOSE(3),
    ACCOUNT_WITHDRAW(4),
    ACCOUNT_DEPOSIT(5),
    ACCOUNT_TRANSFER(6),
    USER_CREATE(7);

    private final int code;

    ConsoleOperationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ConsoleOperationType fromInt(int code)
    {
        for(ConsoleOperationType type : values()){
            if(type.getCode() == code) return type;
        }
        return null;
    }
}
