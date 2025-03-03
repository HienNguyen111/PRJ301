package test;

import dao.UserDAO;
import dto.UserDTO;

public class UserTest {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
//        UserDTO ud1 = new UserDTO("TLN01", "Le Nhat Tung", "AD", "khong_co_password");
//        userDAO.create(ud1);
        for (int i = 0; i < 10; i++) {
            UserDTO ud_i = new UserDTO("USER"+i, "Nguyen Van "+i, "US", "_"+i);
            userDAO.create(ud_i);
        }
    } 
}
