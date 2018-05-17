package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Accessory;

import java.util.ArrayList;

public class AccessoryDbRepository implements ICrudRepository<Accessory>{

    @Override
    public ArrayList<Accessory> readAll() {
        return null;
    }

    @Override
    public boolean create(Accessory object) {
        return false;
    }

    @Override
    public Accessory read(int id) {
        return null;
    }

    @Override
    public void update(Accessory object) {

    }

    @Override
    public void delete(int id) {

    }
}
