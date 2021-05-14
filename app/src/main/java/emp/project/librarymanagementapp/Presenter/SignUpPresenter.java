package emp.project.librarymanagementapp.Presenter;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import emp.project.librarymanagementapp.Interfaces.ISignUp;
import emp.project.librarymanagementapp.Models.LoginModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class SignUpPresenter implements ISignUp.SignUpPresenter {
    private final ISignUp.SignUpView view;
    private final ISignUp.ISignUpRepository repository;

    public SignUpPresenter(ISignUp.SignUpView view,ISignUp.ISignUpRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void insertNewAccount(String username, String password, String passwordCheck) {
        LoginModel model = new LoginModel(username, password, "Pending");
        if (model.validateSignUpCredentials(passwordCheck).equals("Successfull!")) {

            try {
                repository.insertNewAccount(model);
                view.eraseEditTexts();
                view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLIntegrityConstraintViolationException e) {
                view.displayStatusMessage("Username already used!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (model.validateSignUpCredentials(passwordCheck).equals("Please Enter empty fields!")) {
            view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
        } else {
            view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
        }
    }
}
