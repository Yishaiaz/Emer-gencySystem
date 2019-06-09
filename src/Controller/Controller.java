package Controller;

import Model.Model;
import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {
    private Map<String,String> loggedUser = null;
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
        boolean succesfull_login = model.logInUser(userName, password);
        if(succesfull_login){
            loggedUser = new HashMap<>();
            loggedUser.put( "username" ,  userName);
            loggedUser.put( "password" ,  password);
            loggedUser.put( "rank" ,  "5");
        }
        setChanged();
        notifyObservers();
    }

    public Map<String, String> getLoggedUser() {
        return this.loggedUser;
    }

    public void logOut() {
        loggedUser = null;
        setChanged();
        notifyObservers();
    }

    public void changeCurrentUserPassword(String password) {
        model.changeCurrentUserPassword(loggedUser.get("username") ,password);
        System.out.println("users new password is : "+password);
    }

    public void createNewEvent(String event_name, String event_something) {
        model.createNewEvent(event_name, event_something);
    }
}
