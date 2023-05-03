package utils;


import controller.Observer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Language {
    private int currentLanguage;
    private List<Observer> observers = new ArrayList<>();
    private List<String> loginUsernameTexts = List.of(new String[]{"username", "utilizator", "benutzer"});
    private List<String> loginPasswordTexts = List.of(new String[]{"password", "parola", "passwort"});
    private List<String> loginButtonTexts = List.of(new String[]{"login", "logare", "anmeldung"});
    private List<String> loginEnterAsGuestButtonTexts = List.of(new String[]{"enter as guest", "intra fara cont", "als Gast eintreten"});
    private List<String> loginFailMessageTexts = List.of(new String[]{"wrong credentials!", "credentiale gresite!", "falsche anmeldeinformationen"});


    private List<String[]> participantFilesHeadTexts = List.of(new String[][]{new String[]{"FILE", "PARTICIPANT", "SECTION"}, new String[]{"FISIER", "PARTICIPANT", "SECTIUNE"},
            new String[]{"DATEI", "TEILNEHMER", "ABSCHNITT"}});
    private List<String[]> participantJoinedSectionsHeadTexts = List.of(new String[][]{new String[]{"SECTION"}, new String[]{"SECTIUNE"}, new String[]{"ABSCHNITT"}});
    private List<String[]> participantSectionsHeadTexts = List.of(new String[][]{new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"},
            new String[]{"SECTIUNE", "DATA", "ORA START", "ORA FINALA"}, new String[]{"ABSCHNITT", "DATUM", "STARTSTUNDE", "ENDE STUNDE"}});
    private List<String> participantSectionsTableTitleTexts = List.of(new String[]{"SECTIONS", "SECTIUNI", "ABSCHNITT"});
    private List<String> participantJoinedSectionsTableTitleTexts = List.of(new String[]{"JOINED SECTIONS", "SECTIUNI CURENTE", "AKTUELLE ABSCHNITTE"});
    private List<String> participantRegisteredConditionTexts = List.of(new String[]{"(only registered participants)", "doar participantii logati", "(nur angemeldete Teilnehmer)"});
    private List<String> participantJoinSectionButtonTexts = List.of(new String[]{"JOIN", "ALATURA-TE", "VERBINDEN"});
    private List<String> participantSeeConferenceButtonTexts = List.of(new String[]{"SEE CONFERENCE VOLUME", "VEZI VOLUMUL CONFERINTEI", "VERBINDEN"});
    private List<String> participantOpenFileButtonTexts = List.of(new String[]{"(OPEN FILE", "DESCHIDE FISIER", "DATEI ÖFFNEN"});
    private List<String> participantUploadFileMessageTexts = List.of(new String[]{"you have to upload a file before join a section of this conference",
            "trebuie sa incarci un fisier inainte de a te alatura la aceasta conferinta", "sie müssen eine Datei hochladen, bevor Sie einem Abschnitt dieser Konferenz beitreten können"});
    private List<String> participantAlreadyJoinedSectionMessageTexts = List.of(new String[]{"you have already joined this section",
            "sunteti deja inrolat la aceasta sectiune", "Sie sind diesem Abschnitt bereits beigetreten"});
    private List<String> participantMustSelectFileMessageTexts = List.of(new String[]{"you must select a file",
            "trebuie sa seelctati un fisier", "Sie müssen eine Datei auswählen"});


    private List<String[]> organizerParticipantsHeadTexts = List.of(new String[][]{new String[]{"PARTICIPANT", "APPROVED", "FILE", "SECTION"},
            new String[]{"PARTICIPANT", "APROBAT", "FISIER", "SECTIUNE"}, new String[]{"TEILNEHMER", "GENEHMIGT", "DATEI", "ABSCHNITT"}});
    private List<String[]> organizerFilteredParticipantsHeadTexts = List.of(new String[][]{new String[]{"PARTICIPANT", "SECTION"},
            new String[]{"PARTICIPANT", "SECTIUNE"}, new String[]{"TEILNEHMER", "ABSCHNITT"}});
    private List<String[]> organizerSectionsHeadTexts = List.of(new String[][]{new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"},
            new String[]{"SECTIUNE", "DATA", "ORA START", "ORA FINALA"}, new String[]{"ABSCHNITT", "DATUM", "STARTSTUNDE", "ENDE STUNDE"}});
    private List<String> organizerSectionLabelTexts = List.of(new String[]{"section", "sectie", "abschnitt"});
    private List<String> organizerDateLabelTexts = List.of(new String[]{"DATA dd/MM/uuuu", "DATA dd/MM/uuuu", "DATUM dd/MM/uuuu"});
    private List<String> organizerStartHourLabelTexts = List.of(new String[]{"(start", "start", "start"});
    private List<String> organizerEndHourLabelTexts = List.of(new String[]{"end", "final", "ende"});
    private List<String> organizerFilterButtonTexts = List.of(new String[]{"filter", "filtreaza", "filter"});
    private List<String> organizerAddScheduleTexts = List.of(new String[]{"add schedule", "adauga program", "zeitplan hinzufügen"});
    private List<String> organizerMustSelectExistingSectionMessageTexts = List.of(new String[]{"you must select an existing section",
            "trebuie sa selectati o sectie valida", "sie müssen einen vorhandenen Abschnitt auswählen"});
    private List<String> organizerMustInsertValidDateMessageTexts = List.of(new String[]{"you must insert a valid date",
            "trebuie sa introduceti o data valida ", "sie müssen ein gültiges Datum eingeben"});
    private List<String> organizerIncorrectRowMessageTexts = List.of(new String[]{"you haven't chosen correct row",
            "nu ati ales un rand valid", "sie haben nicht die richtige Zeile ausgewählt"});
    private List<String> organizerMustSelectParticipantMessageTexts = List.of(new String[]{"you must select a participant or a file",
            "trebuie sa selectati un participant sau un fisier", "sie müssen einen Teilnehmer oder eine Datei auswählen"});


    private List<String[]> adminUsersHeadTexts = List.of(new String[][]{new String[]{"APPROVED", "FIRST NAME", "LAST NAME", "AGE", "MAIL", "ROLE", "USERNAME", "PASSWORD"},
            new String[]{"APROBAT", "PRENUME", "NUME", "VARSTA", "MAIL", "ROL", "UTILIZATOR", "PAROLA"}, new String[]{"GENEHMIGT", "VORNAME", "NACHNAME", "ALTER", "MAIL", "ROLLE", "BENUTZERNAME", "PASSWORT"}});
    private List<String> adminLabesTexts = List.of(new String[]{"ADMIN", "ADMINISTRATOR", "ADMINISTRATORIN"});
    private List<String> adminMustSelectAUserMessageTexts = List.of(new String[]{"you must select a user",
            "trebuie sa selectati un user", "Sie müssen einen Benutzer auswählen"});

    public void setCurrentLanguage(int language) {
        this.currentLanguage = language;
        notifyObservers();
    }

    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
