package model.persistence;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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

    public List<Schedule> getSchedulesForThisSection(Section section) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Schedule> schedules = (new SchedulePersistence()).readAll();
        Section sectionFromDataBase = session.get(Section.class, section.getSectionId());
        Set<Schedule> searchedSchedules = sectionFromDataBase.getSchedules();
        List<Schedule> searchedSchedulesAsList = new ArrayList<>(searchedSchedules);
        t.commit();
        factory.close();
        session.close();
        return searchedSchedulesAsList;
    }
}
