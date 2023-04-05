package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Participant implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String participantId;
    private String name;
    private boolean registered;

    @OneToOne
    @JoinColumn(name = "userIdFK")
    private User user;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private Set<PresentationFile> presentationFiles;

    @OneToMany(mappedBy = "participant")
    private Set<SectionParticipant> sectionParticipants;
}
