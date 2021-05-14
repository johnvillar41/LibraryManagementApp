package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Models.LoginModel;

public interface ILogin {

    interface LoginViewInterface{

        void InitViews();

        void onSuccess(String message);

        void onFailure(String message);

        void progressBarVisible();

        void progressBarInvisible();

        void signUpForm();

    }
    interface LoginPresenterInterface {
        void getLoginCredentials(String username,String password);

        void goToSignUpForm();
    }
    interface ILoginRepository {
        boolean checkLoginCredentials(LoginModel model) throws ClassNotFoundException, SQLException;

        void Connection() throws ClassNotFoundException;

    }
}
