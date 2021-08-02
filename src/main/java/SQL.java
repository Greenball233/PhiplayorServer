import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;
import java.sql.*;
import java.util.Date;

public class SQL {
    public static void reg(String name,String passwd,String salt) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "Phiplayor", "114514");
        String addUser = "insert into user (name, password, salt) values(?,?,?);";
        PreparedStatement statement = connection.prepareStatement(addUser);
        statement.setString(1, name);
        statement.setString(2, passwd);
        statement.setString(3, salt);
        statement.executeUpdate();
        Logger.info("用户建立完毕", ServerMain.console);
        statement.close();
        connection.close();
    }
    public static String getSalt(String name) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "Phiplayor", "114514");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select salt from user where name='"+name+"'");
        rs.next();
        return rs.getString(1);
    }
    public static boolean login(String name,String password) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "Phiplayor", "114514");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select password from user where name='"+name+"'");
        rs.next();
        String passwd = rs.getString(1);
        return passwd.equals(password);
    }
    public static boolean TokenLogin(String token) {
        Date exp = parseJWT(token);
        return exp.after(new Date());
    }
    private static Date parseJWT(String jwt) {
//This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("XiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTAT"))
                .parseClaimsJws(jwt).getBody();
        return claims.getExpiration();
    }
//    public static void main(String[] args) {
//        try {
//            System.out.println(login("Greenball233", "aaaa"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
