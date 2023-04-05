package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sectionsParticipants")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SectionParticipant implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String sectionParticipantId;

    @ManyToOne
    @JoinColumn(name = "participantIdFK")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "sectionIdFK")
    private Section section;
}
