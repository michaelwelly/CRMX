package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_time")
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "action")
    private Boolean action;

    @Column(name = "session")
    private String session;

    @Column(name = "date_time", nullable = false)
    private OffsetDateTime dateTime;

}
