package emp.project.librarymanagementapp.Controller;

import android.os.StrictMode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import emp.project.librarymanagementapp.Interfaces.SignUpInterface;
import emp.project.librarymanagementapp.Models.LoginModel;
import emp.project.librarymanagementapp.View.SignUpActivityView;

public class SignUpController implements SignUpInterface.SignUpController {
    SignUpActivityView view;
    LoginModel model;

    public SignUpController(SignUpActivityView view) {
        this.view = view;
        this.model=new LoginModel();
    }
    @Override
    public void insertNewAccount(String username, String password, String passwordCheck) {
        model=new LoginModel(username,password,"Pending");
        if(model.validateSignUpCredentials(passwordCheck).equals("Successfull!")){
            DbHelper_SignUp db=new DbHelper_SignUp();
            try {
                db.insertNewAccount(model);
                view.eraseEditTexts();
                view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch(SQLIntegrityConstraintViolationException e){
                view.displayStatusMessage("Username already used!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(model.validateSignUpCredentials(passwordCheck).equals("Please Enter empty fields!")){
            view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
        }else{
            view.displayStatusMessage(model.validateSignUpCredentials(passwordCheck));
        }
    }

    private class DbHelper_SignUp implements SignUpInterface.Dbhelper_SignUp {
        private String DB_NAME="jdbc:mysql://192.168.1.152:3306/librarydb";
        private String USER="admin";
        private String PASS="admin";
        @Override
        public void Connection() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public void insertNewAccount(LoginModel model) throws ClassNotFoundException, SQLException {
            Connection();
            Connection connection= (Connection) DriverManager.getConnection(DB_NAME,USER,PASS);
            String sqlcmd="INSERT INTO userlogin(user_username,user_password,user_status)VALUES("+"'"+model.getUser_username()+"','"+model.getUser_password()+"','"+model.getUser_status()+"')";
            Statement statement= (Statement) connection.createStatement();
            statement.execute(sqlcmd);
        }
    }

}
