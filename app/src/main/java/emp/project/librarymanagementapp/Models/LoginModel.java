package emp.project.librarymanagementapp.Models;

public class LoginModel {
    String user_username,user_password,user_status;

    public LoginModel() {
    }

    public LoginModel(String user_username, String user_password) {
        this.user_username = user_username;
        this.user_password = user_password;
    }

    public LoginModel(String user_username, String user_password,String user_status) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_status=user_status;
    }

    public String getUser_status() {
        return user_status;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public boolean validateCredentials(){
        if(getUser_username().length()==0 || getUser_password().length()==0){
            return false;
        }else{
            return true;
        }
    }
    public String validateSignUpCredentials(String passwordCheck){
        if(getUser_username().length()==0 || getUser_password().length()==0){
            return "Please Enter empty fields!";
        }else{
            if(getUser_password().equals(passwordCheck)){
                return "Successfull!";
            }
            else{
                return "Passwords do not match!";
            }
        }
    }
}
