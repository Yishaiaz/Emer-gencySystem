package Model;


import java.util.Collection;
import java.util.Map;

public interface IModel {
    void changeCurrentUserPassword(String username, String password) throws Exception;
    Map<String,String> logInUser(String userName, String password) throws Exception;
    void createNewEvent(String username, String title, String[] categories, String[] organizations, String details) throws Exception;
    String[] getCategories() throws Exception;
    String[] getEmergencyServices() throws Exception;
    void createNewCategory(String newCategory) throws Exception;

}
