package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Models.LoginModel;

public interface ISignUp {
    interface SignUpView{
        void InitViews();

        void displayStatusMessage(String message);

        void eraseEditTexts();
    }
    interface SignUpPresenter {
        void insertNewAccount(String username,String password,String passwordCheck);

    }
    interface Dbhelper_SignUp {
        void Connection() throws ClassNotFoundException;

        void insertNewAccount(LoginModel model) throws ClassNotFoundException, SQLException;
    }

}
