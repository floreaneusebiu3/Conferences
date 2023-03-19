package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "sections")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Section implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String sectionId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "conferenceIdFK")
    private Conference conference;

    @OneToMany(mappedBy = "section")
    private Set<SectionParticipant> sectionParticipants;

}
