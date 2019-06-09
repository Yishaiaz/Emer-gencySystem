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

    public void changeCurrentUserPassword(String username, String password) {
        // todo: change to actualy retrieve user password and check
    }

    public boolean logInUser(String userName, String password) {
        return true;// todo: change to actual authentication
    }

    public void createNewEvent(String event_name, String event_something) {

    }
}
