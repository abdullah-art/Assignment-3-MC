package com.example.assignment3.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.activities.MainActivity;
import com.example.assignment3.models.Book;
import com.example.assignment3.R;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;




public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> bookList;
    final int permissionCode=33333;


    public RecyclerViewAdapter(Context mContext, List<Book> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        view=inflater.inflate(R.layout.book_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.level.setText(bookList.get(position).getLevel());
        holder.info.setText(bookList.get(position).getInfo());
        String book_name=bookList.get(position).getCover();
        String extension=bookList.get(position).getUrl();
        final String ext=extension;
        extension=extension.substring(extension.length()-4);
        if(extension.equals(".pdf"))
        {

            holder.button.setText("Download");
            holder.button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String url=bookList.get(position).getUrl().trim();
                    String title=bookList.get(position).getTitle().trim();
                    MainActivity.getInstance().startDownloading(url,title);
                }
            });
        }
        else{

            holder.button.setText("Read Online");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent("android.intent.action.VIEW", Uri.parse(bookList.get(position).getUrl()));
                    mContext.startActivity(intent);
                }
            });
        }
        Boolean find=true;
        Drawable drawable=null;
        book_name=book_name.substring(4).trim();
        book_name=book_name.substring(0,book_name.length()-4).trim();
        book_name=book_name.toLowerCase();
        book_name=book_name.replace('-','_');
        int resid=mContext.getResources().getIdentifier(book_name,"drawable",mContext.getPackageName());
        try {
            drawable=mContext.getDrawable(resid);
        }
        catch (Exception e)
        {
            find=false;
        }
        if(find)
        {
            holder.bookImage.setImageDrawable(drawable);
        }
    }



    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView bookImage;
        TextView title;
        TextView level;
        TextView info;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage=itemView.findViewById(R.id.thumbnail);
            title=itemView.findViewById(R.id.title);
            level=itemView.findViewById(R.id.level);
            info=itemView.findViewById(R.id.info);
            button=itemView.findViewById(R.id.btn);
        }
    }


}