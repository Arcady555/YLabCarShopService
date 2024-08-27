package ru.parfenov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.model.LineInLog;
import ru.parfenov.service.impl.LogServiceServletImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/audit")
public class AuditController {
    private final LogServiceServletImpl logServiceServletImpl;

    @Autowired
    public AuditController(LogServiceServletImpl logServiceServletImpl) {
        this.logServiceServletImpl = logServiceServletImpl;
    }

    /**
     * Данный метод, доступный только админу(через фильтр сервлетов), позволяет посмотреть те логи,
     * которые сохранены в БД, отсортировав их по параметрам(указывать можно не все):
     * @param userId ID юзера
     * @param action его действие (название метода в блоке SERVICE)
     * @param dateTimeFrom  с какого времени
     * @param dateTimeTo по какое время
     * @return ответ сервера в виде требуемого списка
     */
    @GetMapping("/find-by-parameters")
    public ResponseEntity<List<LineInLog>> findUsersByParam(
            @RequestParam String userId,
            @RequestParam String action,
            @RequestParam String dateTimeFrom,
            @RequestParam String dateTimeTo
    ) {
        List<LineInLog> logsRecords = logServiceServletImpl.findByParameters(userId, action, dateTimeFrom, dateTimeTo);
        return !logsRecords.isEmpty() ?
                new ResponseEntity<>(logsRecords, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}