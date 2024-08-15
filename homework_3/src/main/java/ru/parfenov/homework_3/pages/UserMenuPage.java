package ru.parfenov.homework_3.pages;

import java.io.IOException;

public interface UserMenuPage {
    void run() throws IOException, InterruptedException;
    default int checkIfReadInt(String answer) throws IOException, InterruptedException {
        int result = 0;
        if (!answer.isEmpty()) {
            try {
                result = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Please enter the NUMBER!");
                run();
            }
        }
        return result;
    }
}