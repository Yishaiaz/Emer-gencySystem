package Controller;

import Model.Model;
import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {
    private Map<String,String> loggedUser = new HashMap<>();
    private Model model = null;
    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public void logIn(String userName, String password) {
        loggedUser.put( "username" ,  userName);
        loggedUser.put( "password" ,  password);
        setChanged();
        notifyObservers();
    }

    public Map<String, String> getLoggedUser() {
        return this.loggedUser;
    }
}
