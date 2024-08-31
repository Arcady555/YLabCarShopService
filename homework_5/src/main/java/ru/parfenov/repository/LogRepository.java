package ru.parfenov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.parfenov.model.LineInLog;

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
    @Query(
            "from LineInLog l where l.userId = ?1" +
                    " and l.action = ?2" +
                    " and l.time > ?3" +
                    " and l.time < ?4"
    )
    List<LineInLog> findByParameters(int userId, String action, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
