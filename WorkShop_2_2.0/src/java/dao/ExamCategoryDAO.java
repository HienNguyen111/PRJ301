package dao;

import dto.ExamCategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class ExamCategoryDAO implements IDAO<ExamCategoryDTO, String>{

    @Override
    public boolean create(ExamCategoryDTO entity) {
        String sql = "INSERT INTO tblExamCategories"
                + " (category_id, category_name, description) "
                + " VALUES (?, ?, ?) ";
        
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getCategory_id()); 
            ps.setString(2, entity.getCategory_name());
            ps.setString(3, entity.getDescription());
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public List<ExamCategoryDTO> readAll() {
        return null;
    }

    @Override
    public ExamCategoryDTO readByID(String id) {
        return null;
    }

    @Override
    public boolean update(ExamCategoryDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<ExamCategoryDTO> search(String searchTerm) {
        return null;
    }
    
    
    // Các phương thức khác:
    public List<ExamCategoryDTO> viewExamCategory(){
        
        String sql = "SELECT * FROM tblExamCategories";
        List<ExamCategoryDTO> list = new ArrayList<>();
        
        try{
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ExamCategoryDTO examCate = new ExamCategoryDTO(
                                            rs.getInt("category_id"),
                                            rs.getString("category_name"),
                                            rs.getString("description"));
                list.add(examCate);
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }      
        return list;
    }    
}
