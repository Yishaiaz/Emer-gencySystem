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

    public void logIn(String userName, String password) throws Exception{
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

    public void changeCurrentUserPassword(String password) throws Exception{
        model.changeCurrentUserPassword(loggedUser.get("username") ,password);
        System.out.println("users new password is : "+password);
    }

    //loggedUser.get("username"), title, categories, organizations, details
    public void createNewEvent(String username, String title, String[] categories, String[] organizations, String details) throws Exception{
        model.createNewEvent(username, title, categories, organizations, details);
    }

    public String[] getCategories() throws Exception{
        return model.getCategories();
    }

    public String[] getEmergencyServices() throws Exception{
        return model.getEmergencyServices();
    }
}
