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
        String sql = "INSERT INTO tblBooks"
                + " (BookID, Title, Author, PublishYear, Price, Quantity, Image) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try {
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getBookID()); 
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getAuthor());
            ps.setInt(4, entity.getPublishYear());
            ps.setDouble(5, entity.getPrice());
            ps.setInt(6, entity.getQuantity());
            ps.setString(7, entity.getImage());
            
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
        List<BookDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM tblBooks";
        
         try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                BookDTO b = new BookDTO(
                        rs.getString("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image")
                );
                result.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    @Override
    public BookDTO readByID(String id) {
        String sql = "SELECT * FROM tblBooks WHERE BookID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookDTO b = new BookDTO(
                        rs.getString("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image")
                );
                return b;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean update(BookDTO entity) {
        String sql = "UPDATE tblBooks SET "
                + " Title=?, Author=?, PublishYear=?, Price=?, Quantity=?, Image=? "
                + "  WHERE BookID=?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getAuthor());
            ps.setInt(3, entity.getPublishYear());
            ps.setDouble(4, entity.getPrice());
            ps.setInt(5, entity.getQuantity());
            ps.setString(6, entity.getImage());
            ps.setString(7, entity.getBookID());
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM tblBooks WHERE BookID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public List<BookDTO> search(String searchTerm) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM tblBooks WHERE Title LIKE ? OR Author LIKE ? OR BookID LIKE ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            String param = "%" + searchTerm + "%";
            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO b = new BookDTO(
                        rs.getString("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image")
                );
                result.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
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
                                    rs.getInt("Quantity"),
                                    rs.getString("Image"));
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
                                    rs.getInt("Quantity"),
                                    rs.getString("Image"));
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
            return i > 0;
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
        
    public boolean checkBookIDExist(String bookID) {
        String sql = "SELECT COUNT(*) FROM tblBooks WHERE BookID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
    
    public BookDTO getBookByID(String bookID) {
        return readByID(bookID);
    }
    
    public boolean addBook(BookDTO book) {
        return create(book);
    }
    
    public boolean updateBook(BookDTO book) {
        return update(book);
    }
    
    public boolean deleteBook(String bookID) {
        return delete(bookID);
    }

}
