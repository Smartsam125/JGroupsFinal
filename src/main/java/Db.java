
import  java.sql.*;
import   java.sql.Connection;
import org.jgroups.protocols.STOMP;

public class Db {

    public static Connection connection(){
        Connection conn =null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/se_share?characterEncoding=utf8","root","");

            System.out.println("success");
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
        return conn;
    }

}
