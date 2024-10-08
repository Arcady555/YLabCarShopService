package ru.homework_5.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Модель строки, которая будет отправлена в лог -
 * время, id юзера, и название его операции
 */
@Entity
@Table(name = "log_records", schema = "cs_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineInLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date_time")
    private LocalDateTime time;
    @Column(name = "user_id")
    private String userId;
    private String action;
}