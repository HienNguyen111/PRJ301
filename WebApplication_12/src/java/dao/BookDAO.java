package dao;

import dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

public class BookDAO implements IDAO<BookDTO, String> {

    @Override
    public boolean create(BookDTO entity) {
        String sql = "INSERT [dbo].[tblBooks] ([BookID], [Title], [Author], [PublishYear], [Price], [Quantity])"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getBookID()); 
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getAuthor());
            ps.setInt(4, entity.getPublishYear());
            ps.setDouble(5, entity.getPrice());
            ps.setInt(6, entity.getQuantity());
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<BookDTO> readAll() {
        return null;
    }

    @Override
    public BookDTO readById(String id) {
        return null;
    }

    @Override
    public boolean update(BookDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<BookDTO> search(String searchTerm) {
        return null;
    }
    
    
    // Các phương thức:
    
    // Tìm book bằng title:
    public List<BookDTO> searchByTitle(String searchTerm) {
        
        String sql ="SELECT * FROM tblBooks WHERE Title LIKE ? ";
        List<BookDTO> list = new ArrayList<>();
        
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            
            // executeQuery() : dùng khi ko làm thay đổi dữ liệu
            // executeUpdate() : dùng khi update, delete, insert
            ResultSet rs = ps.executeQuery();
            
            // Lấy Book (đúng theo yêu cầu) ở trong SQL
            while(rs.next()) {
                BookDTO book = new BookDTO(
                                    rs.getString("BookID"), 
                                    rs.getString("Title"), 
                                    rs.getString("Author"), 
                                    rs.getInt("PublishYear"), 
                                    rs.getDouble("Price"), 
                                    rs.getInt("Quantity"));
                // Ép Book vào list để in ra
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }      
        return list;
    }
    
    // Tìm book bằng title nhưng chỉ hiện book có quantity > 0
    public List<BookDTO> searchByTitle2(String searchTerm) {
        
        String sql ="SELECT * FROM tblBooks WHERE Title LIKE ? AND Quantity > 0 ";
        List<BookDTO> list = new ArrayList<>();
        
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                BookDTO book = new BookDTO(
                                    rs.getString("BookID"), 
                                    rs.getString("Title"), 
                                    rs.getString("Author"), 
                                    rs.getInt("PublishYear"), 
                                    rs.getDouble("Price"), 
                                    rs.getInt("Quantity"));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }      
        return list;
    }
    
    // Xóa book bằng BookID
    public boolean updateQuantityToZero(String id) {
        String sql = "UPDATE tblBooks SET Quantity = 0 WHERE BookID = ? ";
                
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int i = ps.executeUpdate();
            return i>0;
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
        
    

}
