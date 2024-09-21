import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Connection_class {
    Connection con;
    Statement stm;

    Connection_class() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ryandbit", "root", "Ryandsouza123$");
            stm = con.createStatement();
            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Connection_class();
    }

    Object getConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
