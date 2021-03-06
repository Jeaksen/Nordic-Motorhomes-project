package motorhomes.com.examproject.repositories.array;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public interface ICrudRepository<T> {

    ArrayList<T> readAll() throws Exception;
    boolean create(T object) throws Exception;
    T read(int id) throws Exception;
    void update(T object) throws Exception;
    void delete(int id) throws Exception;
}
