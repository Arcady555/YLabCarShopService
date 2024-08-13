package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.UserService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateUserPage implements UserMenuPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateUserPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter the id of the desired user");
        int userId = checkIfReadInt(reader.readLine());
        User user = service.findByIdForAdmin(userId);

        if (user != null) {
            Utility.printUser(user);
            System.out.println("Do you want to delete the user?" + System.lineSeparator() + "0 - yes, another key - no");
            if ("0".equals(reader.readLine())) {
                service.delete(user);
            } else {
                System.out.println(
                        "Do you want to change role?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
                    String answer = reader.readLine();
                    switch (answer) {
                        case "0" -> user.setRole(UserRole.ADMIN);
                        case "1" -> user.setRole(UserRole.MANAGER);
                        case "2" -> user.setRole(UserRole.CLIENT);
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
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new name");
                    String newName = reader.readLine();
                    user.setName(newName);
                }
                System.out.println(
                        "Do you want to change password?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new password");
                    String newPassword = reader.readLine();
                    user.setPassword(newPassword);
                }
                System.out.println(
                        "Do you want to change contact info?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new contact info");
                    String newContactInfo = reader.readLine();
                    user.setContactInfo(newContactInfo);
                }
                System.out.println(
                        "Do you want to change buy amount?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new buy amount");
                    int newBuyAmount = checkIfReadInt(reader.readLine());
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