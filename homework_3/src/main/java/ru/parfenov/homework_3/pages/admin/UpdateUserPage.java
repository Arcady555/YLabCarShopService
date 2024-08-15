package ru.parfenov.homework_3.pages.admin;

import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница обновления информации о юзере
 */
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
                service.delete(userId);
            } else {
                UserRole newUserRole = null;
                String newName = "";
                String newPassword = "";
                String newContactInfo = "";
                int newBuysAmount = 0;

                System.out.println(
                        "Do you want to change role?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
                    String answerRole = reader.readLine();
                    switch (answerRole) {
                        case "0" -> newUserRole = UserRole.ADMIN;
                        case "1" -> newUserRole = UserRole.MANAGER;
                        case "2" -> newUserRole = UserRole.CLIENT;
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
                    newName = reader.readLine();
                }
                System.out.println(
                        "Do you want to change password?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new password");
                    newPassword = reader.readLine();
                }
                System.out.println(
                        "Do you want to change contact info?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new contact info");
                    newContactInfo = reader.readLine();
                }
                System.out.println(
                        "Do you want to change buy amount?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new buy amount");
                    newBuysAmount = checkIfReadInt(reader.readLine());
                }
                service.update(userId, newUserRole, newName, newPassword, newContactInfo, newBuysAmount);
                Thread.sleep(5000);
            }
        } else {
            run();
        }
    }
}