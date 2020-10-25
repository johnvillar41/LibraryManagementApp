package emp.project.librarymanagementapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import emp.project.librarymanagementapp.Controller.NotificationController;
import emp.project.librarymanagementapp.Interfaces.NotificationInterface;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.R;

public class NotificationActivityView extends AppCompatActivity implements NotificationInterface.NotificationViewInterface {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;;
    private NotificationController controller=new NotificationController(NotificationActivityView.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        InitViews();
    }

    @Override
    public void InitViews() {
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        floatingActionButton=findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onRemoveButtonClicked();
            }
        });
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
        //Display RecyclerView
    }
}