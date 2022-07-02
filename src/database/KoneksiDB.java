package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDB {
    
    public Connection connection;
    
    public KoneksiDB(){
    
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db_penilaian",
                    "root","");
            //JOptionPane.showMessageDialog(null, "Terhubung Database");
        }
        catch(SQLException se){
            JOptionPane.showMessageDialog(null, "Tidak Terhubung Database");
            System.exit(0);
        }
        catch(Exception x){
            x.printStackTrace();
        }
        
    }
    
}
