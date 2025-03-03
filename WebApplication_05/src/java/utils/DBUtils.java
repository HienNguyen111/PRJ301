package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBUtils {
    
    private static final String DB_NAME = "Web_05_a";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD= "12345";
  
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
        return conn;
    }
    
//    public static void main(String[] args) {
//        try {
//            Connection c = getConnection();
//            System.out.println(c);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println(conn);
            
         
            String sql = "INSERT INTO products(product_id, product_name, price, category, stock_quantity) "
                    + " VALUES (11, N'Máy ảnh Sony A7 III', 4590000, N'Máy ảnh', 15) ";
            
            Statement st = conn.createStatement();
            int i = st.executeUpdate(sql);
            System.out.println(i);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
