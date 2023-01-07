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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javax.swing.JOptionPane;

public class AdoptionController implements Initializable {

    ObservableList<Adoption> list = FXCollections.observableArrayList();

    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Adoption> membersTableView;
    @FXML
    private TableColumn<Adoption, String> nameCol;
    @FXML
    private TableColumn<Adoption, String> idCol;
    @FXML
    private TableColumn<Adoption, String> childAgeCol;
    @FXML
    private TableColumn<Adoption, String> pOccupationCol;
    @FXML
    private TableColumn<Adoption, String> telCol;
    @FXML
    private TableColumn<Adoption, String> emailCol;
    @FXML
    private TableColumn<Adoption, String> physicalAddressCol;
    @FXML
    private TableColumn<Adoption, String> adoptedDateCol;
    @FXML
    private TableColumn<Adoption, String> childAdmNoCol;
    @FXML
    private TextField stf;

    int adoptionId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adoptionId = 0;
        initTable();
        loadData();
    }

    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        physicalAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        pOccupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        childAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        childAdmNoCol.setCellValueFactory(new PropertyValueFactory<>("childadmno"));
        adoptedDateCol.setCellValueFactory(new PropertyValueFactory<>("adoptiondate"));
    }

    private void loadData() {
        list.clear();
        String query = "SELECT * FROM adoption ORDER BY ParentID ASC";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Adoption(
                        rs.getString("Name"),
                        rs.getString("ParentID"),
                        rs.getString("Age"),
                        rs.getString("Occupation"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("AdoptionDate"),
                        rs.getString("ChildAdmNo")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addAdoptionAction(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair< String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Staff");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Parent Name");

        TextField id = new TextField("00");
        id.setEditable(false);

        TextField email = new TextField();
        email.setPromptText("Parent Email");

        TextField age = new TextField();
        age.setPromptText("Age");

        TextField occupation = new TextField();
        occupation.setPromptText("Occupation");

        TextField address = new TextField();
        address.setPromptText("Staff Address");

        TextField tel = new TextField();
        tel.setPromptText("Staff Tel No");

        TextField ad = new TextField();
        ad.setPromptText("Adoption Date");

        TextField childAdmNo = new TextField();
        childAdmNo.setPromptText("Child Adm No");

        gridPane.add(name, 0, 0);
        gridPane.add(id, 0, 1);
        gridPane.add(email, 0, 2);
        gridPane.add(age, 0, 3);
        gridPane.add(occupation, 0, 4);
        gridPane.add(address, 0, 5);
        gridPane.add(tel, 0, 6);
        gridPane.add(ad, 0, 7);
        gridPane.add(childAdmNo, 0, 8);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("We are adding");
                if (database.AddAdoption(name.getText(), id.getText(), email.getText(), age.getText(), occupation.getText(), address.getText(), tel.getText(), ad.getText(), childAdmNo.getText())) {
                    dialog.close();
                    common.dialogPopUp("INFORMATION", "An Adoption Added Successfully");
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
    private void updateAdoptionAction(ActionEvent event) {
        int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Update Adoption", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter Parent Name", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _age = JOptionPane.showInputDialog(null, "Enter Age", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _occupation = JOptionPane.showInputDialog(null, "Enter Occupation", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _tel = JOptionPane.showInputDialog(null, "Enter Tel", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _email = JOptionPane.showInputDialog(null, "Enter New Email", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _address = JOptionPane.showInputDialog(null, "Enter Physical Address", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);
        String _ad = JOptionPane.showInputDialog(null, "Enter  Adoption Date", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);

        String _admNo = JOptionPane.showInputDialog(null, "Enter Child Admission No", "Update Adoption", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _name, _age, _occupation, _tel, _email, _address, _ad, _admNo
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
            int savingsOk = JOptionPane.showConfirmDialog(null, "Parent Name:\t" + _name + "\nAge:\t" + _age + "\nOccupation:\t" + _occupation + "\nParent Tel:\t" + _tel + "\nEmail:\t" + _email + "\nAddress:\t" + _address + "\nAdoption Date:\t" + _ad + "\nChild Adm No:\t" + _admNo, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.editAdoption(_name, _age, _occupation, _tel, _email, _address, _ad, _admNo, parent_id)) {
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
    private void deleteAdoptionAction(ActionEvent event) {
        int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Delete Adoption Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Adoption?", "Delete Adoption Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM adoption WHERE ParentID='" + parent_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }

    @FXML
    private void searchAdoptionAction(KeyEvent event) {
        // Never mind
    }

    public static class Adoption {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty age;
        private final SimpleStringProperty occupation;
        private final SimpleStringProperty tel;
        private final SimpleStringProperty email;
        private final SimpleStringProperty address;
        private final SimpleStringProperty adoptiondate;
        private final SimpleStringProperty childadmno;

        public Adoption(String NAME, String ID, String AGE, String OCCUPATION, String TEL, String EMAIL, String ADDRESS, String ADOPTIONDATE, String CHILDADMNO) {

            this.name = new SimpleStringProperty(NAME);
            this.id = new SimpleStringProperty(ID);
            this.age = new SimpleStringProperty(AGE);
            this.occupation = new SimpleStringProperty(OCCUPATION);
            this.tel = new SimpleStringProperty(TEL);
            this.email = new SimpleStringProperty(EMAIL);
            this.address = new SimpleStringProperty(ADDRESS);
            this.adoptiondate = new SimpleStringProperty(ADOPTIONDATE);
            this.childadmno = new SimpleStringProperty(CHILDADMNO);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAge() {
            return age.get();
        }

        public String getOccupation() {
            return occupation.get();
        }

        public String getTel() {
            return tel.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getAdoptiondate() {
            return adoptiondate.get();
        }

        public String getChildadmno() {
            return childadmno.get();
        }

    }

}
