package utils;

import dao.UserDAO;
import dto.UserDTO;
import javax.servlet.http.HttpSession;


// Tái sử dụng để kiểm tra người dùng có đăng nhập / có quyền admin hay ko
public class AuthUtils {
    
    public static final String ADMIN_ROLE = "AD";
    public static final String USER_ROLE = "US";
    
    // PT kiểm tra người dùng đăng nhập hay ch
    public static boolean isLoggedIn(HttpSession session){
        return session.getAttribute("user") != null;
    }
    
    // PT kiểm tra admin
    public static boolean isAdmin(HttpSession session){
        if(isLoggedIn(session) == false){
            return false;
        }
        UserDTO user = (UserDTO)session.getAttribute("user");
        return user.getRoleID().equals(ADMIN_ROLE);
    }
    
    
    
    // Phương thức trong MainController:
    public static UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        UserDTO user = udao.readByID(strUserID);
        return user;
    }
    
    public static boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        return user != null && PasswordUtils.checkPassword(strPassword, user.getPassword());  // Sửa: thêm chức năng mã hóa mật khẩu PasswordUtils
    }
    
    public static UserDTO getUser(HttpSession session){
//        if(session.getAttribute("user") != null){
//            return (UserDTO)session.getAttribute("user");
//        }else{
//            return null;
//        }

    Object obj = session.getAttribute("user");
    return (obj != null)?(UserDTO)obj:null;
    }
    
    
}
