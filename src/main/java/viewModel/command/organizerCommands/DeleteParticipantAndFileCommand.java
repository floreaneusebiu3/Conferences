package viewModel.command.organizerCommands;

import model.Participant;
import model.PresentationFile;
import model.SectionParticipant;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.util.List;

public class DeleteParticipantAndFileCommand implements Command {
    private VMOrganizer vmOrganizer;

    public DeleteParticipantAndFileCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        int index = vmOrganizer.getSelectedRowFromParticipantsFilesTable().get();
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        Participant participantToBeDeleted = getParticipantByIndex(index, participants);
        if (participantToBeDeleted != null) {
            deleteAllSectionParticipantsOfThisParticipant(participantToBeDeleted);
            deleteAllPresentationsFilesOfThisParticipant(participantToBeDeleted);
            (new ParticipantPersistence()).delete(participantToBeDeleted.getParticipantId());
        }
        PresentationFile presentationFile = getPresentationFileByIndex(index, participants);
        if (presentationFile != null) {
            (new PresentationFilePersistence()).delete(presentationFile.getPresentationFileId());
        }
    }

    private void deleteAllSectionParticipantsOfThisParticipant(Participant participant) {
        List<SectionParticipant> sectionParticipants = (new SectionParticipantPersistence()).readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                (new SectionParticipantPersistence()).delete(sectionParticipant.getSectionParticipantId());
            }
        }
    }

    private void deleteAllPresentationsFilesOfThisParticipant(Participant participant) {
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                (new PresentationFilePersistence()).delete(presentationFile.getPresentationFileId());
            }
        }
    }

    private Participant getParticipantByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return participant;
            } else {
                k++;
                for (PresentationFile presentationFile : (new ParticipantPersistence()).getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return null;
                    else
                        k++;
                }
            }
        }
        return null;
    }

    private PresentationFile getPresentationFileByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return null;
            } else {
                k++;
                for (PresentationFile presentationFile : (new ParticipantPersistence()).getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return presentationFile;
                    else
                        k++;
                }
            }
        }
        return null;
    }
}
