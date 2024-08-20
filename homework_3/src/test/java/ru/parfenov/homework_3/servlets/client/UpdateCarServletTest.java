package ru.parfenov.homework_3.servlets.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.parfenov.homework_3.dto.CarDTO;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.servlets.DelegatingServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateCarServletTest {
    UpdateCarServlet servlet = new UpdateCarServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    public UpdateCarServletTest() throws Exception {
    }

    @Test
    @DisplayName("Парсинг в CarDTO")
    public void test_parse_json_to_car_dto() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        String jsonInput = "{\"id\":1,\"ownerId\":1,\"brand\":\"Toyota\",\"model\":\"Corolla\",\"yearOfProd\":2020,\"price\":20000,\"condition\":\"New\"}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO carDTO = objectMapper.readValue(jsonInput, CarDTO.class);
        assertEquals(1, carDTO.getId());
        assertEquals(1, carDTO.getOwnerId());
        Assertions.assertEquals("Toyota", carDTO.getBrand());
    }

    @Test
    @DisplayName("Исключение при отсутствии сессии")
    public void test_null_session_user_attribute() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        verify(response).setStatus(401);
    }
}