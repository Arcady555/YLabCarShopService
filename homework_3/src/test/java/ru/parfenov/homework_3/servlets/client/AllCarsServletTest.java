package ru.parfenov.homework_3.servlets.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.CarService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AllCarsServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    CarService carService = mock(CarService.class);
    AllCarsServlet servlet = new AllCarsServlet(carService);

    public AllCarsServletTest() throws Exception {
    }

    @Test
    @DisplayName("Вывод списка для зарегистрированного юзера")
    public void returns_list_of_all_cars_for_registered_user() throws Exception {
        User user = new User(1, UserRole.CLIENT, "testUser", "password", "contact", 0);
        List<Car> carList = List.of(new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW));

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(response.getWriter()).thenReturn(writer);

        when(carService.findAll()).thenReturn(carList);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Отказ не зарегистрированному юзеру")
    public void responds_with_status_401_and_no_registration_message_when_user_not_logged_in() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);


        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Отказ, если сессия не выдала пользователя")
    public void handles_scenario_where_session_does_not_contain_user_attribute() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Исключение если Writer не сработал")
    public void handles_ioexception_during_response_writing() throws Exception {
        User user = new User(1, UserRole.CLIENT, "testUser", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        doThrow(new IOException()).when(response).getWriter();


        assertThrows(IOException.class, () -> {
            servlet.doGet(request, response);
        });
    }
}