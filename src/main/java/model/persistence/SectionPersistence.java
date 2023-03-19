package model.persistence;

import model.Section;

import java.util.List;

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
