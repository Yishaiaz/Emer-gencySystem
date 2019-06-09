package View;

import Controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;

public class View implements Observer{
    private Controller m_controller;
    public Map<String,String> loggedUser = null;
    public Button btn_Login;
    public Button btn_create_event;
    public Button btn_change_password;
    public Button btn_create_category;
    public Label user_rank_label;
    public Label user_name_label;
    public Label user_organization_label;

    public void setController(Controller controller) {
        m_controller = controller;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == m_controller) {
            loggedUser = m_controller.getLoggedUser();
            if (loggedUser != null) {
                btn_Login.setText("Logout");
                user_name_label.setText("Hello " +loggedUser.get("username"));
                if(!loggedUser.get("rank").equals("")){
                    user_rank_label.setText("Your Rank is " +loggedUser.get("rank"));
                }
                if(loggedUser.get("organization").equals("epicenter")){
                    if(loggedUser.get("isAdmin").equals("true")){
                        btn_create_event.setVisible(true);
                        btn_create_category.setVisible(true);
                    }
                }
                user_organization_label.setText("Organization: "+loggedUser.get("organization"));
                user_organization_label.setVisible(true);
                btn_change_password.setVisible(true);
            } else {
                btn_Login.setText("Login");
                user_name_label.setText("Hello Guest");
                user_rank_label.setText("");
                btn_create_event.setVisible(false);
                btn_create_category.setVisible(false);
                btn_change_password.setVisible(false);
                user_organization_label.setText("");
                user_organization_label.setVisible(false);
            }
        }
    }


    public void onClickLoginButton(ActionEvent actionEvent) {
        if(loggedUser!=null){
            m_controller.logOut();
        }
        else{
            displayLoginDialog();
        }
    }

    public void onClickCreateEvent(ActionEvent actionEvent) {
        displayCreateEventDialog();
    }


    public void onClickChangeUserPassword(ActionEvent actionEvent) {
        displayChangePasswordDialog();
    }


    public void onClickAbout(ActionEvent actionEvent) {

    }
    public void onClickCreateCategory(ActionEvent actionEvent) {
        displayCreateNewCategoryDialog();
    }

    private void displayCreateNewCategoryDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderText("Create a category");
        dialog.setResizable(true);

        // Widgets
        Label lbl_password = new Label("New Category name: ");
        TextField new_category = new TextField();
        Button createEvent = new Button("Create Category");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(lbl_password, 1, 1); // col=1, row=1
        grid.add(new_category, 2, 1);
        grid.add(createEvent, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);

        //on click handlers
        createEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String newCategory = new_category.getText();
                try{
                    m_controller.createNewCategory(newCategory);
                }catch(Exception createNewCategoryException) {
                    showAlert("sorry", createNewCategoryException.getMessage());
                }
                dialog.close();
            }
        });

        // Show dialog
        dialog.showAndWait();
    }


    private void displayCreateEventDialog() {
//        String userId, String[] categories, String[] emergencyForces,String title,String status,String details
        // Custom dialog
        String status = "In Progress";

        String[] categories ={};
        LinkedList<String> selectedCategories = new LinkedList<>();

        String[] emergencyForces = {};
        LinkedList<String> selectedOrganizations = new LinkedList<>();

        try{
            categories = m_controller.getCategories();
            emergencyForces = m_controller.getEmergencyServices();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Dialog dialog = new Dialog();
        dialog.setHeaderText("New Event");
        dialog.setResizable(true);

        // Widgets

        Label lbl_title = new Label("Title ");
        TextField title_text_input = new TextField();


        Label lbl_categories = new Label("Categories ");
        //this is the label that will present the chosen categories so far
        Label lbl_categories_chosen = new Label("");
        ComboBox categories_drop_down = new ComboBox(FXCollections.observableArrayList(categories));
        // a button to add the current chosen organization
        Button add_category = new Button("Add");


        Label lbl_organizations = new Label("Emergency Services");
        //this is the label that will present the chosen organizations so far
        Label lbl_organizations_chosen = new Label("");
        ComboBox emergency_forces_drop_down = new ComboBox(FXCollections.observableArrayList(emergencyForces));
        // a button to add the current chosen organization
        Button add_emergency = new Button("Add");


        Label lbl_details = new Label("Details ");
        TextField details_text_input = new TextField();


        Button createEvent = new Button("Create Event");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        //title input
        grid.add(lbl_title, 1,1);
        grid.add(title_text_input, 2,1);
        //categories input
        grid.add(lbl_categories, 1,2);
        grid.add(categories_drop_down, 2,2);
        grid.add(add_category, 3,2);
        grid.add(lbl_categories_chosen, 1,3);
        //organizations input
        grid.add(lbl_organizations, 1,4);
        grid.add(emergency_forces_drop_down, 2,4);
        grid.add(add_emergency, 3,4);
        grid.add(lbl_organizations_chosen, 1,5);
        //details input
        grid.add(lbl_details, 1,6);
        grid.add(details_text_input, 2,6);
        grid.add(createEvent,2,7);


        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);

        //BUTTONS ON CLICK HANDLERS
        createEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String title = title_text_input.getText();
                String[] categories = convertListToStringArray(selectedCategories);
                String[] organizations = convertListToStringArray(selectedOrganizations);
                String details = details_text_input.getText();
                if(title.equals("")
                        || lbl_categories_chosen.getText().equals("")
                        || lbl_organizations_chosen.getText().equals("") || details.equals("")){
                    showAlert("Required Fields", "ALL fields are required");
                    return;
                }
                try{
                    m_controller.createNewEvent(loggedUser.get("username"), title, categories, organizations, details);
                    dialog.close();
                }catch (Exception creatingEventException){
                    System.out.println("problem creating the event "+creatingEventException.getMessage());
                }
            }
        });
        add_category.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                String currentCat = categories_drop_down.getSelectionModel().getSelectedItem().toString();
                if(!lbl_categories_chosen.getText().contains(currentCat)){
                    lbl_categories_chosen.setText(lbl_categories_chosen.getText() + currentCat+ ",");
                    selectedCategories.add(currentCat);
                }

            }
        });
        add_emergency.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                String currentOrg = emergency_forces_drop_down.getSelectionModel().getSelectedItem().toString();
                if(!lbl_organizations_chosen.getText().contains(currentOrg)){
                    lbl_organizations_chosen.setText(lbl_organizations_chosen.getText() + currentOrg+ ",");
                    selectedOrganizations.add(currentOrg);
                }
            }
        });


        // Show dialog
        dialog.showAndWait();
    }

    private void displayChangePasswordDialog() {

        // Custom dialog
        Dialog dialog = new Dialog();
        dialog.setHeaderText("Enter new password ");
        dialog.setResizable(true);

        // Widgets
        Label lbl_password = new Label("New password: ");
        TextField text1 = new TextField();
        Button createEvent = new Button("Change password");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(lbl_password, 1, 1); // col=1, row=1
        grid.add(text1, 2, 1);
        grid.add(createEvent, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);

        //on click handlers
        createEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String newPassword = text1.getText();
                try{
                    m_controller.changeCurrentUserPassword(newPassword);
                }catch(Exception changeCurrentPasswordException) {
                    showAlert("sorry", changeCurrentPasswordException.getMessage());
                }
                dialog.close();
            }
        });

        // Show dialog
        dialog.showAndWait();
    }

    private void displayLoginDialog() {

        // Custom dialog
        Dialog dialog = new Dialog();
        dialog.setHeaderText("Enter user name and password ");
        dialog.setResizable(true);

        // Widgets
        Label lbl_userName = new Label("User name: ");
        Label lbl_password = new Label("Password: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        Button loginBtn = new Button("Login");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(lbl_userName, 1, 1); // col=1, row=1
        grid.add(text1, 2, 1);
        grid.add(lbl_password, 1, 2); // col=1, row=2
        grid.add(text2, 2, 2);
        grid.add(loginBtn, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);

        //on click handlers
        /**
         * opens the "createAccount" popup
         */

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String userName = text1.getText();
                String password = text2.getText();

                try{
                    m_controller.logIn(userName, password);
                }catch(Exception changeCurrentPasswordExcemption) {
                    showAlert("sorry", changeCurrentPasswordExcemption.getMessage());
                }
                dialog.close();
            }
        });

        // Show dialog
        dialog.showAndWait();
    }

    private String[] convertListToStringArray(LinkedList<String> toConvert){
        String[] ans = new String[toConvert.size()];
        int ctr = 0;
        for(String single: toConvert){
            ans[ctr] = single;
            ctr++;
        }
        return ans;
    }

    private void showAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
