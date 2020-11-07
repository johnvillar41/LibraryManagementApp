package emp.project.librarymanagementapp.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.librarymanagementapp.Presenter.IssueBookHistoryPresenter;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.View.IssueHistoryActivityView;

public class RecyclerViewIssueHistory extends RecyclerView.Adapter<RecyclerViewIssueHistory.MyViewHolder> {

    Context context;
    List<IssueBookModel>list;
    IssueBookHistoryPresenter controller;

    public RecyclerViewIssueHistory(Context context, List<IssueBookModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_issue_history, parent, false);
        return new RecyclerViewIssueHistory.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IssueBookModel model=getItem(position);
        Glide.with(context).load(model.getBook_image_url()).into(holder.circleImageView);
        holder.txt_book_title.setText(model.getBook_title());
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
                dialogBuilder
                        .setIcon(R.drawable.booklogo3)
                        .setTitle("Return "+model.getBook_title()+"?")
                        .setMessage("Are you sure you want to bring back the books you rented?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //DB Operation
                                controller=new IssueBookHistoryPresenter((IssueHistoryActivityView) context);
                                try {
                                    controller.directReturnBook(model);
                                    //refresh-----------
                                    Intent intent=new Intent(context,context.getClass());
                                    context.startActivity(intent);
                                    ((IssueHistoryActivityView) context).finish();
                                    ((IssueHistoryActivityView) context).overridePendingTransition(0,0);
                                    //--------------
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No",null);
                AlertDialog dialog=dialogBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public IssueBookModel getItem(int position){
        return list.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView txt_book_title;
        FloatingActionButton floatingActionButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.book_img);
            txt_book_title=itemView.findViewById(R.id.txt_book_title);
            floatingActionButton=itemView.findViewById(R.id.floating_action_button);
        }
    }
}
