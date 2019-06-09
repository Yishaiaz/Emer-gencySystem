package View;

import Controller.Controller;
import javafx.event.ActionEvent;

import java.util.Observable;
import java.util.Observer;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class View implements Observer{
    private Controller m_controller;
    public Map<String,String> loggedUser = null;
    public Button btn_Login;
    public Button btn_create_event;
    public Button btn_change_password;
    public Label user_rank_label;
    public Label user_name_label;

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
                user_rank_label.setText("Your Rank is " +loggedUser.get("rank"));
                btn_create_event.setVisible(true);
                btn_change_password.setVisible(true);
            } else {
                btn_Login.setText("Login");
                user_name_label.setText("Hello Guest");
                user_rank_label.setText("");
                btn_create_event.setVisible(false);
                btn_change_password.setVisible(false);
            }
        }
    }


    public void onClickLoginButton(ActionEvent actionEvent) {
        displayLoginDialog();
    }

    public void onClickCreateEvent(ActionEvent actionEvent) {
        displayCreateEventDialog();
    }


    public void onClickChangeUserPassword(ActionEvent actionEvent) {
        displayChangePasswordDialog();
    }


    public void onClickAbout(ActionEvent actionEvent) {

    }

    private void displayCreateEventDialog() {
        // Custom dialog
        Dialog dialog = new Dialog();
        dialog.setHeaderText("New Event");
        dialog.setResizable(true);

        // Widgets
        Label lbl_password = new Label("Event ");
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
                m_controller.createNewEvent(loggedUser.get("username"), newPassword);
                dialog.close();
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
                m_controller.createNewEvent(loggedUser.get("username"), newPassword);
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
        Label lbl3 = new Label("dont have an account?");
        Label lbl_loginError = new Label("");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        Button loginBtn = new Button("Login");
        Button btn_createAccount = new Button("Create Account");

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
        grid.add(lbl_loginError, 2, 3);
        grid.add(lbl3, 1, 5);
        grid.add(btn_createAccount, 2, 5);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);

        //on click handlers
        /**
         * opens the "createAccount" popup
         */
        btn_createAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
//                onClickCreateProfile();
            }
        });

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String userName = text1.getText();
                String password = text2.getText();
                m_controller.logIn(userName, password);
                dialog.close();
            }
        });

        // Show dialog
        dialog.showAndWait();
    }

    private void createNewEvent(){

        // Custom dialog
        Dialog dialog = new Dialog();
        dialog.setHeaderText("Enter user name and password ");
        dialog.setResizable(true);

        // Widgets
        Label lbl_event_name = new Label("Event Name: ");
        Label lbl_event_something = new Label("Event something ");
        TextField textField_event_name = new TextField();
        TextField textField_event_something = new TextField();
        Button createEventBtn = new Button("Create Event");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(lbl_event_name, 1, 1); // col=1, row=1
        grid.add(textField_event_name, 2, 1);
        grid.add(lbl_event_something, 1, 2); // col=1, row=2
        grid.add(textField_event_something, 2, 2);
        grid.add(createEventBtn, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType btn_cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(btn_cancel);


        createEventBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String event_name = textField_event_name.getText();
                String event_something = textField_event_something.getText();
                m_controller.createNewEvent(event_name, event_something);
                dialog.close();
            }
        });

        // Show dialog
        dialog.showAndWait();
    }



}
