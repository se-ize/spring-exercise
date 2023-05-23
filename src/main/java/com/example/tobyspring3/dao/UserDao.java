package com.example.tobyspring3.dao;

import com.example.tobyspring3.db.ConnectChecker;
import com.example.tobyspring3.domain.User;
import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class UserDao {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                dbHost,
                dbUser,
                dbPassword
        );
        return con;
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());
        pstmt.executeUpdate(); //쿼리 update

        pstmt.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();

        PreparedStatement pstmt = con.prepareStatement("select id, name, password from users where id = ?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery(); //쿼리 select
        rs.next(); //ctrl+enter

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        pstmt.close();
        con.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("3");
        user.setName("sewoon");
        user.setPassword("0831");
//        userDao.add(user);

        User selectedUser = userDao.get("3");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());

    }
}
