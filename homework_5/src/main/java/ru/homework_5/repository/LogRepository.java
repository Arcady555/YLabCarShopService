package ru.homework_5.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.homework_5.model.LineInLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Служит для передачи запросов в хранилище логов о действиях юзеров
 */
@Repository
public interface LogRepository extends CrudRepository<LineInLog, Integer> {

    /**
     * Вывод записей лога, которые ушли в базу данных, по параметрам
     *
     * @param userId       ID юзера
     * @param action       действие юзера
     * @param dateTimeFrom с какой даты-времени искать логи
     * @param dateTimeTo   по какую дату-время искать логи
     * @return список строк-записей логов
     */

    @Query("SELECT l FROM LineInLog l WHERE " +
            "(:userId = '' OR l.userId = :userId) AND " +
            "(:action = '' OR l.action LIKE CONCAT ('%', :action, '%')) AND" +
            "(CAST(:dateTimeFrom AS time) IS NULL OR l.time > :dateTimeFrom) AND" +
            "(CAST(:dateTimeTo AS time) IS NULL OR l.time < :dateTimeTo)"
    )
    List<LineInLog> findByParameters(
            @Param("userId") String userId,
            @Param("action") String action,
            @Param("dateTimeFrom") LocalDateTime dateTimeFrom,
            @Param("dateTimeTo") LocalDateTime dateTimeTo
    );
}