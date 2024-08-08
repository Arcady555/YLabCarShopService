package ru.parfenov.homework_1.server;

import ru.parfenov.homework_1.server.pages.StartPage;
import ru.parfenov.homework_1.server.service.*;
import ru.parfenov.homework_1.server.store.*;

import java.io.IOException;

public class ServerClass {
    /**
     * Запуск хранилищ и сервисов
     * Вывод стартовой страницы
     */
    public void run() throws IOException, InterruptedException {
        LogStore logStore = new LogStore();
        LogService logService = new LogService(logStore);

        UserStore userStore = new UserStoreConsoleImpl();
        UserService userService = new UserServiceConsoleImpl(userStore);

        CarStore carStore = new CarStoreConsoleImpl();
        CarService carService = new CarServiceConsoleImpl(carStore);

        OrderStore orderStore = new OrderStoreConsoleImpl();
        OrderService orderService = new OrderServiceConsoleImpl(orderStore);

        StartPage page = new StartPage(userService, carService, orderService, logService);
        page.run();
    }
}