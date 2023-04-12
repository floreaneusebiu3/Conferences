package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PresentationFile implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String presentationFileId;
    private String fileAddress;

    @ManyToOne
    @JoinColumn(name = "participantIdFK")
    private Participant participant;

    @OneToOne
    @JoinColumn(name = "sectionIdFK")
    private Section section;

    public String getPresentationFileId() {
        return presentationFileId;
    }

    public void setPresentationFileId(String presentationFileId) {
        this.presentationFileId = presentationFileId;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @JsonIgnore
    public Section getSection() {
        return section;
    }

    @JsonIgnore
    public void setSection(Section section) {
        this.section = section;
    }
}
