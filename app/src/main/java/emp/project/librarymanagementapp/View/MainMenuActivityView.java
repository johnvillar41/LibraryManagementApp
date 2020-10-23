package emp.project.librarymanagementapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import emp.project.librarymanagementapp.Controller.MainMenuController;
import emp.project.librarymanagementapp.Interfaces.LoginInterface;
import emp.project.librarymanagementapp.Interfaces.MainMenuInterface;
import emp.project.librarymanagementapp.R;

public class MainMenuActivityView extends AppCompatActivity implements MainMenuInterface.MainMenuViewInterface, View.OnClickListener {
    TextView txt_username,txt_logout;
    CardView booksList,issueBooks,faqs,notifs;
    MainMenuController controller=new MainMenuController(MainMenuActivityView.this);
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
        txt_username=findViewById(R.id.txt_username);
        txt_logout=findViewById(R.id.txt_logout);
        booksList=findViewById(R.id.booksList);
        issueBooks=findViewById(R.id.issueBooks);
        faqs=findViewById(R.id.faqs);
        notifs=findViewById(R.id.notifs);

        txt_logout.setOnClickListener(this);
        booksList.setOnClickListener(this);
        issueBooks.setOnClickListener(this);
        faqs.setOnClickListener(this);
        notifs.setOnClickListener(this);

        setUsername();

    }

    @Override
    public void goToBooksList() {
        Toast.makeText(MainMenuActivityView.this,"BooksList",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainMenuActivityView.this,BookActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToIssueBooks() {
        Toast.makeText(MainMenuActivityView.this,"IssueBooks",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainMenuActivityView.this,IssueHistoryActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToFaqs() {
        Toast.makeText(MainMenuActivityView.this,"Faqs",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainMenuActivityView.this,FAQActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToNotifs() {
        Toast.makeText(MainMenuActivityView.this,"Notifications",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logOut() {
        Intent intent=new Intent(MainMenuActivityView.this,LoginActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setUsername() {
        txt_username.setText(LoginActivityView.getUsername());
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.booksList:{
                controller.directToBooksList();
                break;
            }
            case R.id.issueBooks:{
                controller.directToIssuelist();
                break;
            }
            case R.id.faqs:{
                controller.directToFaqs();
                break;
            }
            case R.id.notifs:{
                controller.directToNotifs();
                break;
            }
            case R.id.txt_logout:{
                controller.directToLogout();
                break;
            }
        }
    }
}