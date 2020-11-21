package emp.project.librarymanagementapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.librarymanagementapp.Interfaces.ILogin;
import emp.project.librarymanagementapp.Models.LoginModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

@SuppressWarnings("ALL")
public class LoginPresenter implements ILogin.LoginPresenterInterface {

    private ILogin.LoginViewInterface view;
    private LoginModel model;
    private Context context;

    public LoginPresenter(ILogin.LoginViewInterface view, Context context) {
        this.view = view;
        this.model = new LoginModel();
        this.context = context;
    }

    @Override
    public void getLoginCredentials(final String username, final String password) {
        view.progressBarVisible();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper db = new DbHelper();
                model = new LoginModel(username, password);
                try {
                    if (db.checkLoginCredentials(model) &&
                            model.validateCredentials()) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onSuccess("Successfully Logged In");
                                view.progressBarInvisible();
                            }
                        });
                    } else {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onFailure("Error!");
                                view.progressBarInvisible();
                            }
                        });
                    }
                } catch (ClassNotFoundException e) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onFailure("Error!");
                            view.progressBarInvisible();
                        }
                    });

                } catch (SQLException e) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onFailure("Error!");
                            view.progressBarInvisible();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    @Override
    public void goToSignUpForm() {
        view.signUpForm();
    }

    private class DbHelper implements ILogin.LoginDbHelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/librarydb";
        private String USER = LoginActivityView.getUsername();
        private String PASS = LoginActivityView.getPassword();

        @Override
        public void Connection() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public boolean checkLoginCredentials(LoginModel model) throws ClassNotFoundException, SQLException {
            Connection();
            Connection connection = (Connection) DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT * FROM userlogin WHERE user_username=" + "'" + model.getUser_username() + "' AND user_password=" + "'" + model.getUser_password() +
                    "' AND user_status='Active'";
            Statement statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            if (resultSet.next()) {
                resultSet.close();
                statement.close();
                connection.close();
                return true;
            } else {
                resultSet.close();
                statement.close();
                connection.close();
                return false;
            }
        }
    }
}
