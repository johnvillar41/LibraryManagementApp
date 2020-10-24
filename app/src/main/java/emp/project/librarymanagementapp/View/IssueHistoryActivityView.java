package emp.project.librarymanagementapp.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.librarymanagementapp.Controller.IssueBookHistoryController;
import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewIssueHistory;
import emp.project.librarymanagementapp.Interfaces.IssueHistoryInterface;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.R;

public class IssueHistoryActivityView extends AppCompatActivity implements IssueHistoryInterface.IssueHistoryViewInterface {
    TextView txt_number_books;
    RecyclerView recyclerView;
    Toolbar toolbar;
    CircleImageView circleImageView;
    IssueBookHistoryController controller = new IssueBookHistoryController(IssueHistoryActivityView.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_issue_history_view);

        try {
            InitViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            controller.directIssueBookDisplay(LoginActivityView.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void InitViews() throws SQLException, ClassNotFoundException {
        toolbar = findViewById(R.id.Toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        circleImageView=findViewById(R.id.image_emptycart);
        txt_number_books=findViewById(R.id.txt_number_books);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        controller.directCheckCart(LoginActivityView.getUsername());
        controller.directNumberBooks();
    }

    @Override
    public void displayIssueBooks(List<IssueBookModel> books) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(IssueHistoryActivityView.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewIssueHistory adapter = new RecyclerViewIssueHistory(
                IssueHistoryActivityView.this, books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayIfEmptyCart(boolean isEmpty) {
        if(isEmpty){
            circleImageView.setVisibility(View.VISIBLE);
        }else{
            circleImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void displayNumberOfBooks(int total_books) {
        txt_number_books.setText(String.valueOf(total_books));
        if(total_books>5){
            txt_number_books.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}