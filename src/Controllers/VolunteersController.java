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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javax.swing.JOptionPane;

public class VolunteersController implements Initializable {
ObservableList<Volunteer> list = FXCollections.observableArrayList();
    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Volunteer> membersTableView;
    @FXML
    private TableColumn<Volunteer, String> nameCol;
    @FXML
    private TableColumn<Volunteer, String> idCol;
    @FXML
    private TableColumn<Volunteer, String> ageCol;
    @FXML
    private TableColumn<Volunteer, String> institutionCol;
    @FXML
    private TableColumn<Volunteer, String> telCol;
    @FXML
    private TableColumn<Volunteer, String> emailCol;
    @FXML
    private TableColumn<Volunteer, String> physicalAddressCol;
    @FXML
    private TableColumn<Volunteer, String> fromDateCol;
    @FXML
    private TableColumn<Volunteer, String> tillDateCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int volunteerId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         volunteerId = 0;
        initTable();
        loadData();
    }
    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        institutionCol.setCellValueFactory(new PropertyValueFactory<>("institution"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        physicalAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        fromDateCol.setCellValueFactory(new PropertyValueFactory<>("fromdate"));
        tillDateCol.setCellValueFactory(new PropertyValueFactory<>("tilldate"));
       ;
    }
    private void loadData() {
        list.clear();
        String query = "SELECT * FROM volunteers ORDER BY VolunteerID ASC";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Volunteer(
                        rs.getString("Name"),
                        rs.getString("VolunteerID"),
                        rs.getString("Age"),
                        rs.getString("Institution"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("StartDate"),
                        rs.getString("EndDate")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addVolunteerAction(ActionEvent event) {
        // Create the custom dialog.
            Dialog<Pair< String, String>> dialog = new Dialog<>();
            dialog.setTitle("Add Volunteer");

            // Set the button types.
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            Label name_ = new Label("Name");
            TextField name = new TextField();
            name.setPromptText("Volunteer Name");

             Label id_ = new Label("Id");
            TextField id = new TextField("00");
            id.setEditable(false);

             Label email_ = new Label("Email");
            TextField email = new TextField();
            email.setPromptText("Volunteer Email");

             Label age_ = new Label("Age");
            TextField age = new TextField();
            age.setPromptText("Age");

             Label institution_ = new Label("Institution");
            TextField institution = new TextField();
            institution.setPromptText("Institution");

             Label address_ = new Label("Address");
            TextField address = new TextField();
            address.setPromptText("Physical Address");

             Label tel_ = new Label("Phone No");
            TextField tel = new TextField();
            tel.setPromptText(" Tel No");

             Label fromdate_ = new Label("From Date");
            TextField fromdate = new TextField();
            fromdate.setPromptText("From Date");

             Label endDate_ = new Label("End Date");
            TextField endDate= new TextField();
            endDate.setPromptText("End Date");

            
            gridPane.add(name_, 0, 0);
            gridPane.add(name, 1, 0);
            gridPane.add(id_, 0, 1);
            gridPane.add(id, 1, 1);
            gridPane.add(email_,0, 2);
            gridPane.add(email, 1, 2);
            gridPane.add(age_, 0, 3);
            gridPane.add(age, 1, 3);
            gridPane.add(institution_, 0, 4);
            gridPane.add(institution, 1, 4);
            gridPane.add(address_, 0, 5);
            gridPane.add(address, 1, 5);
            gridPane.add(tel_, 0, 6);
            gridPane.add(tel, 1, 6);
            gridPane.add(fromdate_, 0, 7);
            gridPane.add(fromdate, 1, 7);
            gridPane.add(endDate_, 0, 8);
            gridPane.add(endDate, 1, 8);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    System.out.println("We are adding");
                    if (database.AddVolunteer(id.getText(), name.getText(),  age.getText(), institution.getText(), tel.getText(), email.getText(), address.getText(), fromdate.getText(),  endDate.getText()) ) {
                        dialog.close();
                        common.dialogPopUp("INFORMATION", "An Volunteer Added Successfully");
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
    private void updateVolunteerAction(ActionEvent event) {
         int volunteer_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Volunteer ID", "Update Volunteers", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter Volunteer Name", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _age = JOptionPane.showInputDialog(null, "Enter Age", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _institution = JOptionPane.showInputDialog(null, "Enter Occupation", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _tel = JOptionPane.showInputDialog(null, "Enter Tel", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _email = JOptionPane.showInputDialog(null, "Enter New Email", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _address = JOptionPane.showInputDialog(null, "Enter Physical Address", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);
        String _fromDate = JOptionPane.showInputDialog(null, "Enter  Fostering Date", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);

        String _endDate = JOptionPane.showInputDialog(null, "Enter End Date", "Update Volunteer", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _name, _age, _institution, _tel, _email, _address, _fromDate, _endDate
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
            int savingsOk = JOptionPane.showConfirmDialog(null, "Volunteer Name:\t" + _name + "\nAge:\t" + _age + "\nInstitution:\t" + _institution + "\nVolunteer Tel:\t" + _tel + "\nEmail:\t" + _email + "\nAddress:\t" + _address + "\nStart Date:\t" + _fromDate + "\nEnd Date:\t" + _endDate, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.updateFoster(_name, _age, _institution, _tel, _email, _address, _fromDate, _endDate, volunteer_id)) {
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
    private void deleteVolunteerAction(ActionEvent event) {
         int parent_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter volunteer ID", "Delete volunteer Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete volunteer?", "Delete volunteer Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM volunteers WHERE VolunteerID='" + parent_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }
  public static class Volunteer {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty age;
        private final SimpleStringProperty institution;
        private final SimpleStringProperty tel;
        private final SimpleStringProperty email;
        private final SimpleStringProperty address;
        private final SimpleStringProperty fromdate;
        private final SimpleStringProperty tilldate;

        public Volunteer(String NAME, String ID, String AGE, String INSTITUTION, String TEL, String EMAIL, String ADDRESS, String FROMDATE, String TILLDATE) {

            this.name = new SimpleStringProperty(NAME);
            this.id = new SimpleStringProperty(ID);
            this.age = new SimpleStringProperty(AGE);
            this.institution = new SimpleStringProperty(INSTITUTION);
            this.tel = new SimpleStringProperty(TEL);
            this.email = new SimpleStringProperty(EMAIL);
            this.address = new SimpleStringProperty(ADDRESS);
            this.fromdate = new SimpleStringProperty(FROMDATE);
            this.tilldate = new SimpleStringProperty(TILLDATE);
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

        public String getInstitution() {
            return institution.get();
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

        public String getFromdate() {
            return fromdate.get();
        }

        public String getTilldate() {
            return tilldate.get();
        }

    }

}
