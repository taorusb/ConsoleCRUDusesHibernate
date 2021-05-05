package com.taorusb.consolecrunduseshibernate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        Facade facade = Facade.getInstance();

        facade.assembleApplication();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                facade.startApp(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("I/O error.");
        }
    }
}