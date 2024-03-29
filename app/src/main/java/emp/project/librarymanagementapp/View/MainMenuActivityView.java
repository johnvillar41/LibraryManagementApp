package emp.project.librarymanagementapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import emp.project.librarymanagementapp.Interfaces.IMainMenu;
import emp.project.librarymanagementapp.Presenter.MainMenuPresenter;
import emp.project.librarymanagementapp.R;

public class MainMenuActivityView extends AppCompatActivity implements IMainMenu.MainMenuViewInterface, View.OnClickListener {
    private TextView txt_username, txt_logout;
    private MainMenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        InitViews();
    }

    @Override
    public void InitViews() {
        presenter = new MainMenuPresenter(MainMenuActivityView.this);
        txt_username = findViewById(R.id.txt_username);
        txt_logout = findViewById(R.id.txt_logout);
        CardView booksList = findViewById(R.id.booksList);
        CardView issueBooks = findViewById(R.id.issueBooks);
        CardView faqs = findViewById(R.id.faqs);
        CardView notifs = findViewById(R.id.notifs);

        txt_logout.setOnClickListener(this);
        booksList.setOnClickListener(this);
        issueBooks.setOnClickListener(this);
        faqs.setOnClickListener(this);
        notifs.setOnClickListener(this);

        setUsername();

    }

    @Override
    public void goToBooksList() {
        Toast.makeText(MainMenuActivityView.this, "BooksList", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivityView.this, BookActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToIssueBooks() {
        Toast.makeText(MainMenuActivityView.this, "IssueBooks", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivityView.this, IssueHistoryActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToFaqs() {
        Toast.makeText(MainMenuActivityView.this, "Faqs", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivityView.this, FAQActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToNotifs() {
        Toast.makeText(MainMenuActivityView.this, "Notifications", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivityView.this, NotificationActivityView.class);
        startActivity(intent);
    }

    @Override
    public void logOut() {
        Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        txt_logout.startAnimation(animFadein);
        Intent intent = new Intent(MainMenuActivityView.this, LoginActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setUsername() {
        txt_username.setText(LoginActivityView.getUsername());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booksList: {
                presenter.directToBooksList();
                break;
            }
            case R.id.issueBooks: {
                presenter.directToIssuelist();
                break;
            }
            case R.id.faqs: {
                presenter.directToFaqs();
                break;
            }
            case R.id.notifs: {
                presenter.directToNotifs();
                break;
            }
            case R.id.txt_logout: {
                presenter.directToLogout();
                break;
            }
        }
    }
}