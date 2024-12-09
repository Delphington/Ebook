package org.example.controller;

import java.io.PrintStream;
import java.util.Scanner;

public interface Controller {
    Scanner scanner = new Scanner(System.in);
    PrintStream printStream = System.out;

    ActionType input();

    void showMenu();

    ActionType run();
}
