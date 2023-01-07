package Controllers;

import Database.database;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HomeController implements Initializable {

    @FXML
    private Button staffbtn;
    @FXML
    private Button adoptionbtn;
    @FXML
    private Button fosteringbtn;
    @FXML
    private Button staffmenubtn;
    @FXML
    private Text mText;
    @FXML
    private StackPane motherPane;
    @FXML
    private Menu adminMenu;
    @FXML
    private Menu userMenu;
    @FXML
    private Menu closeMenu;
    @FXML
    private MenuItem volunteers;
    @FXML
    private MenuItem contacts;
    @FXML
    private MenuItem kidsCorner;
    @FXML
    private MenuItem donations;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem adoption;
    @FXML
    private MenuItem children;
    @FXML
    private MenuItem staff;
    @FXML
    private MenuItem fostering;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(10.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setColor(Color.WHITE);

        mText.setEffect(dropShadow);
        mText.setCache(true);
        mText.setX(30.0f);
        mText.setY(70.0f);
        mText.setWrappingWidth(700);
        mText.setTextAlignment(TextAlignment.CENTER);
        mText.setTextOrigin(VPos.CENTER);

        mText.setFill(Color.GREEN);
        mText.setFont(Font.font("JFRockOutcrop", FontWeight.BOLD, 62));

        if(database.checkLoggedIn().equals("user")){
            adminMenu.setDisable(true);
        }
    }

    @FXML
    private void staffAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/staff.fxml")));
    }

    @FXML
    private void adoptionAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/adoption.fxml")));
    }

    @FXML
    private void fosteringAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/fostering.fxml")));
    }

    @FXML
    private void childrenAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/children.fxml")));
    }


    @FXML
    private void volunteersAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/volunteers.fxml")));
    }

    @FXML
    private void contactsAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/contacts.fxml")));
    }

    @FXML
    private void donationsAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/donation.fxml")));
    }


    @FXML
    private void staffbtnAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/staff.fxml")));
    }

    @FXML
    private void adoptionbtnAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/adoption.fxml")));
    }

    @FXML
    private void fosteringbtnAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/fostering.fxml")));
    }

    @FXML
    private void staffmenubtnAction(ActionEvent event) throws IOException {
        motherPane.getChildren().clear();
        motherPane.getChildren().add(FXMLLoader.load(getClass().getResource("/FXML/staffMenu.fxml")));
    }

    @FXML
    private void exitAction(ActionEvent event) {
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION);
        _alert.setContentText("Sure to Exit");
        ButtonType result1 = _alert.showAndWait().orElse(ButtonType.OK);

        if (ButtonType.OK.equals(result1)) {
            _alert.setContentText("Closing...");
            _alert.showAndWait();
            System.exit(0);
        } else if (ButtonType.CANCEL.equals(result1)) {
            event.consume();
        }
    }

}
