package controller;

import dao.BookDAO;
import dao.UserDAO;
import dto.BookDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {
    
    // Khai báo trang ban đầu là login.jsp
    private static final String LOGIN_PAGE = "login.jsp";
    
    // Phương thức:
    public UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        UserDTO user = udao.readById(strUserID);
        return user;
    }
    
    public boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        return user != null && user.getPassword().equals(strPassword);
    }
    
    // Main:
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            if(action == null){
                url = LOGIN_PAGE;
            }else{
                
                // Các action:
                // Đăng nhập
                if(action.equals("login")){
                    String strUserID = request.getParameter("txtUserID");
                    String strPassword = request.getParameter("txtPassword");
                    
                    if(isValidLogin(strUserID, strPassword)){
                        url = "search.jsp";
                        // In tên của người đăng nhập ra
                        UserDTO user = getUser(strUserID);
                        request.getSession().setAttribute("user", user);
                        
                    }else{
                        // In ra dòng thông báo lỗi
                        request.setAttribute("message", "Incorrect UserID or Password !");
                        // Quay trở lại trang login
                        url = "login.jsp";
                    }
                    
                // Thoát    
                }else if(action.equals("logout")){
                    request.getSession().invalidate(); // Hủy session = Hủy 1 phiên làm việc
                    url = "login.jsp";
                    
                // Tìm sách
                }else if(action.equals("search")){
                    BookDAO bdao = new BookDAO();
                    String searchTerm = request.getParameter("searchTerm"); // Lấy searchTerm từ trang search.jsp
                    List<BookDTO> books = bdao.searchByTitle2(searchTerm);
                    
                    // Truyền list books(ở trên) sang search.jsp
                    request.setAttribute("books", books);
                    // Truyền lại searchTerm
                    request.setAttribute("searchTerm", searchTerm);
                    
                    url = "search.jsp";
                
                // Xóa sách
                }else if(action.equals("delete")){
                    BookDAO bdao = new BookDAO();
                    String id = request.getParameter("id"); // Lấy BookID (ở search.jsp) để delete
                    bdao.updateQuantityToZero(id); 
                    
                    // Search
                    String searchTerm = request.getParameter("searchTerm");
                    List<BookDTO> books = bdao.searchByTitle2(searchTerm);
                    request.setAttribute("books", books);
                    request.setAttribute("searchTerm", searchTerm);
                    url = "search.jsp";
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
