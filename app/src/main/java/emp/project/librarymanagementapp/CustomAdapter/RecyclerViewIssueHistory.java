package emp.project.librarymanagementapp.CustomAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.librarymanagementapp.Interfaces.IIssueHistory;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.Presenter.IssueBookHistoryPresenter;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.Repository.IssueBookHistoryRepository;
import emp.project.librarymanagementapp.View.IssueHistoryActivityView;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class RecyclerViewIssueHistory extends RecyclerView.Adapter<RecyclerViewIssueHistory.MyViewHolder> {
    Context context;
    List<IssueBookModel> list;
    IssueBookHistoryPresenter presenter;

    public RecyclerViewIssueHistory(Context context, List<IssueBookModel> list) {
        this.context = context;
        this.list = list;
        this.presenter = new IssueBookHistoryPresenter((IIssueHistory.IssueHistoryViewInterface) context, IssueBookHistoryRepository.getInstance());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_issue_history, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Calendar c1 = null;
        Calendar c2 = null;
        IssueBookModel model = getItem(position);
        Glide.with(context).load(model.getBook_image_url()).into(holder.circleImageView);
        holder.txt_book_title.setText(model.getBook_title());
        holder.txt_issue_date.setText(model.getDateIssue());
        holder.txt_deadline.setText(model.getDateDeadline());

        @SuppressLint("SimpleDateFormat") String date1 = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        String date2 = model.getDateDeadline();

        try {
            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
            c1.setTime(Objects.requireNonNull(sdf.parse(date1)));
            c2.setTime(Objects.requireNonNull(sdf.parse(date2)));
            if (c1.after(c2)) {
                holder.statusLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                holder.txt_book_title.setTextColor(Color.parseColor("#FF0000"));
                presenter.directNewNotification("Book due date needs to be returned", "Return " + model.getBook_title(), LoginActivityView.getUsername());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Calendar finalC = c1;
        Calendar finalC1 = c2;
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder
                        .setIcon(R.drawable.booklogo3)
                        .setTitle("Return " + model.getBook_title() + "?")
                        .setMessage("Are you sure you want to bring back the books you rented?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter = new IssueBookHistoryPresenter((IIssueHistory.IssueHistoryViewInterface) context,IssueBookHistoryRepository.getInstance());
                                try {
                                    presenter.directReturnBook(model);
                                    //refresh-----------
                                    Intent intent = new Intent(context, context.getClass());
                                    context.startActivity(intent);
                                    ((IssueHistoryActivityView) context).finish();
                                    ((IssueHistoryActivityView) context).overridePendingTransition(0, 0);
                                    //--------------
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", null);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public IssueBookModel getItem(int position) {
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_book_title;
        FloatingActionButton floatingActionButton;
        TextView txt_issue_date, txt_deadline;
        LinearLayout statusLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.book_img);
            txt_book_title = itemView.findViewById(R.id.txt_book_title);
            floatingActionButton = itemView.findViewById(R.id.floating_action_button);
            txt_issue_date = itemView.findViewById(R.id.txt_date_issue);
            txt_deadline = itemView.findViewById(R.id.txt_date_deadline);
            statusLayout = itemView.findViewById(R.id.status);
        }
    }
}
