package org.example;


import org.example.Service.OperationsConsoleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");

        OperationsConsoleListener console = context.getBean(OperationsConsoleListener.class);
        console.run();

        context.close();
    }
}
