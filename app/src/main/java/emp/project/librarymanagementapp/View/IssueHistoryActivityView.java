package emp.project.librarymanagementapp.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewIssueHistory;
import emp.project.librarymanagementapp.Interfaces.IssueHistoryInterface;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.Presenter.IssueBookHistoryPresenter;
import emp.project.librarymanagementapp.R;

public class IssueHistoryActivityView extends AppCompatActivity implements IssueHistoryInterface.IssueHistoryViewInterface {

    private TextView txt_number_books;
    private RecyclerView recyclerView;
    private CircleImageView circleImageView;
    private IssueBookHistoryPresenter presenter;

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
            presenter.directIssueBookDisplay(LoginActivityView.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void InitViews() throws SQLException, ClassNotFoundException {
        presenter = new IssueBookHistoryPresenter(IssueHistoryActivityView.this);

        Toolbar toolbar = findViewById(R.id.Toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        circleImageView = findViewById(R.id.image_emptycart);
        txt_number_books = findViewById(R.id.txt_number_books);
        TextView text_late_books = findViewById(R.id.txt_number_latebooks);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        presenter.directCheckCart(LoginActivityView.getUsername());
        presenter.directNumberBooks();
        
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
        if (isEmpty) {
            circleImageView.setVisibility(View.VISIBLE);
        } else {
            circleImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void displayNumberOfBooks(int total_books) {
        txt_number_books.setText(String.valueOf(total_books));
        if (total_books > 5) {
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