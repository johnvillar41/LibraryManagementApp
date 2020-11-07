package emp.project.librarymanagementapp.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Presenter.FAQPresenter;
import emp.project.librarymanagementapp.Models.FAQModel;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.View.FAQActivityView;

public class RecyclerViewFAQ extends RecyclerView.Adapter<RecyclerViewFAQ.MyViewHolder> {

    Context context;
    List<FAQModel>list;
    FAQPresenter presenter;

    public RecyclerViewFAQ(Context context, List<FAQModel> list) {
        this.context = context;
        this.list = list;
        this.presenter =new FAQPresenter((FAQActivityView) context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_faq, parent, false);
        return new RecyclerViewFAQ.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FAQModel model=getItem(position);
        String id=model.getFaq_id();
        holder.txt_question.setText(model.getFaq_question());
        holder.txt_reply.setText(model.getFaq_reply());
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.directToRemoveFAQ(id);
                    //refresh-------
                    Intent intent=new Intent(context,context.getClass());
                    context.startActivity(intent);
                    ((FAQActivityView) context).overridePendingTransition(0,0);
                    ((FAQActivityView) context).finish();
                    //refresh----------
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display ALert Dialog to see full reply
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
                LayoutInflater inflater = ((FAQActivityView) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_faq_question, null);
                dialogBuilder.setView(dialogView);

                TextView txt_answer=dialogView.findViewById(R.id.txt_answer_pop);
                Button btn_back=dialogView.findViewById(R.id.btn_back);

                AlertDialog dialog=dialogBuilder.create();
                dialog.show();

                txt_answer.setText(model.getFaq_reply());
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public FAQModel getItem(int position){
        return list.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn_remove;
        TextView txt_reply,txt_question;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_remove=itemView.findViewById(R.id.btn_remove);
            txt_reply=itemView.findViewById(R.id.txt_reply);
            txt_question=itemView.findViewById(R.id.txt_answer);
            cardView=itemView.findViewById(R.id.faqs);
        }
    }
}
