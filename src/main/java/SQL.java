import java.sql.*;

public class SQL {
    public static void reg(String name,String passwd,String salt) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "123456");
        String addUser = "insert into user (name, password, salt) values(?,?,?);";
        PreparedStatement statement = connection.prepareStatement(addUser);
        statement.setString(1, name);
        statement.setString(2, passwd);
        statement.setString(3, salt);
        statement.executeUpdate();
        System.out.println(statement);
        statement.close();
        connection.close();
    }
}
