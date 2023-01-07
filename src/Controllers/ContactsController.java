package Controllers;

import Base.common;
import Database.database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ContactsController implements Initializable {

    ObservableList<Contact> list = FXCollections.observableArrayList();

    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Contact> membersTableView;
    @FXML
    private TableColumn<Contact, String> nameCol;
    @FXML
    private TableColumn<Contact, String> idCol;
    @FXML
    private TableColumn<Contact, String> telCol;
    @FXML
    private TableColumn<Contact, String> emailCol;
    @FXML
    private TableColumn<Contact, String> physicalAddressCol;
    @FXML
    private TableColumn<Contact, String> childAdmNoCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int contactId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contactId = 0;
        initTable();
        loadData();
    }

    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("contactid"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        physicalAddressCol.setCellValueFactory(new PropertyValueFactory<>("physicaladdress"));
        childAdmNoCol.setCellValueFactory(new PropertyValueFactory<>("childadmno"));

    }

    private void loadData() {
        list.clear();
        String query = "SELECT * FROM contacts ORDER BY ContactID ASC";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Contact(
                        rs.getString("Name"),
                        rs.getString("ContactID"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("ChildAdmNo")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addContactAction(ActionEvent event) {
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

        TextField name = new TextField();
        name.setPromptText("Contact Name");

        TextField id = new TextField();
        id.setPromptText("Id Generated Auto");
        id.setEditable(false);

        TextField email = new TextField();
        email.setPromptText("Contact Email");

        TextField address = new TextField();
        address.setPromptText("Contact  Address");

        TextField tel = new TextField();
        tel.setPromptText("Contact Tel No");

        TextField childAdmNo = new TextField();
        childAdmNo.setPromptText("Child Adm No");

        gridPane.add(id, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(tel, 0, 2);
        gridPane.add(email, 0, 3);
        gridPane.add(address, 0, 4);
        gridPane.add(childAdmNo, 0, 5);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("We are adding");
                if (database.AddContact(id.getText(), name.getText(), tel.getText(), email.getText(), address.getText(), childAdmNo.getText())) {
                    dialog.close();
                    loadData();
                    common.dialogPopUp("INFORMATION", "Contact  Added Successfully");
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
    private void updateContactAction(ActionEvent event) {
        int contact_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Update Contact", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter Contact Name", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _tel = JOptionPane.showInputDialog(null, "Enter Tel", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _email = JOptionPane.showInputDialog(null, "Enter New Email", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _address = JOptionPane.showInputDialog(null, "Enter Physical Address", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _admNo = JOptionPane.showInputDialog(null, "Enter Child Admission No", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _name, _tel, _email, _address, _admNo
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
            int savingsOk = JOptionPane.showConfirmDialog(null, "Contact Name:\t" + _name + "\nContact Tel:\t" + _tel + "\nEmail:\t" + _email + "\nAddress:\t" + _address + "\nChild Adm No:\t" + _admNo, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.editContact(_name, _tel, _email, _address, _admNo, contact_id)) {
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
    private void deleteContactAction(ActionEvent event) {
        int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Contact ID", "Delete Contact Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Contact?", "Delete Contact Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM contacts WHERE ContactID='" + parent_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }

    public static class Contact {

        private final SimpleStringProperty contactid;
        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        private final SimpleStringProperty physicaladdress;
        private final SimpleStringProperty childadmno;

        public Contact(String NAME, String CONTACTID, String PHONE, String EMAIL, String physicaladdress, String CHILDADMNO) {
            this.contactid = new SimpleStringProperty(CONTACTID);
            this.name = new SimpleStringProperty(NAME);
            this.phone = new SimpleStringProperty(PHONE);
            this.email = new SimpleStringProperty(EMAIL);
            this.physicaladdress = new SimpleStringProperty(physicaladdress);
            this.childadmno = new SimpleStringProperty(CHILDADMNO);
        }

        public String getContactid() {
            return contactid.get();
        }

        public String getName() {
            return name.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getPhysicaladdress() {
            return physicaladdress.get();
        }

        public String getChildadmno() {
            return childadmno.get();
        }
    }
}
