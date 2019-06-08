package Model;

import DataBaseConnection.IdbConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Observable;

public class Model extends Observable implements IModel {
    private IdbConnection db;

    public Model(IdbConnection db) {
        this.db = db;
    }

    public void createTables() {
    }
}
