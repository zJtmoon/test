package Jdbc;
import pojo.Chair;
import pojo.User;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Jdbc {

    private static final String URL="jdbc:mysql://127.0.0.1:3306/jsp?useUnicode=true&characterEncoding=utf-8";
    private static final String USER="root";
    private static final String PASSWORD="123456";
    private User user;

    private static Connection conn=null;

    static {
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库的连接
            conn=DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //将获得的数据库与java的链接返回（返回的类型为Connection）
    public static Connection getConnection(){
        return conn;
    }
    public void userinsert(User user) throws SQLException {
        String sql="insert into user(username,password,sex,schoolage) values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getSex());
        ps.setString(4,user.getSchoolage());
        ps.executeUpdate();
        ps.close();

    }
    public String loginuser(String s) throws SQLException {
        String sql="select password from user where username=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,s);
        ResultSet set=ps.executeQuery();
        String s1=null;
        if(set.next()){
            s1=set.getString("password");

        }

        return set.getString("password");
    }
    public ArrayList<Chair> bookchair() throws SQLException {
        String sql="select * from chairs";
        PreparedStatement ps = null;
        try {
             ps=conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs=ps.executeQuery();
        ArrayList<Chair>  list=new ArrayList();

        while(rs.next()){
            Chair chair=new Chair();
            chair.setBool(rs.getString("bool"));
            chair.setId(rs.getInt("id"));
            chair.setFloor(rs.getInt("floor"));
            list.add(chair);
            System.out.println(rs.getInt("id")+"====================================");
        }

        return list;
    }
    public void order(String id) {
        String sql="update chairs set bool='否' where id=?";
        PreparedStatement ps=null;
        try {
             ps=conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}