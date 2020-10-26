package emp.project.librarymanagementapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import emp.project.librarymanagementapp.Controller.NotificationController;
import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewIssueHistory;
import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewNotification;
import emp.project.librarymanagementapp.Interfaces.NotificationInterface;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.R;

public class NotificationActivityView extends AppCompatActivity implements NotificationInterface.NotificationViewInterface {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private NotificationController controller=new NotificationController(NotificationActivityView.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification_view);

        InitViews();
    }

    @Override
    public void InitViews() {
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        floatingActionButton=findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onRemoveButtonClicked();
            }
        });
        controller.getAllNotifications();
    }

    @Override
    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshPage() {
        Intent intent=new Intent(NotificationActivityView.this,NotificationActivityView.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void displayNotifications(List<NotificationModel>list) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(NotificationActivityView.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewNotification adapter = new RecyclerViewNotification(
                NotificationActivityView.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}