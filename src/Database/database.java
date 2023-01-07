package Database;

import static Base.common.dialogPopUp;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class database {

    private static final String url = "jdbc:mysql://localhost:3306/gmch";
    private static final String pass = "";
    private static final String user = "root";
    private static Connection con = null;
    private static Statement stmt = null;

    public static ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = con.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery:database " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public static void databaseCreator() throws SQLException, FileNotFoundException {
        {

//            String url = "jdbc:mysql://localhost/gmch";
//            Connection con = DriverManager.getConnection(url, "root", "");
//            System.out.println("Connection established ...");
//            // Initialize the script runner
//            ScriptRunner sr = new ScriptRunner(con);
//
//            File file = new File("src/Database/gmch.sql");
//            String fil = file.toString();
//            fil = file.getAbsolutePath();
//
//            System.out.println(file);
//            // Creating a reader object
//            Reader reader = new BufferedReader(new FileReader(file));
//            // running the script
//            sr.runScript(reader);
        }
    }

    public static boolean execAction(String query) {
        try {
            stmt = con.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:database " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean AddStaff(String Name, String id, String tel, String email, String address, String dep, String pass) {
        return execAction("INSERT INTO staff values(null, '" + Name + "', '" + tel + "', '" + email + "', '" + address + "', '" + dep + "', '" + pass + "')");
    }

    public static boolean editStaff(String _name, String _email, String _tel, String _address, String _dep, String _pass, int staff_id) {
        return execAction("UPDATE staff SET Name='" + _name + "', Phone='" + _tel + "', email='" + _email + "', PhysicalAddress='" + _address + "', Department='" + _dep + "', Password='" + _pass + "' WHERE StaffID='" + staff_id + "'");
    }

    public static boolean AddAdoption(String name, String id, String email, String age, String occupation, String address, String tel, String ad, String cadmno) {
        return execAction("INSERT INTO adoption VALUES(null, '" + name + "', '" + age + "', '" + occupation + "', '" + tel + "', '" + email + "','" + address + "','" + ad + "','" + cadmno + "')");
    }

    public static boolean editAdoption(String _name, String _age, String _occupation, String _tel, String _email, String _address, String _ad, String _admNo, int parent_id) {
        return execAction("UPDATE adoption SET Name='" + _name + "',Age='" + _age + "',Occupation='" + _occupation + "',Phone='" + _tel + "', Email='" + _email + "', PhysicalAddress='" + _address + "', AdoptionDate='" + _ad + "', ChildAdmNo='" + _admNo + "' WHERE ParentID='" + parent_id + "'");
    }

    public static boolean AddChild(String admNo, String name, String age, String admissionDate) {
        return execAction("INSERT INTO children VALUES(null,'" + name + "','" + age + "','" + admissionDate + "')");
    }

    public static boolean editChild(String _name, String _age, String _ad, int child_adm) {
        return execAction("UPDATE children SET Name='" + _name + "',Age='" + _age + "',AdmissionDate='" + _ad + "' WHERE AdmNo='" + child_adm + "'");
    }

    public static boolean editContact(String _name, String _tel, String _email, String _address, String _admNo, int contact_id) {
        return execAction("UPDATE contacts SET Name='" + _name + "',Phone='" + _tel + "',Email='" + _email + "',PhysicalAddress='" + _address + "', ChildAdmNo='" + _admNo + "' WHERE ContactID='" + contact_id + "'");
    }

    public static boolean AddContact(String id, String name, String tel, String email, String address, String childadm) {
        return execAction("INSERT INTO contacts VALUES(null,'" + name + "','" + tel + "','" + email + "','" + address + "','" + childadm + "')");
    }

    public static boolean AddDonation(String id, String origin, String type, String quantity, String date) {

        return execAction("INSERT INTO donation VALUES(null,'" + origin + "','" + type + "','" + quantity + "','" + date + "')");
    }

    public static boolean editDonation(String _origin, String _type, String _quantity, String _date, int contact_id) {
        return execAction("UPDATE donations SET Origin='" + _origin + "',Type='" + _type + "',Quantity='" + _quantity + "',Date='" + _date + "' WHERE DonationID='" + contact_id + "'");
    }

    public static boolean updateFoster(String _name, String _age, String _occupation, String _tel, String _email, String _address, String _ad, String _admNo, int parent_id) {
        return execAction("UPDATE fostering SET Name='" + _name + "',Age='" + _age + "',Occupation='" + _occupation + "',Phone='" + _tel + "', Email='" + _email + "', PhysicalAddress='" + _address + "', FosteringDate='" + _ad + "', ChildAdmNo='" + _admNo + "' WHERE ParentID='" + parent_id + "'");
    }

    public static boolean AddFoster(String name, String id, String email, String age, String occupation, String address, String tel, String ad, int cadmno) {
        return execAction("INSERT INTO fostering VALUES(null, '" + name + "', '" + age + "', '" + occupation + "', '" + tel + "', '" + email + "','" + address + "','" + ad + "','" + cadmno + "')");
    }

    public static boolean AddVolunteer(String id, String name, String age, String institution, String tel, String email, String address, String fromDate, String endDate) {
        return execAction("INSERT INTO volunteers VALUES(null, '" + name + "', '" + age + "', '" + institution + "', '" + tel + "', '" + email + "','" + address + "','" + fromDate + "','" + endDate + "')");
    }

    public database() {
        CreateConnection();
    }

    void CreateConnection() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     *
     * @param who
     * @return
     */
    public static String Password(int who) {
        String password = null;
        /**
         * Who = 1 change later
         */

        who = 1;
        try {
            con = DriverManager.getConnection(url, user, pass);

            String query = "select * from Admin where id='" + who + "' LIMIT 1";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                password = rs.getString("password");

            }

        } catch (SQLException e) {
            dialogPopUp("Error!", e.getMessage());
        }
        return password;

    }

    public static void loggedIn(String USER) {
        String sql1 = "DELETE  FROM loggedin";
        String sql = USER.equals("user") ? "INSERT INTO loggedin VALUES('user')" : "INSERT INTO loggedin VALUES('admin')";
        try {
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.execute();
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method checks the current logged in user
     * @return the username
     */
    public static String checkLoggedIn() {
        String userIn = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            String query = "SELECT title from loggedin LIMIT 1";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userIn = rs.getString("title");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userIn;
    }

}
