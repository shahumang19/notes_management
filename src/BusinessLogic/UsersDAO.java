package BusinessLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Poras
 */
public class UsersDAO {

    private Connection con;
    private String QueryString;

    public UsersDAO() {
        //this.connectDatabase();
    }
    
    

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3307/shazam", "chaka", "abc123");
        } catch (Exception ex) {
            //Logger.getLogger(AllInOneDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public boolean insertUser(Object... parameters) {
        connectDatabase();
        boolean flag = false;
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
        this.QueryString = "insert into users values";
        this.QueryString += "(?,?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(QueryString);
            ps.setString(1, parameters[0].toString());
            ps.setString(2, parameters[1].toString());
            ps.setString(3, parameters[2].toString());
            System.out.println(QueryString);
            System.out.println(parameters[0].toString());
            System.out.println(parameters[1].toString());
            System.out.println(parameters[2].toString());
            if (ps.executeUpdate() == 1) {
                con.commit();
                flag = true;
            } else {
                con.rollback();
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
        return flag;
    }

    public boolean updateUser(Object... parameters) {
        connectDatabase();
        boolean flag = false;
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
        this.QueryString = "update users set Name=?,Password=? where Email=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(QueryString);
            ps.setString(1, parameters[1].toString());
            ps.setString(2, parameters[2].toString());
            ps.setString(3, parameters[0].toString());
            if (ps.executeUpdate() == 1) {
                con.commit();
                flag = true;
            } else {
                con.rollback();
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
        return flag;
    }

    public String validateUser(String unm, String pwd) {
        //boolean flag = false;
        String name = "";
        try {
            connectDatabase();
            java.sql.Statement st = con.createStatement();
            System.out.println("select Name from users where Email='" + unm + "' and Password='" + pwd + "'");
            ResultSet r1 = st.executeQuery("select Name from users where Email='" + unm + "' and Password='" + pwd + "'");
            if (r1.next()) {
                name = r1.getString(1);
                System.out.println(name);
                return name;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        con.close();
    }
    
    
}
