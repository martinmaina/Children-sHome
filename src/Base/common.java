package Base;

import static Base.validateUser.generateHash;
import Database.database;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class common {

    private static final String url = "jdbc:mysql://localhost:3306/gmch";
    private static final String pass = "";
    private static final String user = "root";

    public static void dialogPopUp(String msgType, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();

    }

    public static boolean login(String username, String password) {

        database db = new database();

        boolean isAuthenticated = false;

        String hashedPassword = generateHash(password);
        if (hashedPassword.equals(verifyPassword(username, password))) {

            isAuthenticated = true;

        } else {

            isAuthenticated = false;

        }

        return isAuthenticated;

    }

    public static String verifyPassword(String username, String password) {

        String storedHashedPassword = null;

        try {

            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "select * from ADMIN where username ='" + username + "' ";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                storedHashedPassword = rs.getString("password");

            }

        } catch (SQLException e) {
            dialogPopUp("Error!", e.getMessage());
        }

        return storedHashedPassword;
    }

    public static boolean loginMain(String username, String password, String title) {

        boolean isAuthenticated = false;
        String passw = "";
        String table = title.equals("admin") ? "login" : "staff";
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            if (table.equals("login")) {
                String query = "select * FROM login WHERE Username='" + username + "'";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery(query);
                while (rs.next()) {
                    passw = rs.getString("Password");
                }
            } else if (table.equals("staff")) {
                String query = "select * FROM staff WHERE StaffID='" + username + "'";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery(query);
                while (rs.next()) {
                    passw = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (passw.equals(password)) {
            isAuthenticated = true;
        }

        return isAuthenticated;
    }

    /**
     * Load an fxml file into the specified stage
     *
     * @param fxml
     */
    public void loadStage(String fxml) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Files/person.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Great Mercy Children's Home | HOME");

            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static String getTheCurrentDate() {
        return new java.util.Date().toString().split(" ")[1] + " " + new java.util.Date().toString().split(" ")[2] + "," + new java.util.Date().toString().split(" ")[5];

    }

    public static void logout(ActionEvent e) {
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION);
        _alert.setContentText("Sure to Exit");
        ButtonType result1 = _alert.showAndWait().orElse(ButtonType.OK);

        if (ButtonType.OK.equals(result1)) {
            _alert.setContentText("Closing...");
            _alert.showAndWait();

            System.exit(0);
        } else if (ButtonType.CANCEL.equals(result1)) {
            e.consume();
        }
    }
}
