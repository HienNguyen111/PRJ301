package utils;

import dao.UserDAO;
import dto.UserDTO;
import javax.servlet.http.HttpSession;


// Tái sử dụng để kiểm tra người dùng có đăng nhập / có quyền admin hay ko
public class AuthUtils {
    
    public static final String ADMIN_ROLE = "Founder";
    public static final String USER_ROLE = "Team Member";
    
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
        return user.getRole().equals(ADMIN_ROLE);
    }
    
}
