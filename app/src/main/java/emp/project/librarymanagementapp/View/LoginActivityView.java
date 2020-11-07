package emp.project.librarymanagementapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import emp.project.librarymanagementapp.Presenter.LoginPresenter;
import emp.project.librarymanagementapp.Interfaces.LoginInterface;
import emp.project.librarymanagementapp.R;

@SuppressWarnings({"Convert2Lambda", "FieldCanBeLocal"})
public class LoginActivityView extends AppCompatActivity implements LoginInterface.LoginViewInterface, View.OnClickListener {
    private EditText txt_username, txt_password;
    private Button btn_login;
    private ProgressBar progressBar;
    private TextView txt_signUp;
    public static String username,password;
    private LoginPresenter presenter = new LoginPresenter(LoginActivityView.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_view);

        InitViews();
    }

    @Override
    public void InitViews() {
        txt_signUp = findViewById(R.id.txt_signUp);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);

        btn_login.setOnClickListener(this);
        txt_signUp.setOnClickListener(this);
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(LoginActivityView.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivityView.this, MainMenuActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(LoginActivityView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressBarInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void signUpForm() {
        Intent intent = new Intent(LoginActivityView.this, SignUpActivityView.class);
        startActivity(intent);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoginActivityView.username = username;
    }

    public static String getPassword() {  return password; }

    public static void setPassword(String password) { LoginActivityView.password = password; }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            presenter.getLoginCredentials(txt_username.getText().toString(),
                    txt_password.getText().toString());
            setUsername(txt_username.getText().toString());
            setPassword(txt_password.getText().toString());
        } else if (v.getId() == R.id.txt_signUp) {
            Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            txt_signUp.startAnimation(animFadein);
            presenter.goToSignUpForm();
        }
    }
}