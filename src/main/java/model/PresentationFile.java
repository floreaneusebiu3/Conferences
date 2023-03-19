package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "presentationFiles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PresentationFile implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String presentationFileId;
    private String fileAddress;

    @ManyToOne
    @JoinColumn(name = "participantIdFK")
    private Participant participant;

}
