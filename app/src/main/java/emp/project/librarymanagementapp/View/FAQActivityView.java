package emp.project.librarymanagementapp.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.CustomAdapter.RecyclerViewFAQ;
import emp.project.librarymanagementapp.Interfaces.IFAqs;
import emp.project.librarymanagementapp.Models.FAQModel;
import emp.project.librarymanagementapp.Presenter.FAQPresenter;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.Repository.FaqRepository;

public class FAQActivityView extends AppCompatActivity implements IFAqs.FAQView {

    private RecyclerView recyclerView;
    private FAQPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_f_a_q_view);

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
        presenter = new FAQPresenter(FAQActivityView.this, FaqRepository.getInstance());
        Toolbar toolbar = findViewById(R.id.Toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FAQActivityView.this);
                LayoutInflater inflater = FAQActivityView.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_add_faq, null);
                dialogBuilder.setView(dialogView);

                EditText txt_question = dialogView.findViewById(R.id.txt_question);
                Button btn_sumbit = dialogView.findViewById(R.id.btn_sumbit);

                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                btn_sumbit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (txt_question.getText().length() == 0) {
                                presenter.directToErrorMessage(txt_question);
                            } else {
                                presenter.directToNewQuestion(txt_question.getText().toString());
                                dialog.cancel();
                                //refresh-----------
                                Intent intent = new Intent(FAQActivityView.this, FAQActivityView.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                                //refresh-----------
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        presenter.directToDisplayFAQ();
    }

    @Override
    public void displayFaqs(List<FAQModel> list) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewFAQ adapter = new RecyclerViewFAQ(
                FAQActivityView.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayErrorMessage(String message, EditText textErrorField) {
        Toast.makeText(FAQActivityView.this, message, Toast.LENGTH_SHORT).show();
        textErrorField.setBackgroundResource(R.drawable.rounnded_editext_error);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}