package model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Setter
@Getter
public class Connection {
    private StandardServiceRegistry ssr;
    private Metadata meta;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public Connection() {
    }

    public void startConnection() {
        ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        meta = new MetadataSources(ssr).getMetadataBuilder().build();
        sessionFactory = meta.getSessionFactoryBuilder().build();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    public void stopConnection() {
        transaction.commit();
        sessionFactory.close();
        session.close();
    }


}
