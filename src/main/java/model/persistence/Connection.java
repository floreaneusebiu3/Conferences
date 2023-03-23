package model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Setter
@Getter
public class Connection {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
                sessionFactory = new MetadataSources(serviceRegistry)
                        .buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        Connection.sessionFactory = sessionFactory;
    }

}
