package ru.parfenov.homework_1.server.pages.admin;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.UserService;
import ru.parfenov.homework_1.server.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateUserPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateUserPage(UserService service) {
        this.service = service;
    }

    public void run() throws IOException, InterruptedException {
        System.out.println("Enter the id of the desired user");
        int userId = 0;
        try {
            userId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        User user = service.findByIdForAdmin(userId);
        if (user != null) {
            Utility.printUser(user);
            System.out.println("Do you want to delete the user?" + System.lineSeparator() + "0 - yes, another key - no");
            String answerDelete = reader.readLine();
            if (answerDelete.equals("0")) {
                service.delete(user);
            } else {
                System.out.println(
                        "Do you want to change role?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerRole = reader.readLine();
                if (answerRole.equals("0")) {
                    System.out.println("Enter new role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
                    String answer = reader.readLine();
                    switch (answer) {
                        case "0" -> user.setRole(UserRoles.ADMIN);
                        case "1" -> user.setRole(UserRoles.MANAGER);
                        case "2" -> user.setRole(UserRoles.CLIENT);
                        default -> {
                            System.out.println("Please enter correct" + System.lineSeparator());
                            run();
                        }
                    }
                }
                System.out.println(
                        "Do you want to change name?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no"
                );
                String answerBrand = reader.readLine();
                if (answerBrand.equals("0")) {
                    System.out.println("Enter new name");
                    String newName = reader.readLine();
                    user.setName(newName);
                }
                System.out.println(
                        "Do you want to change password?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerPassword = reader.readLine();
                if (answerPassword.equals("0")) {
                    System.out.println("Enter new password");
                    String newPassword = reader.readLine();
                    user.setPassword(newPassword);
                }
                System.out.println(
                        "Do you want to change contact info?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerContactInfo = reader.readLine();
                if (answerContactInfo.equals("0")) {
                    System.out.println("Enter new contact info");
                    String newContactInfo = reader.readLine();
                    user.setContactInfo(newContactInfo);
                }
                System.out.println(
                        "Do you want to change buy amount?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerBuyAmount = reader.readLine();
                if (answerBuyAmount.equals("0")) {
                    System.out.println("Enter new buy amount");
                    int newBuyAmount = Integer.parseInt(reader.readLine());
                    user.setBuysAmount(newBuyAmount);
                }
                service.update(user);
                Thread.sleep(5000);
            }
        } else {
            System.out.println("User not found!");
            run();
        }
    }
}