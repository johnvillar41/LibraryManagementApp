package emp.project.librarymanagementapp.Presenter;

import android.app.Activity;
import android.content.Context;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Interfaces.ILogin;
import emp.project.librarymanagementapp.Models.LoginModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class LoginPresenter implements ILogin.LoginPresenterInterface {

    private final ILogin.LoginViewInterface view;
    private final ILogin.ILoginRepository repository;

    public LoginPresenter(ILogin.LoginViewInterface view, ILogin.ILoginRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getLoginCredentials(final String username, final String password) {
        view.progressBarVisible();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LoginModel model = new LoginModel(username, password);
                try {
                    if (repository.checkLoginCredentials(model) &&
                            model.validateCredentials()) {
                        view.onSuccess("Successfully Logged In");
                    } else {
                        view.onFailure("Error!");
                    }
                    view.progressBarInvisible();
                } catch (ClassNotFoundException e) {
                    view.onFailure("Error!");
                    view.progressBarInvisible();
                } catch (SQLException e) {
                    view.onFailure("Error!");
                    view.progressBarInvisible();
                }
            }
        });
        thread.start();
    }

    @Override
    public void goToSignUpForm() {
        view.signUpForm();
    }
}
