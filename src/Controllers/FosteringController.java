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

public class FosteringController implements Initializable {

    ObservableList<Foster> list = FXCollections.observableArrayList();

    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Foster> membersTableView;
    @FXML
    private TableColumn<Foster, String> nameCol;
    @FXML
    private TableColumn<Foster, String> idCol;
    @FXML
    private TableColumn<Foster, String> childAgeCol;
    @FXML
    private TableColumn<Foster, String> pOccupationCol;
    @FXML
    private TableColumn<Foster, String> telCol;
    @FXML
    private TableColumn<Foster, String> emailCol;
    @FXML
    private TableColumn<Foster, String> physicalAddressCol;;
    @FXML
    private TableColumn<Foster, String> fosteringDateCol;
    @FXML
    private TableColumn<Foster, String> childAdmNoCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int fosterId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fosterId = 0;
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
        fosteringDateCol.setCellValueFactory(new PropertyValueFactory<>("fosteringdate"));
        childAdmNoCol.setCellValueFactory(new PropertyValueFactory<>("childadmno"));

    }

    private void loadData() {
        list.clear();
        String query = "SELECT * FROM fostering";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Foster(
                        rs.getString("Name"),
                        rs.getString("ParentID"),
                        rs.getString("Age"),
                        rs.getString("Occupation"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("FosteringDate"),
                        rs.getString("ChildAdmNo")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addFosteringAction(ActionEvent event) {

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
        address.setPromptText("Physical Address");

        TextField tel = new TextField();
        tel.setPromptText(" Tel No");

        TextField ad = new TextField();
        ad.setPromptText("Fostering Date");

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
                if (database.AddFoster(name.getText(), id.getText(), age.getText(), occupation.getText(), tel.getText(), email.getText(), address.getText(), ad.getText(), 0)) {
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
    private void updateFosteringAction(ActionEvent event) {
        int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Update Fostering", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter Parent Name", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _age = JOptionPane.showInputDialog(null, "Enter Age", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _occupation = JOptionPane.showInputDialog(null, "Enter Occupation", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _tel = JOptionPane.showInputDialog(null, "Enter Tel", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _email = JOptionPane.showInputDialog(null, "Enter New Email", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _address = JOptionPane.showInputDialog(null, "Enter Physical Address", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);
        String _ad = JOptionPane.showInputDialog(null, "Enter  Fostering Date", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);

        String _admNo = JOptionPane.showInputDialog(null, "Enter Child Admission No", "Update Fostering", JOptionPane.OK_CANCEL_OPTION);

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
            int savingsOk = JOptionPane.showConfirmDialog(null, "Parent Name:\t" + _name + "\nAge:\t" + _age + "\nOccupation:\t" + _occupation + "\nParent Tel:\t" + _tel + "\nEmail:\t" + _email + "\nAddress:\t" + _address + "\nFostering Date:\t" + _ad + "\nChild Adm No:\t" + _admNo, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.updateFoster(_name, _age, _occupation, _tel, _email, _address, _ad, _admNo, parent_id)) {
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
    private void deleteFosteringAction(ActionEvent event) {
        int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Parent ID", "Delete Fostering Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Foster?", "Delete Fostering Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM fostering WHERE ParentID='" + parent_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }

    public static class Foster {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty age;
        private final SimpleStringProperty occupation;
        private final SimpleStringProperty tel;
        private final SimpleStringProperty email;
        private final SimpleStringProperty address;
        private final SimpleStringProperty fosteringdate;
        private final SimpleStringProperty childadmno;

        public Foster(String NAME, String ID, String AGE, String OCCUPATION, String TEL, String EMAIL, String ADDRESS, String fosteringdate, String CHILDADMNO) {

            this.name = new SimpleStringProperty(NAME);
            this.id = new SimpleStringProperty(ID);
            this.age = new SimpleStringProperty(AGE);
            this.occupation = new SimpleStringProperty(OCCUPATION);
            this.tel = new SimpleStringProperty(TEL);
            this.email = new SimpleStringProperty(EMAIL);
            this.address = new SimpleStringProperty(ADDRESS);
            this.fosteringdate = new SimpleStringProperty(fosteringdate);
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

        public String getFosteringdate() {
            return fosteringdate.get();
        }

        public String getChildadmno() {
            return childadmno.get();
        }

    }

}
