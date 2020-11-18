package emp.project.librarymanagementapp.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import emp.project.librarymanagementapp.Interfaces.SignUpInterface;
import emp.project.librarymanagementapp.Presenter.SignUpPresenter;
import emp.project.librarymanagementapp.R;

@SuppressWarnings("ALL")
public class SignUpActivityView extends AppCompatActivity implements SignUpInterface.SignUpView {
    private Toolbar toolbar;
    private TextInputLayout txt_username, txt_password, txt_checkPassword;
    private Button btn_signup;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_view);

        InitViews();

    }

    @Override
    public void InitViews() {
        presenter = new SignUpPresenter(SignUpActivityView.this);
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txt_password = findViewById(R.id.txt_password);
        txt_username = findViewById(R.id.txt_username);
        txt_checkPassword = findViewById(R.id.txt_checkPassword);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.insertNewAccount(txt_username.getEditText().getText().toString(),
                        txt_password.getEditText().getText().toString(), txt_checkPassword.getEditText().getText().toString());
            }
        });
    }

    @Override
    public void displayStatusMessage(String message) {
        Toast.makeText(SignUpActivityView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void eraseEditTexts() {
        txt_username.getEditText().getText().clear();
        txt_password.getEditText().getText().clear();
        txt_checkPassword.getEditText().getText().clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }
}