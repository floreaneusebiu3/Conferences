package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conferenceParticipants")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConferenceParticipant implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String conferenceParticipantId;

    @ManyToOne
    @JoinColumn(name = "participantIdFK")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "conferenceIdFK")
    private Conference conference;
}
