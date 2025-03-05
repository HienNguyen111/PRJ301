package dao;

import dto.SUp_ProjectDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

public class SUp_ProjectDAO implements IDAO<SUp_ProjectDTO, String>{

    @Override
    public boolean create(SUp_ProjectDTO entity) {
        String sql = "INSERT [dbo].[tblStartupProjects] ([Project_id], [Project_name], [Description], [Status], [Estimated_launch])"
                + "VALUES (?,?,?,?,?)";
        
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getProject_id()); 
            ps.setString(2, entity.getProject_name());
            ps.setString(3, entity.getDescription());
            ps.setString(4, entity.getStatus());
            // Chuyển đổi LocalDate thành java.sql.Date trước khi set
            LocalDate estimatedLaunch = entity.getEstimated_launch();
            ps.setDate(5, (estimatedLaunch != null) ? Date.valueOf(estimatedLaunch) : null); 
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SUp_ProjectDTO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SUp_ProjectDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<SUp_ProjectDTO> readAll() {
        return null;
    }

    @Override
    public SUp_ProjectDTO readById(String id) {
        return null;
    }

    @Override
    public boolean update(SUp_ProjectDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<SUp_ProjectDTO> search(String searchTerm) {
        return null;
    }
    
    // Các phương thức khác:
    public List<SUp_ProjectDTO> searchByName(String searchTerm){
        
        String sql = "SELECT * FROM tblStartupProjects WHERE Project_name LIKE ? ";
        List<SUp_ProjectDTO> list = new ArrayList<>();
        
        try{
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                SUp_ProjectDTO pj = new SUp_ProjectDTO(
                                            rs.getInt("Project_id"),
                                            rs.getString("Project_name"),
                                            rs.getString("Description"),
                                            rs.getString("Status"),
                                            rs.getDate("Estimated_launch") != null ? rs.getDate("Estimated_launch").toLocalDate() : null);
                list.add(pj);
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }      
        return list;
    }    
    
    
    // Cập nhật trạng thái của SUp_Project
    public boolean updateStatus(int id, String status_update) {
        String sql = "UPDATE tblStartupProjects SET Status = ? WHERE Project_id = ? ";
    
        try (Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
                ps.setString(1, status_update);
                ps.setInt(2, id);
        
                int i = ps.executeUpdate();
                return i > 0; // Trả về true nếu có ít nhất một dòng được cập nhật

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi chi tiết để dễ debug
        }
        return false; // Trả về false nếu cập nhật thất bại
    }
    
        
}
