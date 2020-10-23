package emp.project.librarymanagementapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import emp.project.librarymanagementapp.Models.BookModel;
import emp.project.librarymanagementapp.R;

public class RecyclerViewBookCart extends RecyclerView.Adapter<RecyclerViewBookCart.MyViewHolder> {
    Context context;
    List<BookModel> list;

    public RecyclerViewBookCart(Context context, List<BookModel> list) {
        this.context = context;
        this.list = list;
    }


    public BookModel getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_bookslist_cart, parent, false);
        return new RecyclerViewBookCart.MyViewHolder(view);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookModel model = getItem(position);
        holder.txt_description.setText(model.getBook_description());
        holder.txt_title.setText(model.getBook_title());
        holder.txt_category.setText(model.getBook_category());
        holder.txt_quantity.setText(model.getBook_quantity());
        Glide.with(context).load(model.getBook_url()).into(holder.imageView_book);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookModel.getList_cartBooks().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_quantity, txt_description, txt_category;
        ImageView imageView_book;
        Button btn_cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_book_title);
            txt_quantity = itemView.findViewById(R.id.txt_book_quantity);
            txt_description = itemView.findViewById(R.id.txt_book_description);
            txt_category = itemView.findViewById(R.id.txt_book_category);
            imageView_book = itemView.findViewById(R.id.book_img);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
        }
    }
}
