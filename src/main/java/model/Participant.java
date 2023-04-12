package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Participant implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String participantId;
    private String name;
    private boolean approved;

    @OneToOne
    @JoinColumn(name = "userIdFK")
    private User user;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private Set<PresentationFile> presentationFiles;

    @OneToMany(mappedBy = "participant")
    private Set<SectionParticipant> sectionParticipants;

    public String getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPresentationFiles(Set<PresentationFile> presentationFiles) {
        this.presentationFiles = presentationFiles;
    }

    public void setSectionParticipants(Set<SectionParticipant> sectionParticipants) {
        this.sectionParticipants = sectionParticipants;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonIgnore
    public Set<PresentationFile> getPresentationFiles() {
        return presentationFiles;
    }

    @JsonIgnore
    public Set<SectionParticipant> getSectionParticipants() {
        return sectionParticipants;
    }

}

