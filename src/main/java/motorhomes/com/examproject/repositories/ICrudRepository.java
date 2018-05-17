package motorhomes.com.examproject.repositories;

import java.util.ArrayList;

public interface ICrudRepository<T> {

    ArrayList<T> readAll();
    boolean create(T object);
    T read(int id);
    void update(T object);
    void delete(int id);
}
