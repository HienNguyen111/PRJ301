package dao;

import dto.ExamCategoryDTO;
import dto.ExamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class ExamDAO implements IDAO<ExamDTO, String>{

    @Override
    public boolean create(ExamDTO entity) {
        String sql = "INSERT INTO tblExams"
                + " (exam_id, exam_title, subject, category_id, total_marks, duration) "
                + " VALUES (?, ?, ?, ?, ?, ?) ";
        
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getExam_id()); 
            ps.setString(2, entity.getExam_title());
            ps.setString(3, entity.getSubject());
            ps.setInt(4, entity.getCategory_id());
            ps.setInt(5, entity.getTotal_marks());
            ps.setInt(6, entity.getDuration());
            
            int n = ps.executeUpdate();
            return n > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public List<ExamDTO> readAll() {
        return null;
    }

    @Override
    public ExamDTO readByID(String id) {
        return null;
    }

    @Override
    public boolean update(ExamDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<ExamDTO> search(String searchTerm) {
        return null;
    }
    
    
    
    // Các phương thức khác:

    public List<ExamDTO> viewExam(){
        
        String sql = "SELECT * FROM tblExams";
        List<ExamDTO> list = new ArrayList<>();
        
        try{
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ExamDTO e = new ExamDTO(
                                        rs.getInt("exam_id"),
                                        rs.getString("exam_title"),
                                        rs.getString("subject"),
                                        rs.getInt("category_id"),
                                        rs.getInt("total_marks"),
                                        rs.getInt("duration"));
                list.add(e);
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }      
        return list;
    }  

    public static List<ExamDTO> getExamsByCategory(int categoryId) {
        List<ExamDTO> exams = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE category_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ExamDTO e = new ExamDTO(
                                rs.getInt("exam_id"),
                                rs.getString("exam_title"),
                                rs.getString("subject"),
                                rs.getInt("category_id"),
                                rs.getInt("total_marks"),
                                rs.getInt("duration"));
                exams.add(e);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } 
        return exams;
    }
}
