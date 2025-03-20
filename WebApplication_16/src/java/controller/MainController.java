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
import javax.servlet.http.HttpSession;
import utils.AuthUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    // Khai báo trang ban đầu là login.jsp
    private static final String LOGIN_PAGE = "login.jsp";

    private BookDAO bookDAO = new BookDAO();

    // Tạo hàm nhỏ trong Main:
    public String processSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        // Cần đăng nhập mới search được
        HttpSession session = request.getSession();
        if (AuthUtils.isLoggedIn(session)) {
            String searchTerm = request.getParameter("searchTerm");
            if (searchTerm == null) {
                searchTerm = "";
            }
            List<BookDTO> books = bookDAO.searchByTitle2(searchTerm);
            // Truyền list books(ở trên) sang search.jsp
            request.setAttribute("books", books);
            // Truyền lại searchTerm
            request.setAttribute("searchTerm", searchTerm);
        }
        url = "search.jsp";
        return url;
    }

    private String processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        String strUserID = request.getParameter("txtUserID");
        String strPassword = request.getParameter("txtPassword");

        if (AuthUtils.isValidLogin(strUserID, strPassword)) {
            url = "search.jsp";
            // In tên của người đăng nhập ra
            UserDTO user = AuthUtils.getUser(strUserID);
            request.getSession().setAttribute("user", user);
            // search: Hiện sách khi đăng nhập thành công
            processSearch(request, response);
        } else {
            // In ra dòng thông báo lỗi
            request.setAttribute("message", "Incorrect UserID or Password !");
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

    private String processDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        // Cần quyền ADMIN để xóa
        HttpSession session = request.getSession();
        if (AuthUtils.isAdmin(session)) {
            String id = request.getParameter("id"); // Lấy BookID (ở search.jsp) để delete
            bookDAO.updateQuantityToZero(id);

            // search
            url = "search.jsp";
            processSearch(request, response);
        }
        //
        return url;
    }

    private String processAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        // Cần quyền ADMIN để thêm sách
        HttpSession session = request.getSession();
        if (AuthUtils.isAdmin(session)) {

            try {
                String bookID = request.getParameter("txtBookID");
                String title = request.getParameter("txtTitle");
                String author = request.getParameter("txtAuthor");
                int publishYear = Integer.parseInt(request.getParameter("txtPublishYear"));
                double price = Double.parseDouble(request.getParameter("txtPrice"));
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                String image = request.getParameter("txtImage");

                // Kiểm tra xem có lỗi ko
                Boolean checkError = false;

                if (bookID == null || bookID.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtBookID_error", "BookID cannot be empty !");
                }
                if (title == null || title.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtTitle_error", "Title cannot be empty !");
                }
                if (author == null || author.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtAuthor_error", "Author cannot be empty !");
                }
                if (publishYear <= 0) {
                    checkError = true;
                    request.setAttribute("txtPublishYear_error", "PublishYear must be greater than zero !");
                }
                if (price <= 0) {
                    checkError = true;
                    request.setAttribute("txtPrice_error", "Price must be greater than zero !");
                }
                if (quantity < 0) {
                    checkError = true;
                    request.setAttribute("txtQuantity_error", "Quantity must be must be greater than or equal to 0 !");
                }

                BookDTO book = new BookDTO(bookID, title, author, publishYear, price, quantity, image);

                if (checkError == false) {
                    bookDAO.create(book);
                    // search
                    url = processSearch(request, response);
                } else {
                    url = "bookForm.jsp";
                    request.setAttribute("book", book);
                }
            } catch (Exception e) {
                System.out.println("e.toString");
            }
        }
        //
        return url;
    }

    private String processEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        // Cần quyền ADMIN để edit
        HttpSession session = request.getSession();
        if (AuthUtils.isAdmin(session)) {
            String id = request.getParameter("id"); // Lấy BookID (ở search.jsp) để edit
            BookDTO book = bookDAO.readByID(id); // Lấy quyển sách
            if(book != null){
                request.setAttribute("book", book);
                request.setAttribute("action", "update");
                url = "bookForm.jsp";
            }
            // search
            processSearch(request, response);
        }
        //
        return url;
    }
        private String processUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //
        // Cần quyền ADMIN để thêm sách
        HttpSession session = request.getSession();
        if (AuthUtils.isAdmin(session)) {

            try {
                String bookID = request.getParameter("txtBookID");
                String title = request.getParameter("txtTitle");
                String author = request.getParameter("txtAuthor");
                int publishYear = Integer.parseInt(request.getParameter("txtPublishYear"));
                double price = Double.parseDouble(request.getParameter("txtPrice"));
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                String image = request.getParameter("txtImage");

                // Kiểm tra xem có lỗi ko
                Boolean checkError = false;

                if (bookID == null || bookID.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtBookID_error", "BookID cannot be empty !");
                }
                if (title == null || title.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtTitle_error", "Title cannot be empty !");
                }
                if (author == null || author.trim().isEmpty()) {
                    checkError = true;
                    request.setAttribute("txtAuthor_error", "Author cannot be empty !");
                }
                if (publishYear <= 0) {
                    checkError = true;
                    request.setAttribute("txtPublishYear_error", "PublishYear must be greater than zero !");
                }
                if (price <= 0) {
                    checkError = true;
                    request.setAttribute("txtPrice_error", "Price must be greater than zero !");
                }
                if (quantity < 0) {
                    checkError = true;
                    request.setAttribute("txtQuantity_error", "Quantity must be must be greater than or equal to 0 !");
                }

                BookDTO book = new BookDTO(bookID, title, author, publishYear, price, quantity, image);

                if (checkError == false) {
                    bookDAO.update(book);    // giống processAdd chỉ khác dòng này
                    // search
                    url = processSearch(request, response);
                } else {
                    url = "bookForm.jsp";
                    request.setAttribute("book", book);
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
        try {
            String action = request.getParameter("action");
            if (action == null) {
                url = LOGIN_PAGE;
            } else {

                // Các action:
                // Đăng nhập
                if (action.equals("login")) {
                    url = processLogin(request, response);

                    // Thoát    
                } else if (action.equals("logout")) {
                    url = processLogout(request, response);

                    // Tìm sách
                } else if (action.equals("search")) {
                    url = processSearch(request, response);

                    // Xóa sách
                } else if (action.equals("delete")) {
                    url = processDelete(request, response);

                    // Thêm sách
                } else if (action.equals("add_book")) {
                    url = processAdd(request, response);
                            
                    // Edit sách         
                } else if (action.equals("edit")) {
                    url = processEdit(request, response);
                    // Sau kih edit thêm thông tin thì update
                } else if (action.equals("update")) {
                    url = processUpdate(request, response);
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
