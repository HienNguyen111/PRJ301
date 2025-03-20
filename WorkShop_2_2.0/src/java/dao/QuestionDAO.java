package dao;

import dto.QuestionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import utils.DBUtils;

public class QuestionDAO implements IDAO<QuestionDTO, String>{

    @Override
    public boolean create(QuestionDTO entity) {
        String sql = "INSERT INTO tblQuestions"
                + " (question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        
        try{
            Connection conn = DBUtils.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getQuestion_id()); 
            ps.setInt(2, entity.getExam_id());
            ps.setString(3, entity.getQuestion_text());
            ps.setString(4, entity.getOption_a());
            ps.setString(5, entity.getOption_b());
            ps.setString(6, entity.getOption_c());
            ps.setString(7, entity.getOption_d());
            ps.setString(8, String.valueOf(entity.getCorrect_option()));

            int n = ps.executeUpdate();
            return n > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public List<QuestionDTO> readAll() {
        return null;
    }

    @Override
    public QuestionDTO readByID(String id) {
        return null;
    }

    @Override
    public boolean update(QuestionDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<QuestionDTO> search(String searchTerm) {
        return null;
    }
    
}
