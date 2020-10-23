package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Models.LoginModel;

public interface SignUpInterface {
    interface SignUpView{
        void InitViews();

        void displayStatusMessage(String message);

        void eraseEditTexts();
    }
    interface SignUpController{
        void insertNewAccount(String username,String password,String passwordCheck);

    }
    interface Dbhelper_SignUp {
        void Connection() throws ClassNotFoundException;

        void insertNewAccount(LoginModel model) throws ClassNotFoundException, SQLException;
    }

}
