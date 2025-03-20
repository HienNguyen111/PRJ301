package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

public class UserDAO implements IDAO<UserDTO, String>{

    @Override
    public boolean create(UserDTO entity) {
        String sql = "INSERT [dbo].[tblUsers] ([Username], [Name], [Password], [Role])"
                + "VALUES (?,?,?,?)";
        Connection conn;
        try{
            conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getRole());
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<UserDTO> readAll() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [tblUsers]";
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                UserDTO user = new UserDTO(
                    rs.getString("Username"),
                    rs.getString("Name"),
                    rs.getString("Password"),
                    rs.getString("Role"));
                list.add(user);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public UserDTO readByID(String id) {
        String sql = "SELECT * FROM tblUsers WHERE Username = ? ";
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                UserDTO user = new UserDTO(
                    rs.getString("Username"),
                    rs.getString("Name"),
                    rs.getString("Password"),
                    rs.getString("Role"));
                return user;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean update(UserDTO entity) {
        String sql = "UPDATE [tblUsers] SET "
                + "[Name] = ?, "
                + "[Password] = ?, "
                + "[Role] = ? "
                + "WHERE [Username] = ? ";
        Connection conn;
        try{
            conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getRole());
            ps.setString(4, entity.getUsername());
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM [tblUsers] WHERE [Username] = ? ";
        Connection conn;
        
        try{
            conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<UserDTO> search(String searchTerm) {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT [Username], [Name], [Password], [Role] FROM [tblUses] "
                + "WHERE [Username] LIKE ? "
                + "OR [Name] LIKE ? "
                + "OR [Role] like ? ";
        
        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                
                String searchPattern = "%" + searchTerm + "%";
                // Thiết lập giá trị cho tất cả các tham số
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
                
        try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserDTO user = new UserDTO(
                            rs.getString("Username"),
                            rs.getString("Name"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    );
                    list.add(user);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
}
