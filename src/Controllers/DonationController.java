package Controllers;

import Base.common;
import Database.database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javax.swing.JOptionPane;

public class DonationController implements Initializable {
 ObservableList<Donation> list = FXCollections.observableArrayList();
    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Donation> membersTableView;
    @FXML
    private TableColumn<Donation, String> nameCol;
    @FXML
    private TableColumn<Donation, String> idCol;
    @FXML
    private TableColumn<Donation, String> donationCol;
    @FXML
    private TableColumn<Donation, String> quantityCol;
    @FXML
    private TableColumn<Donation, String> donationDateCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int donationId ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
donationId = 0;
initTable();
loadData();
    }
    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        donationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        donationDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadData() {
        list.clear();
        String query = "SELECT * FROM donations";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Donation(
                        rs.getString("DonationID"),
                        rs.getString("Origin"),
                        rs.getString("Type"),
                        rs.getString("Quantity"),
                        rs.getString("Date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void adddonationAction(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair< String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Contact");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField origin = new TextField();
        origin.setPromptText("Donor Name");

        TextField id = new TextField();
        id.setPromptText("Id Generated Auto");
        id.setEditable(false);

        TextField type = new TextField();
        type.setPromptText("Type of Donation");

        TextField quantity = new TextField();
        quantity.setPromptText("Quantity");

        TextField date = new TextField();
        date.setPromptText("Date");

        gridPane.add(id, 0, 0);
        gridPane.add(origin, 0, 1);
        gridPane.add(type, 0, 2);
        gridPane.add(quantity, 0, 3);
        gridPane.add(date, 0, 4);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("We are adding");
                if (database.AddDonation(id.getText(), origin.getText(), type.getText(), quantity.getText(), date.getText())) {
                    dialog.close();
                    common.dialogPopUp("INFORMATION", "Donation  Added Successfully");
                }
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            System.out.println("Adding");
        });
    }

    @FXML
    private void updatedonationAction(ActionEvent event) {
        int contact_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Update Adoption", JOptionPane.OK_CANCEL_OPTION));

        String _origin = JOptionPane.showInputDialog(null, "Enter Donorl", "Update Donation", JOptionPane.OK_CANCEL_OPTION);
        String _type = JOptionPane.showInputDialog(null, "Enter Type", "Update Donation", JOptionPane.OK_CANCEL_OPTION);
        String _quantity = JOptionPane.showInputDialog(null, "Enter  Donation Date", "Update Donation", JOptionPane.OK_CANCEL_OPTION);

        String _date = JOptionPane.showInputDialog(null, "Enter Date", "Update Donation", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _origin, _type, _quantity, _date
        };

        boolean error = false;

        for (String data1 : data) {
            if (data1.isEmpty()) {
                error = true;
            }
        }
        if (error) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Data Cannot be Saved\nSome fields are Empty");
            alert.setHeaderText("Error!");
            alert.showAndWait();
        }

        if (!error) {
            int savingsOk = JOptionPane.showConfirmDialog(null, "Origin:\t" + _origin + "\nType:\t" + _type + "\nQuantity:\t" + _quantity + "\nDate:\t" + _date, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.editDonation(_origin, _type, _quantity, _date, contact_id)) {
                        common.dialogPopUp("Success", " Updated Successfully");
                        loadData();
                    } else {
                        common.dialogPopUp("Failed", "Some error occurred");
                    }
                    break;
                case 1:
                    common.dialogPopUp("Abort Process", "Aborting Saving");
                    break;
                case 2:
                    System.out.println("...");
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    private void deletedonationAction(ActionEvent event) {
        int donation_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Donation ID", "Delete Donation Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Donation?", "Delete Donation Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM donation WHERE DonationID='" + donation_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }

    public static class Donation {

        private final SimpleStringProperty id;
        private final SimpleStringProperty origin;
        private final SimpleStringProperty type;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty date;

        public Donation(String ID, String ORIGIN, String _TYPE, String QUANTITY, String _DATE) {

            this.id = new SimpleStringProperty(ID);
            this.origin = new SimpleStringProperty(ORIGIN);
            this.type = new SimpleStringProperty(_TYPE);
            this.quantity = new SimpleStringProperty(QUANTITY);
            this.date = new SimpleStringProperty(_DATE);
        }

        public String getId() {
            return id.get();
        }

        public String getOrigin() {
            return origin.get();
        }

        public String getType() {
            return type.get();
        }

        public String getQuantity() {
            return quantity.get();
        }

        public String getDate() {
            return date.get();
        }

    }

}
