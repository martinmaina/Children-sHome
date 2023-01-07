package Controllers;

import Base.common;
import static Base.common.logout;
import Database.database;
import Main.login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class StaffMenuController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button volunteersBtn;
    @FXML
    private Button childrenBtn;
    @FXML
    private Button adminPageBtn;
    @FXML
    private Button contactsBtn;
    @FXML
    private Button donationBtn;
    @FXML
    private Button logoutBtn;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Disable Admin Page for common user
    if(database.checkLoggedIn().equals("user")){
            adminPageBtn.setDisable(true);
        }
     
    }

    @FXML
    private void volunteersAction(ActionEvent event) throws IOException {
        Pane motherPane = (Pane) anchorPane.getParent();
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/volunteers.fxml")));
    }

    @FXML
    private void childrenAction(ActionEvent event) throws IOException {
        Pane motherPane = (Pane) anchorPane.getParent();
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/children.fxml")));
    }

    @FXML
    private void adminPageAction(ActionEvent event) throws Exception {
        if(database.checkLoggedIn().equals("user")){
            common.dialogPopUp("ERROR", "You cannot access this Page");
        }else{
            common.dialogPopUp("ERROR", "You are the Admin");
        }
    }

    @FXML
    private void contactsAction(ActionEvent event) throws IOException {
        Pane motherPane = (Pane) anchorPane.getParent();
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/contacts.fxml")));
    }

    @FXML
    private void donationAction(ActionEvent event) throws IOException {
        Pane motherPane = (Pane) anchorPane.getParent();
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/donation.fxml")));
    }

    @FXML
    private void logoutAction(ActionEvent event) {
        logout(event);
    }

}
    