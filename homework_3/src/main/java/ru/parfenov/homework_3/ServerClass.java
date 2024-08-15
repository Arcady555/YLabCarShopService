package ru.parfenov.homework_3;

import ru.parfenov.homework_3.pages.StartPage;
import ru.parfenov.homework_3.service.*;
import ru.parfenov.homework_3.store.*;

public class ServerClass {
    /**
     * Запуск хранилищ и сервисов
     * Вывод стартовой страницы
     */
    public void run() throws Exception {
        LogStore logStore = new LogStore();
        LogService logService = new LogService(logStore);

        UserStore userStore = new UserStoreJdbcImpl();
        UserService userService = new UserServiceConsoleImpl(userStore);

        CarStore carStore = new CarStoreJdbcImpl();
        CarService carService = new CarServiceConsoleImpl(carStore);

        OrderStore orderStore = new OrderStoreJdbcImpl();
        OrderService orderService = new OrderServiceConsoleImpl(orderStore);

        StartPage page = new StartPage(userService, carService, orderService, logService);
        page.run();
    }
}