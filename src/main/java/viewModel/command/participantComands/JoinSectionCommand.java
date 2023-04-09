package viewModel.command.participantComands;

import model.*;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.SectionPersistence;
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
        if (vmParticipant.getSelectedRow().get() >= sectionList.size() || vmParticipant.getSelectedRow().get() < 0) {
            showMessage("You must select a section");
            return;
        }
        addParticipantToSection(vmParticipant.getLoggedUser(), sectionList.get(vmParticipant.getSelectedRow().get()).getSectionId());
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
        participant.setRegistered(false);
        if (user.getUserId() == null) {
            User newUser = new User();
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setMail(user.getMail());
            newUser.setRole("participant");
            newUser.setUsername("user" + new Random().nextInt(1000));
            newUser.setPassword("password" + new Random().nextInt(1000));
            newUser.setAge(0);
            newUser.setApproved(false);
            participant.setUser(user);
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
