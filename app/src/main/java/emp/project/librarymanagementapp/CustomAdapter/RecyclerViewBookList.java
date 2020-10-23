package emp.project.librarymanagementapp.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import emp.project.librarymanagementapp.Models.BookModel;
import emp.project.librarymanagementapp.R;


public class RecyclerViewBookList extends RecyclerView.Adapter<RecyclerViewBookList.MyViewHolder> {

    Context context;
    List<BookModel> list;

    public RecyclerViewBookList(Context context, List<BookModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_bookslist, parent, false);
        return new MyViewHolder(view);
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
        holder.btn_rent_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(model.getBook_quantity()) <= 0){
                    Toast.makeText(context,"No books left!",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to rent this book?")
                            .setIcon(R.drawable.booklogo1)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    model.addBooks(model);
                                    Toast.makeText(context, BookModel.getList_cartBooks().toString(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle(holder.txt_title.getText());
                    alertDialog.show();
                }
            }
        });
    }

    public BookModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_quantity, txt_description, txt_category;
        ImageView imageView_book;
        Button btn_rent_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_book_title);
            txt_quantity = itemView.findViewById(R.id.txt_book_quantity);
            txt_description = itemView.findViewById(R.id.txt_book_description);
            txt_category = itemView.findViewById(R.id.txt_book_category);
            imageView_book = itemView.findViewById(R.id.book_img);
            btn_rent_book = itemView.findViewById(R.id.btn_rent_book);
        }
    }
}
