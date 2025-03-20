 package controller;

import dao.ExamCategoryDAO;
import dao.ExamDAO;
import dao.QuestionDAO;
import dto.ExamCategoryDTO;
import dto.ExamDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.AuthUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

   // Khai báo trang khởi đầu là login.jsp
   private static final String LOGIN_PAGE = "login.jsp";
   
   private ExamCategoryDAO E_CategoryDAO = new ExamCategoryDAO();
   private ExamDAO examDAO = new ExamDAO();
   private QuestionDAO questionDAO = new QuestionDAO();
    
// Tạo hàm nhỏ trong Main:
    protected String processViewCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        
        // Cần đăng nhập mới cho view
        HttpSession session = request.getSession();
        if (AuthUtils.isLoggedIn(session)) {
            
            List<ExamCategoryDTO> listExamCate = E_CategoryDAO.viewExamCategory();
            // Truyền list project (ở trên) sang dashBroad.jsp
            request.setAttribute("listExamCate", listExamCate);
            
        }
        return url;
    }
    
    protected String processViewExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        
        // Cần đăng nhập mới cho view
        HttpSession session = request.getSession();
        if (AuthUtils.isLoggedIn(session)) {
            
            List<ExamDTO> listExams = examDAO.viewExam();
            // Truyền list project (ở trên) sang dashBroad.jsp
            request.setAttribute("listExams", listExams);
            
        }
        return url;
    }
    
    private String processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        String strUsername = request.getParameter("txtUsername");
        String strPassword = request.getParameter("txtPassword");
        
        if (AuthUtils.isValidLogin(strUsername, strPassword)) {
            url = "dashBroad.jsp";
            // In tên của người đăng nhập ra
            UserDTO user = AuthUtils.getUser(strUsername);
            request.getSession().setAttribute("user", user);
            // View All khi đăng nhập thành công
            processViewCategory(request, response);
            processViewExam(request, response);
            
        } else {
            // In ra dòng thông báo lỗi
            request.setAttribute("message", "Incorrect Username or Password !");
            // Quay trở lại trang login
            url = "login.jsp";
        //
        } 
        return url;
    }

    private String processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        HttpSession session = request.getSession();
        if (AuthUtils.isLoggedIn(session)) {
            request.getSession().invalidate(); // Hủy bỏ session
            url = "login.jsp";
        }
        //
        return url;
    }
    
    private String processViewExamsByCategory(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    String url = LOGIN_PAGE;
    
    HttpSession session = request.getSession();
    if (AuthUtils.isLoggedIn(session)) {

        try {
            String strCategory_id = request.getParameter("txtCategory_id_viewExam");
            List<ExamDTO> listExamByCate = null;

            if (strCategory_id != null && !strCategory_id.isEmpty()) {
                try {
                    int categoryId = Integer.parseInt(strCategory_id);
                    listExamByCate = examDAO.getExamsByCategory(categoryId);
                    request.setAttribute("txtCategory_id_viewExam", categoryId);
                } catch (NumberFormatException e) {
                    log("Invalid category ID: " + strCategory_id);
                }
            }
            
            request.setAttribute("listExamByCate", listExamByCate);

        } catch (Exception e) {
            log("Error at processViewExamsByCategory: " + e.toString());
        }
        
        // Trở lại trang dashBroad.jsp
        processViewCategory(request, response);
        url = "dashBroad.jsp";
    }
    return url;
    }
    
    private String processAddExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        // Cần quyền ADMIN để Add Exam
        HttpSession session = request.getSession();
        if (AuthUtils.isAdmin(session)) {

            try {
                int exam_id = Integer.parseInt(request.getParameter("txtExam_id"));
                String exam_title = request.getParameter("txtExam_title");
                String subject = request.getParameter("txtSubject");
                int category_id = Integer.parseInt(request.getParameter("txtCategory_id"));
                int total_marks = Integer.parseInt(request.getParameter("txtTotal_marks"));
                int duration = Integer.parseInt(request.getParameter("txtDuration"));

                // Kiểm tra xem có lỗi ko
                Boolean checkError = false;

                if (exam_id <= 0) {
                    checkError = true;
                    request.setAttribute("txtExam_id_error", "Exam ID must be greater than zero !");
                }
                if (exam_title == null || exam_title.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtExam_title_error", "Exam Title cannot be empty !");
                }
                if (subject == null || subject.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtSubject_error", "Subject cannot be empty !");
                }
                if (category_id == 0) {
                    checkError = true;
                    request.setAttribute("txtCategory_id_error", "Please select a category !");
                }
                if (total_marks <= 0) {
                    checkError = true;
                    request.setAttribute("txtTotal_marks_error", "Total Marks must be greater than zero !");
                }
                if (duration <= 0) {
                    checkError = true;
                    request.setAttribute("txtDuration_error", "Duration must be greater than zero !");
                }

                ExamDTO newExam = new ExamDTO(exam_id, exam_title, subject, category_id, total_marks, duration);

                if (checkError == false) {
                    examDAO.create(newExam);
                    url = "dashBroad.jsp";
                } else {
                    url = "examForm.jsp";
                    request.setAttribute("newExam", newExam);
                }
            } catch (Exception e) {
                System.out.println("e.toString");
            }
        }
        //
        return url;
    }

    
    
    
    
    
    // Main:
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        
        String url = LOGIN_PAGE;
        try{
            String action = request.getParameter("action");
            if(action == null){
                url = LOGIN_PAGE;
            }else{
                
                // Các action:
                    // Đăng nhập
                if (action.equals("login")) {
                    url = processLogin(request, response);

                    // Thoát    
                }else if (action.equals("logout")) {
                    url = processLogout(request, response);
                    
                    // View Exam
                }else if (action.equals("viewExamsByCategory")) {
                    url = processViewExamsByCategory(request, response);
                
                    // Thêm Exam mới
                } else if (action.equals("add_exam")) {
                    url = processAddExam(request, response);
                    
                    // trở lại Trang 9
                } else if (action.equals("backToDashBroad")) {
                    processViewCategory(request, response);
                    processViewExam(request, response);
                    url = "dashBroad.jsp";
                }
                
                        
            }
            
            
        } catch (Exception e) {
            log("Error at MainController : " + e.toString());
        } finally {
            
            // Cách chuyển trang trên trang Web
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
