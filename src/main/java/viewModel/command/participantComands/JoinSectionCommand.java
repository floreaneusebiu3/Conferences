package viewModel.command.participantComands;

import model.*;
import model.persistence.*;
import viewModel.VMParticipant;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class JoinSectionCommand implements Command {
    private VMParticipant vmParticipant;

    public JoinSectionCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }


    @Override
    public void execute() {
        List<Section> sectionList = new SectionPersistence().readAll();
        String searchedSectionName = (String) vmParticipant.getSectionsTable().get().getValueAt(vmParticipant.getSelectedRow().get(), 0);
        addParticipantToSection(vmParticipant.getLoggedUser(), getSectionIdFromSelectedRow(sectionList, searchedSectionName));
    }

    private String getSectionIdFromSelectedRow(List<Section> sections, String searchedSectionName) {
        for (Section section : sections) {
            if (section.getName().equals(searchedSectionName)) {
                return section.getSectionId();
            }
        }
        return null;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    private void addParticipantToSection(User user, String sectionId) {
        if (vmParticipant.getSelectedPresentationFile() == null) {
            showMessage("You have to upload a file before join a section of this conference");
            return;
        }
        Section section = (new SectionPersistence()).getSectionById(sectionId);
        if (isParticipantAtThisSection(user, section)) {
            showMessage("You have already joined this section");
            return;
        }
        Participant participant = getParticipant(user);
        PresentationFile presentationFile1 = getPresentationFile(section, vmParticipant.getSelectedPresentationFile(), participant);
        new PresentationFilePersistence().insert(presentationFile1);
        addSectionParticipantsRow(participant, section);
    }

    private Participant getParticipant(User user) {
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        for(Participant participant : participants) {
            if (participant.getName().equals(user.getFirstName()))
                return participant;
        }
        Participant participant = new Participant();
        participant.setName(user.getFirstName());
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setUser(user);
        participant.setApproved(false);
        if (user.getUserId() == null) {
            User newUser = new User();
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setMail(user.getMail());
            newUser.setRole("participant");
            newUser.setUsername("user" + new Random().nextInt(1000));
            newUser.setPassword("password" + new Random().nextInt(1000));
            newUser.setAge(0);
            newUser.setApproved(false);
            (new UserPersistence()).insert(newUser);
            participant.setUser(newUser);
        }
        (new ParticipantPersistence()).insert(participant);
        return participant;
    }

    private PresentationFile getPresentationFile(Section section, String pathToFile, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress(pathToFile);
        presentationFile.setParticipant(participant);
        presentationFile.setSection(section);
        return presentationFile;
    }

    private void addSectionParticipantsRow(Participant participant, Section section) {
        SectionParticipant sectionParticipant = getSectionParticipant(participant, section);
        new SectionParticipantPersistence().insert(sectionParticipant);
    }

    private SectionParticipant getSectionParticipant(Participant participant, Section section) {
        return new SectionParticipant(UUID.randomUUID().toString(), participant, section);
    }

    private boolean isParticipantAtThisSection(User user, Section section) {
        List<SectionParticipant> sectionParticipants = (new SectionParticipantPersistence()).readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getSection().getSectionId().equals(section.getSectionId()) && sectionParticipant.getParticipant().getName().equals(user.getFirstName()))
                return true;
        }
        return false;
    }
}
