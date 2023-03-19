package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "conferences")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Conference implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String conferenceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminIdFK")
    private User admin;

    @OneToMany(mappedBy = "conference")
    private Set<ConferenceParticipant> conferenceParticipants;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduleIdFK")
    private Schedule schedule;

    @OneToMany(mappedBy = "conference")
    private Set<Section> sections;




}
