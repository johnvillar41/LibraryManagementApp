package emp.project.librarymanagementapp.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewBookCart;
import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewBookList;
import emp.project.librarymanagementapp.Interfaces.IBook;
import emp.project.librarymanagementapp.Models.BookModel;
import emp.project.librarymanagementapp.Presenter.BookPresenter;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.Repository.BookRepository;

@SuppressWarnings("Convert2Lambda")
public class BookActivityView extends AppCompatActivity implements IBook.BookActivityViewInterface {
    private RecyclerView recyclerView;
    private Spinner spinner;
    private EditText txt_search;
    private BookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_view);
        try {
            InitViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void InitViews() throws SQLException, ClassNotFoundException {
        presenter = new BookPresenter(BookRepository.getInstance(), BookActivityView.this);
        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner_category);
        Button btn_checkout = findViewById(R.id.btn_checkout);
        txt_search = findViewById(R.id.txt_search);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    presenter.directGetBookBasedOnSearch(txt_search.getText().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.btnCheckOutClicked();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.directRefreshPage();
            }
        });

        presenter.directRecyclerView();
        presenter.directBookCategory();
    }

    @Override
    public void displayBookCategory(List<String> books){
        ArrayAdapter adapter = new ArrayAdapter(BookActivityView.this, android.R.layout.simple_spinner_dropdown_item, books);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    presenter.directRecyclerViewBasedOnCategory(spinner.getSelectedItem().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void displayRecyclerView(List<BookModel> list) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewBookList adapter = new RecyclerViewBookList(
                BookActivityView.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayRecyclerViewBasedOnCategory(List<BookModel>books){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(BookActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewBookList adapter = new RecyclerViewBookList(
                BookActivityView.this, books);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookActivityView.this);

        LayoutInflater inflater = BookActivityView.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_cartlist, null);
        dialogBuilder.setView(dialogView);

        Button btn_checkout_dialog = dialogView.findViewById(R.id.btn_checkout);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerview_cart);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(BookActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewBookCart adapter = new RecyclerViewBookCart(
                BookActivityView.this, BookModel.getList_cartBooks());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        if (BookModel.getList_cartBooks().size() == 0) {
            ImageView imageView = dialogView.findViewById(R.id.image_emptycart);
            imageView.setVisibility(View.VISIBLE);
        }

        btn_checkout_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BookModel.getList_cartBooks().size() == 0) {
                    Toast.makeText(BookActivityView.this, "Empty Cart!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        presenter.directCartListToDB(BookModel.getList_cartBooks());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    BookModel.getList_cartBooks().clear();
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public void onFailedMessage(String message) {
        Toast.makeText(BookActivityView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRecyclerViewBasedOnSearch(List<BookModel> list) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(BookActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewBookList adapter = new RecyclerViewBookList(
                BookActivityView.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void refreshPage() {
        Intent intent=new Intent(BookActivityView.this,BookActivityView.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}