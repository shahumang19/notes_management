/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Poras
 */
public class NoteDAO {

    private Connection con;
    private String QueryString;

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3307/shazam", "chaka", "abc123");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean insertNote(NoteBL newNote,String userID) {
        connectDatabase();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean flag = false;
        this.QueryString = "insert into Note values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(QueryString);
            ps.setInt(1, newNote.getId());
            ps.setString(2, newNote.getCat());
            ps.setString(3, newNote.getTitle());
            ps.setString(4, newNote.getTextData());
            ps.setInt(5, newNote.getPriority());
            ps.setBoolean(6, newNote.isLock());
            ps.setDate(7, java.sql.Date.valueOf(newNote.getCreationDate().toString()));
            ps.setTime(8, java.sql.Time.valueOf(newNote.getCreationTime().toString()));
            if (ps.executeUpdate() == 1 && ps.executeUpdate("insert into ownernotes values('" + userID + "'," + newNote.getId() + ",true)") == 1) {
                con.commit();
                flag = true;
            } else {
                con.rollback();
                flag = false;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateNote(NoteBL note) {
        connectDatabase();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean flag = false;
        this.QueryString = "update Note set category=?,title=?,text=?,priority=?,isLocked=? where id=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(QueryString);
            ps.setString(1, note.getCat());//
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getTextData());
            ps.setInt(4, note.getPriority());
            ps.setBoolean(5, note.isLock());
            ps.setInt(6, note.getId());//id
            if (ps.executeUpdate() == 1) {
                con.commit();
                flag = true;
            } else {
                con.rollback();
                flag = false;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean deleteNote(NoteBL note, String userID) {
        connectDatabase();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean flag = false;
        this.QueryString = "delete from Note where id=?";
        try {
            PreparedStatement ps = con.prepareStatement("select * from ownernotes where email=? and noteid=?");
            ps.setString(1, userID);
            ps.setInt(2, note.getId());
            ResultSet tmp = ps.executeQuery();
            tmp.next();
            if (tmp.getBoolean(3)) {
                ps = con.prepareStatement("delete from ownernotes where noteid=" + tmp.getInt(2));
                if (ps.executeUpdate() == 1) {
                    ps = con.prepareStatement(QueryString);
                    ps.setInt(1, note.getId());
                    if (ps.executeUpdate() == 1) {
                        con.commit();
                        flag = true;
                    } else {
                        con.rollback();
                        flag = false;
                    }
                }
            } else {
                ps = con.prepareStatement("delete from ownernotes where email=?,noteid=?");
                ps.setString(1, userID);
                ps.setInt(2, note.getId());
                if (ps.executeUpdate() == 1) {
                    ps = con.prepareStatement(QueryString);
                    ps.setInt(1, note.getId());
                    if (ps.executeUpdate() == 1) {
                        con.commit();
                        flag = true;
                    } else {
                        con.rollback();
                        flag = false;
                    }
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public ArrayList<NoteBL> readData(UserBL user) {
        connectDatabase();
        ArrayList<NoteBL> data = null;
        ResultSet rs;
        QueryString = "select * from Note where id in( select noteid from ownernotes where email = ?);";
        try {
            PreparedStatement st = con.prepareStatement(QueryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, user.getEmail());
            rs = st.executeQuery();
            if (rs == null) {
                return data;
            }
            data = new ArrayList<NoteBL>();
            NoteBL temp;
            while (rs.next()) {
                temp = new NoteBL();
                temp.setId(rs.getInt(1));
                temp.setCat(rs.getString(2));
                temp.setTitle(rs.getString(3));
                temp.setTextData(rs.getString(4));
                temp.setPriority(rs.getInt(5));
                temp.setLock(rs.getBoolean(6));
                temp.setCreationDate(rs.getDate(7));
                temp.setCreationTime(rs.getTime(8));
                data.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int generateNewID() {
        connectDatabase();
        int newID = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from Note");
            rs.next();
            newID = rs.getInt(1);
            newID++;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newID;
    }

    public NoteBL readNote(int id) {
        connectDatabase();
        NoteBL temp = null;
        QueryString = "select * from Note where id = ?;";
        try {
            PreparedStatement st = con.prepareStatement(QueryString);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                temp = new NoteBL();
                temp.setId(rs.getInt(1));
                temp.setCat(rs.getString(2));
                temp.setTitle(rs.getString(3));
                temp.setTextData(rs.getString(4));
                temp.setPriority(rs.getInt(5));
                temp.setLock(rs.getBoolean(6));
                temp.setCreationDate(rs.getDate(7));
                temp.setCreationTime(rs.getTime(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }
}
