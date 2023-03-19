package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Schedule implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String scheduleId;
    private LocalDate date;
    private int startHour;
    private int endHour;

}
