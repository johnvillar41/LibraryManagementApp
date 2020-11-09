package emp.project.librarymanagementapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.Presenter.NotificationPresenter;
import emp.project.librarymanagementapp.R;
import emp.project.librarymanagementapp.View.NotificationActivityView;

public class RecyclerViewNotification extends RecyclerView.Adapter<RecyclerViewNotification.MyViewHolder>{

    Context context;
    NotificationPresenter presenter;
    List<NotificationModel>list;

    public RecyclerViewNotification(Context context, List<NotificationModel> list) {
        this.context = context;
        this.presenter = new NotificationPresenter((NotificationActivityView) context);
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_notification, parent, false);
        return new RecyclerViewNotification.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel model=getItem(position);
        holder.textView_title.setText(model.getNotif_title());
        holder.textView_message.setText(model.getNotif_msg());
        if(model.getNotif_title().equals("Books")){
            holder.circleImageView.setImageResource(R.drawable.booklogo1);
        }else if(model.getNotif_title().equals("Notification")){
            holder.circleImageView.setImageResource(R.drawable.notiflogo);
        }else{
            holder.circleImageView.setImageResource(R.drawable.itemlogo);
        }
        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.deleteNotification(model.getNotif_id());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public NotificationModel getItem(int position){
        return list.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textView_title,textView_message;
        ImageView imageView_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.image_recycler_notif);
            textView_title=itemView.findViewById(R.id.notif_title);
            imageView_delete=itemView.findViewById(R.id.delete);
            textView_message=itemView.findViewById(R.id.txt_message);
        }
    }
}
