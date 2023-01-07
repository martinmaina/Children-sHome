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

public class ChildrenController implements Initializable {

    ObservableList<Child> list = FXCollections.observableArrayList();

    @FXML
    private Pane motherPane;
    @FXML
    private TableView<Child> membersTableView;
    @FXML
    private TableColumn<Child, String> nameCol;
    @FXML
    private TableColumn<Child, String> admDateCol;
    @FXML
    private TableColumn<Child, String> childAgeCol;
    @FXML
    private TableColumn<Child, String> childAdmNoCol;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    int childId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        childId = 0;
        initTable();
        loadData();
    }

    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        admDateCol.setCellValueFactory(new PropertyValueFactory<>("admissiondate"));
        childAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        childAdmNoCol.setCellValueFactory(new PropertyValueFactory<>("admno"));
    }

    private void loadData() {
        list.clear();
        String query = "SELECT * FROM children ORDER BY AdmNo ASC";
        ResultSet rs = database.execQuery(query);
        try {
            while (rs.next()) {
                list.add(new Child(
                        rs.getString("Name"),
                        rs.getString("AdmissionDate"),
                        rs.getString("Age"),
                        rs.getString("AdmNo")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        membersTableView.getItems().setAll(list);
    }

    @FXML
    private void addchildAction(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair< String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Child");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Child Name");

        TextField id = new TextField();
        id.setPromptText("Adm No Generated Auto");
        id.setEditable(false);

        TextField age = new TextField();
        age.setPromptText("Age");

        TextField ad = new TextField();
        ad.setPromptText("Admission Date");

        gridPane.add(id, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(age, 0, 2);
        gridPane.add(ad, 0, 3);
        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("We are adding");
                if (database.AddChild(id.getText(), name.getText(), age.getText(), ad.getText())) {
                    dialog.close();
                    loadData();
                    common.dialogPopUp("INFORMATION", "A Child Added Successfully");
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
    private void updatechildAction(ActionEvent event) {
        int child_adm = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Child IAdmission No", "Update Child", JOptionPane.OK_CANCEL_OPTION));

        String _name = JOptionPane.showInputDialog(null, "Enter Child Name", "Update Child", JOptionPane.OK_CANCEL_OPTION);
        String _age = JOptionPane.showInputDialog(null, "Enter Age", "Update Child", JOptionPane.OK_CANCEL_OPTION);

        String _ad = JOptionPane.showInputDialog(null, "Enter  Admission Date", "Update Child", JOptionPane.OK_CANCEL_OPTION);

        String data[] = {
            _name, _age, _ad
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
            int savingsOk = JOptionPane.showConfirmDialog(null, "Child Name:\t" + _name + "\nAge:\t" + _age + "\nAdmission Date:\t" + _ad, "Confirm Details", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (savingsOk) {
                case 0:
                    if (database.editChild(_name, _age, _ad, child_adm)) {
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
    private void deletechildAction(ActionEvent event) {
        int child_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Child Admission No", "Delete Child Record", JOptionPane.OK_CANCEL_OPTION));
        int confirmDelete = JOptionPane.showConfirmDialog(null, "Delete Adoption?", "Delete Child Record", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (confirmDelete) {
            case 0:
                if (database.execAction("DELETE FROM children WHERE AdmNo='" + child_id + "'")) {
                    common.dialogPopUp("ERROR", "Deleted!");
                    loadData();
                } else {
                    common.dialogPopUp("ERROR", "Some Error Occured. Try Again");
                }
        }
    }

    public static class Child {

        private final SimpleStringProperty admno;
        private final SimpleStringProperty name;
        private final SimpleStringProperty age;
        private final SimpleStringProperty admissiondate;

        public Child(String NAME, String ADMISSIONDATE, String AGE, String ADMNO) {
            this.name = new SimpleStringProperty(NAME);
            this.admissiondate = new SimpleStringProperty(ADMISSIONDATE);
            this.age = new SimpleStringProperty(AGE);
            this.admno = new SimpleStringProperty(ADMNO);
        }

        public String getAdmno() {
            return admno.get();
        }

        public String getName() {
            return name.get();
        }

        public String getAge() {
            return age.get();
        }

        public String getAdmissiondate() {
            return admissiondate.get();
        }
    }
}
