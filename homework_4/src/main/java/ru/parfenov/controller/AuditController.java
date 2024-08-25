package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.model.LineInLog;
import ru.parfenov.service.LogService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/audit")
public class AuditController {
    private final LogService logService;

    @Autowired
    public AuditController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/find-by-parameters")
    public ResponseEntity<List<LineInLog>> findUsersByParam(
            @RequestParam String userId,
            @RequestParam String action,
            @RequestParam String dateTimeFom,
            @RequestParam String dateTimeTo
    ) {
        List<LineInLog> logsRecords = logService.findByParameters(userId, action, dateTimeFom, dateTimeTo);
        return !logsRecords.isEmpty() ?
                new ResponseEntity<>(logsRecords, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}