package Main;

import static Base.common.loginMain;
import static Base.common.logout;
import static Database.database.databaseCreator;
import static Database.database.loggedIn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Text scenetitle, logo;
    private Label userName, alert;
    private TextField userTextField;
    private Label pw;
    private PasswordField pwBox;
    private Button btn, exit, user;
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) {
        
        try {
            databaseCreator();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        primaryStage.setTitle("GREAT MERCY CHILDREN'S HOME");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(10.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setColor(Color.WHITE);

        logo = new Text();
        logo.setEffect(dropShadow);
        logo.setCache(true);
        logo.setX(30.0f);
        logo.setY(70.0f);
        logo.setText("GMCH");
        logo.setWrappingWidth(330);
        logo.setFill(Color.GREENYELLOW);
        logo.setFont(Font.font("JFRockOutcrop", FontWeight.BOLD, 62));
        logo.setTextAlignment(TextAlignment.CENTER);
        logo.setTextOrigin(VPos.CENTER);

        grid.add(logo, 0, 0, 4, 5);

        scenetitle = new Text("Welcome\nPlease  Login👤");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 5, 3, 2);

        userName = new Label("User Name:");
        grid.add(userName, 0, 7);

        userTextField = new TextField();
        userTextField.setOnKeyPressed(e -> handle(e));
        grid.add(userTextField, 1, 7);

        pw = new Label("Password:");
        grid.add(pw, 0, 8);

        pwBox = new PasswordField();
        pwBox.setOnKeyPressed(e -> handle(e));
        grid.add(pwBox, 1, 8);

        userTextField.setPromptText("Username");
        pwBox.setPromptText("Passsword");
        user = new Button("User");
        btn = new Button("Admin");
        exit = new Button("Cancel");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER_RIGHT);
        hbBtn.getChildren().add(btn);
        hbBtn.getChildren().add(user);
        hbBtn.getChildren().add(exit);
        grid.add(hbBtn, 1, 9);

        alert = new Label("Incorrect Username or Password.");
        alert.setId("notice");

        btn.setOnAction((ActionEvent e) -> {
            tryLoginAdmin();
        });
        user.setOnAction((ActionEvent e) -> {
            tryLoginUser();
        });

        exit.setOnAction((ActionEvent e) -> {
           logout(e);
        });

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension size = tk.getScreenSize();

        Scene scene = new Scene(grid, size.width, size.height);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("Resources/css/login.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(150, 12, 15, 120));
        hbox.setSpacing(15);
        hbox.setStyle("-fx-background-color: #336699;");

        return hbox;
    }

    /**
     *
     */
    public void tryLoginAdmin() {
        if (!userTextField.getText().isEmpty() && !pwBox.getText().isEmpty()) {
            if (loginMain(userTextField.getText(), pwBox.getText(), "admin")) {
                loggedIn("admin");

                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);

                Stage stage = (Stage) grid.getScene().getWindow();
                loadStage("/Home/home.fxml");
                stage.close();

            } else {
                userTextField.setText("");
                grid.add(this.alert, 0, 11, 3, 1);
                pwBox.setText("");
                userTextField.setOnKeyPressed((event) -> {
                    grid.getChildren().remove(alert);
                });
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Login Error");
            alert2.setContentText("Provide All the Fields");
            alert2.showAndWait();
        }
    }

    public void tryLoginUser() {
        if (!userTextField.getText().isEmpty() && !pwBox.getText().isEmpty()) {
            if (loginMain(userTextField.getText(), pwBox.getText(), "user")) {
                loggedIn("user");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);

                Stage stage = (Stage) grid.getScene().getWindow();
                loadStage("/Home/home.fxml");
                stage.close();

            } else {
                userTextField.setText("");
                grid.add(this.alert, 0, 11, 3, 1);
                pwBox.setText("");
                userTextField.setOnKeyPressed((event) -> {
                    grid.getChildren().remove(alert);
                });
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Provide All the Fields");
            alert.showAndWait();
        }
    }

    private void handle(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.ENTER) {
            if (userTextField.getText() == null) {
                userTextField.setPromptText("Enter Username");
            } else if (pwBox.getText() == null) {
                pwBox.setPromptText("Enter Password");
            } else {
                tryLoginUser();
            }

        }
    }

    private void loadStage(String fxml) {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension size = tk.getScreenSize();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, size.width, size.height));
            stage.getIcons().add(new Image("/Resources/icons/home.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Great Mercy Children's Home | HOME");

            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
