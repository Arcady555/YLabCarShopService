package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.parfenov.model.LineInLog;
import ru.parfenov.repository.impl.LogRepositoryJdbcImpl;
import ru.parfenov.service.LogService;

import static ru.parfenov.utility.Utility.getIntFromString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class LogServiceServletImpl implements LogService {
    private final LogRepositoryJdbcImpl repo;

    @Autowired
    public LogServiceServletImpl(LogRepositoryJdbcImpl repo) {
        this.repo = repo;
    }

    @Override
    public void saveLineInLog(LocalDateTime dateTime, int userId, String action) {
        log.info("date time : {}, user id : {}, action : {}", dateTime, userId, action);
        LineInLog lineInLog = new LineInLog(
                0L,
                dateTime.truncatedTo(ChronoUnit.MINUTES),
                Integer.toString(userId),
                action
        );
        repo.create(lineInLog);
    }

    @Override
    public List<LineInLog> findByParameters(String userIdStr, String action, String dateTimeFomStr, String dateTimeToStr) {
        int userId = getIntFromString(userIdStr);
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeFomStr);
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeToStr);
        return repo.findByParameters(userId, action, dateTimeFrom, dateTimeTo);
    }
}