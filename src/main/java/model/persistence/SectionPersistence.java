package model.persistence;

import model.Participant;
import model.PresentationFile;
import model.Section;
import model.SectionParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SectionPersistence extends AbstractPersistence<Section>{
    public Section getSectionById(String name) {
        List<Section> sectionList = this.readAll();
        for (Section section : sectionList) {
            if (section.getSectionId().equals(name)) {
                return section;
            }
        }
        return null;
    }
}
