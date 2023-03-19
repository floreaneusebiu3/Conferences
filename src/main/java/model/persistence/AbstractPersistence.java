package model.persistence;

import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractPersistence<T> {
    private final Class<T> persistentClass;
    private Connection connection;

    public AbstractPersistence() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        connection = new Connection();
    }

    public void insert(Object a) {
        connection.startConnection();
        connection.getSession().save(a);
        connection.stopConnection();
    }

    public void delete(Object a) {
        connection.startConnection();
        connection.getSession().delete(a);
        connection.stopConnection();
    }

    public void update(Object a) {
        connection.startConnection();
        connection.getSession().update(a);
        connection.stopConnection();
    }

    public List<T> readAll() {
        connection.startConnection();
        Query q = connection.getSession().createQuery("from " + persistentClass.getName());
        List<T> list = q.getResultList();
        connection.stopConnection();
        return list;
    }
}