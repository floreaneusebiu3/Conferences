package viewModel.command.organizerCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import model.Participant;
import model.PresentationFile;
import model.persistence.PresentationFilePersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SerializeDataCommand implements Command {
    private VMOrganizer vmOrganizer;

    public SerializeDataCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        List<Participant> participants = getParticipantsForSelectedSection();
        writeDataInJsonFile(participants);
        writeDataInCsvFile(participants);
        try {
            writeDataInXML(participants);
            writeDataInTxt(participants);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void writeDataInCsvFile(List<Participant> participants) {
        List<String[]> values = new ArrayList<>();
        try (CSVWriter writer = new CSVWriter(new FileWriter("presentationFiles.csv"))) {
            for (Participant participant : participants) {
                List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
                for (PresentationFile presentationFile : presentationFiles) {
                    Field[] fields = PresentationFile.class.getDeclaredFields();
                    String[] row = new String[fields.length];
                    int index = 0;
                    for (Field field : fields) {
                        if (index > 1) {
                            row[index++] = participant.getName();
                            break;
                        }
                        field.setAccessible(true);
                        row[index++] = String.valueOf(field.get(presentationFile));
                    }
                    values.add(row);
                }
            }
            writer.writeAll(values);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void writeDataInJsonFile(List<Participant> participants) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PresentationFile> presentationFilesToBeSerialized = new ArrayList<>();
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            presentationFilesToBeSerialized.addAll(presentationFiles);
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("presentationFiles.json"), presentationFilesToBeSerialized);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeDataInXML(List<Participant> participants) throws IOException, IllegalAccessException {
        FileWriter file = new FileWriter("presentationFiles.xml");
        StringBuilder sb = new StringBuilder();
        List<String[]> values = new ArrayList<>();
        sb.append("<?xml version=\"1.0\" ?>\n\n");
        sb.append("<presentationFiles>\n\n");
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            for (PresentationFile presentationFile : presentationFiles) {
                Field[] fields = PresentationFile.class.getDeclaredFields();
                String[] row = new String[fields.length];
                int index = 0;
                for (Field field : fields) {
                    if (index > 1) {
                        row[index++] = participant.getName();
                        break;
                    }
                    field.setAccessible(true);
                    row[index++] = String.valueOf(field.get(presentationFile));
                }
                values.add(row);
            }
        }
        for (String[] data : values) {
            sb.append("<presentationFile id=\"" + data[0] + "\">\n");
            sb.append("<name>" + data[1] + "</name>\n");
            sb.append("</presentationFile>\n\n");
        }
        sb.append("</presentationFiles>");
        file.write(sb.toString());
        file.close();
    }

    private void writeDataInTxt(List<Participant> participants) throws FileNotFoundException {
  PrintWriter printWriter = new PrintWriter("presentationFiles.txt");
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            for (PresentationFile presentationFile : presentationFiles) {
                StringBuilder row = new StringBuilder();
                row.append(presentationFile.getPresentationFileId())
                        .append(" ")
                        .append(presentationFile.getFileAddress())
                        .append(" ")
                        .append(presentationFile.getParticipant().getName());
                printWriter.println(row);
                printWriter.flush();
            }
        }
    }

    private List<Participant> getParticipantsForSelectedSection() {
        String section = vmOrganizer.getSectionField().get();
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        List<Participant> participants = new ArrayList<>();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getSection().getName().equals(section)) {
                participants.add(presentationFile.getParticipant());
            }
        }
        return participants;
    }
}
