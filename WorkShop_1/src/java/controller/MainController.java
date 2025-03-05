package controller;

import dao.SUp_ProjectDAO;
import dao.UserDAO;
import dto.SUp_ProjectDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
   
   private SUp_ProjectDAO projectDAO = new SUp_ProjectDAO();
   
   // Phương thức: kiểm tra user hợp lệ ko
    public UserDTO getUser(String strUsername){
        UserDAO udao = new UserDAO();
        UserDTO user = udao.readById(strUsername);
        return user;
    }
    public boolean isValidLogin(String strUsername, String strPassword){
         UserDTO user = getUser(strUsername);
        return user != null && user.getPassword().equals(strPassword);
    }
    
    // Tạo hàm search(dùng nhiều/bị trùng code)
    protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("searchTerm");
        if(searchTerm == null){
            searchTerm = "";
        }
        List<SUp_ProjectDTO> search_pj = projectDAO.searchByName(searchTerm);
        // Truyền list project (ở trên) sang search.jsp
        request.setAttribute("search_pj", search_pj);
        // Truyền lại searchTerm (vẫn giữ nội dung tìm kiếm ở ô)
        request.setAttribute("searchTerm", searchTerm);
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
                if(action.equals("login")){
                    String strUsername = request.getParameter("txtUsername");
                    String strPassword = request.getParameter("txtPassword");
                    
                    if(isValidLogin(strUsername, strPassword)){
                        url = "search.jsp";
                        // Giúp in tên của người đăng nhập
                        UserDTO user = getUser(strUsername);
                        request.getSession().setAttribute("user", user);
                        // search: hiện tất cả project khi login thành công
                        search(request, response);
                    }else{
                        // In ra dòng thông báo lỗi
                        request.setAttribute("message", "Incorrect UserID or Password !");
                        // Quay trở lại trang login
                        url = "login.jsp";
                    }
                
                // Thoát
                }else if(action.equals("logout")){
                    HttpSession session = request.getSession();
                    if (AuthUtils.isLoggedIn(session)) {
                        request.getSession().invalidate(); // Hủy session = Hủy 1 phiên làm việc
                        url = "login.jsp";
                        }
                // Tìm SUp_Project
                }else if(action.equals("search")){
                    HttpSession session = request.getSession();
                    if (AuthUtils.isLoggedIn(session)) {
                        // search
                        search(request, response);
                        url = "search.jsp";
                    }
                    
                // Update Project Status
                }else if(action.equals("updateStatus")){
                    HttpSession session = request.getSession();
                    if (AuthUtils.isAdmin(session)) {
                        int project_id = Integer.parseInt(request.getParameter("project_id")); // Lấy Project_id (ở search.jsp) để update
                        String status_update = request.getParameter("status_update");
                        projectDAO.updateStatus(project_id, status_update);

                        boolean success = projectDAO.updateStatus(project_id, status_update);

                        if (success) {
                            request.setAttribute("messageUpdate", "Update successful!");
                        } else {
                            request.setAttribute("messageUpdate", "Update failed!");
                        }
                        // search
                        search(request, response);
                        url = "search.jsp";
                    }
                    
                // Thêm New Project    
                }else if(action.equals("add_project")){
                    
                    HttpSession session = request.getSession();
                    if (AuthUtils.isAdmin(session)) {
                        try {
                            int project_id = Integer.parseInt(request.getParameter("txtProjectID"));
                            String project_name = request.getParameter("txtProjectName");
                            String description = request.getParameter("txtDescription");
                            String status = request.getParameter("txtStatus");
                            String estimatedLaunchStr = request.getParameter("txtEstimatedLaunch");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Định dạng phù hợp với dữ liệu đầu vào
                            LocalDate estimated_launch = LocalDate.parse(estimatedLaunchStr, formatter);


                            // Kiểm tra xem có lỗi ko
                            Boolean checkError = false;

                            if(project_id < 0){
                                checkError = true;
                                request.setAttribute("txtProjectID_error", "ProjectID must be greater than zero !");
                            }
                            if(project_name == null || project_name.trim().isEmpty()){
                                checkError = true;
                                request.setAttribute("txtProjectName_error", "Project Name cannot be empty !");
                            }
                            if(description == null || description.trim().isEmpty()){
                                checkError = true;
                                request.setAttribute("txtDescription_error", "Description cannot be empty !");
                            }
                            if(status == null || status.trim().isEmpty()){
                                checkError = true;
                                request.setAttribute("txtStatus_error", "Status cannot be empty !");
                            }
                            if(estimatedLaunchStr == null || estimatedLaunchStr.trim().isEmpty()){
                                checkError = true;
                                request.setAttribute("txtEstimatedLaunch_error", "Estimated Launch cannot be empty !");
                            }


                            SUp_ProjectDTO pj = new SUp_ProjectDTO(project_id, project_name, description, status, estimated_launch);

                            if(checkError == false){
                                projectDAO.create(pj);
                                // search
                                search(request, response);
                                url = "search.jsp";
                            }else{
                                url = "projectForm.jsp";
                                request.setAttribute("pj", pj);
                            }
                        } catch (Exception e) {
                            System.out.println("e.toString");
                        }
                    }
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
