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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javax.swing.JOptionPane;

public class StaffController implements Initializable {

    ObservableList<Staff> list = FXCollections.observableArrayList();

    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Staff> membersTableView;
    @FXML
    private TableColumn<Staff, String> nameCol;
    @FXML
    private TableColumn<Staff, String> idCol;
    @FXML
    private TableColumn<Staff, String> telCol;
    @FXML
    private TableColumn<Staff, String> emailCol;
    @FXML
    private TableColumn<Staff, String> physicalAddressCol;
    @FXML
    private TableColumn<Staff, String> departmentCol;
    @FXML
    private TableColumn<Staff, String> passwordCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int staffId;
    @FXML
    private TextField stf;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // If the logged in person is a common user, disable cud and password col
        if (database.checkLoggedIn().equals("user")) {
            add.setDisable(true);
            update.setDisable(true);
            delete.setDisable(true);
//            passwordCol.getParentColumn().setVisible(false);
        }
        staffId = 0;
        // Initialize Table
        initTable();

        // Load data
        loadData();


    }

    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        physicalAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

//    Load data from database to table
    private void loadData() {
        list.clear();
        String query = "SELECT * FROM staff ORDER BY StaffID ASC";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Staff(
                        rs.getString("Name"),
                        rs.getString("StaffID"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("Department"),
                        rs.getString("Password")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addStaffAction(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair< String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Staff");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Staff Name");
        TextField id = new TextField("00");
        id.setEditable(false);
        TextField email = new TextField();
        email.setPromptText("Staff E,mail");
        TextField address = new TextField();
        address.setPromptText("Staff Address");
        TextField tel = new TextField();
        tel.setPromptText("Staff Tel No");
        TextField dep = new TextField();
        dep.setPromptText("Staff Department");
        TextField password = new TextField();
        password.setPromptText("Staff Password");

        gridPane.add(name, 0, 0);
        gridPane.add(id, 0, 1);
        gridPane.add(email, 0, 2);
        gridPane.add(address, 0, 3);
        gridPane.add(tel, 0, 4);
        gridPane.add(dep, 0, 5);
        gridPane.add(password, 0, 6);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
//    Platform.runLater(() -> from.requestFocus());
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("We are adding");
                if (database.AddStaff(name.getText(), id.getText(), tel.getText(), email.getText(), address.getText(), dep.getText(), password.getText())) {
                    dialog.close();
                    common.dialogPopUp("INFORMATION", "Staff Added Successfully");
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
    private void updateStaffAction(ActionEvent event) {
        int staff_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Staff ID", "Update Staff", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter new Staff Name", "Update Staff", JOptionPane.OK_CANCEL_OPTION);
        String _tel = JOptionPane.showInputDialog(null, "Enter new Tel", "Update Staff", JOptionPane.OK_CANCEL_OPTION);
        String _address = JOptionPane.showInputDialog(null, "Enter new Physical Address", "Update Staff", JOptionPane.OK_CANCEL_OPTION);
        String _dep = JOptionPane.showInputDialog(null, "Enter New Department", "Update Staff", JOptionPane.OK_CANCEL_OPTION);
        String _email = JOptionPane.showInputDialog(null, "Enter New Email", "Update Staff", JOptionPane.OK_CANCEL_OPTION);
        String _pass = JOptionPane.showInputDialog(null, "Enter New Password", "Update Staff", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _name, _tel, _address, _dep, _pass
        };
        
        boolean error = false;
        
        for(String data1 : data){
            if(data1.isEmpty()){
                error = true;
            }
        }
        if(error){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Data Cannot be Saved\nSome fields are Empty");
            alert.setHeaderText("Error!");
            alert.showAndWait();
        }
      
        if(!error){
            int savingsOk = JOptionPane.showConfirmDialog(null, "Staff Name:\t"+_name+"\nStaff Tel:\t"+_tel+"\nEmail:\t"+_email+"\nAddress:\t"+_address+"\nDepartment:\t"+_dep+"\nPassword:\t"+_pass, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);
            
            switch(savingsOk){
                case 0:
                    if(database.editStaff(_name,_email, _tel, _address,_dep,_pass,staff_id)){
                        common.dialogPopUp("Success", "Staff Updated Successfully");
                        loadData();
                    }else{
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
    private void deleteStaffAction(ActionEvent event) {
          int staff_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Staff ID", "Delete Staff", JOptionPane.OK_CANCEL_OPTION));
          int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Staff?", "Delete Staff Member", JOptionPane.YES_NO_CANCEL_OPTION);
          
          switch(confirmDelete){
              case 0:
               if(   database.execAction("DELETE FROM staff WHERE StaffID='"+staff_id+"'")){
                   common.dialogPopUp("ERROR", "Deleted!");
                   loadData();
               }else{
                   common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
               }
          }
    }

    @FXML
    private void searchStaffAction(KeyEvent event) {
         list.clear();
        String query = "SELECT * FROM staff ORDER BY StaffID ASC WHERE Name LIKE%'"+stf.getText()+"'%";

        ResultSet rs = database.execQuery(query);

        try {
            while (rs.next()) {
                list.add(new Staff(
                        rs.getString("Name"),
                        rs.getString("StaffID"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("Department"),
                        rs.getString("Password")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void searchStaffAction(ActionEvent event) {
        System.out.println("Enter pressed");
    }

    private void searchStaffAction(MouseEvent event) {
        System.out.println("Mouse search");
    }

    public static class Staff {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty tel;
        private final SimpleStringProperty email;
        private final SimpleStringProperty address;
        private final SimpleStringProperty department;
        private final SimpleStringProperty password;

        public Staff(String NAME, String ID, String TEL, String EMAIL, String ADDRESS, String DEPARTMENT, String PASSWORD) {

            this.name = new SimpleStringProperty(NAME);
            this.id = new SimpleStringProperty(ID);
            this.tel = new SimpleStringProperty(TEL);;
            this.email = new SimpleStringProperty(EMAIL);
            this.address = new SimpleStringProperty(ADDRESS);
            this.department = new SimpleStringProperty(DEPARTMENT);
            this.password = new SimpleStringProperty(PASSWORD);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
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

        public String getDepartment() {
            return department.get();
        }

        public String getPassword() {
            return password.get();
        }

    }

}
