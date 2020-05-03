package com.example.assignment3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment3.R;
import com.example.assignment3.adapters.RecyclerViewAdapter;
import com.example.assignment3.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int permissionCode=33344;
    private List<Book> bookList;
    private RecyclerView recyclerView;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;

        recyclerView=findViewById(R.id.recyclerView);
        bookList=new ArrayList<>();

        fetchData();
        setUpRecyclerView(bookList);
    }


    public static MainActivity getInstance() {
        return instance;
    }

    public void startDownloading(String url,String title)
    {
        try {
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setTitle("Download");
            request.setDescription("Downloading file...");
            request.setAllowedOverMetered(true);
            request.setAllowedOverRoaming(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
            DownloadManager manager=(DownloadManager)getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
        catch (Exception e)
        {
            Log.i("error", "onClick: "+e);
        }
    }


    private void fetchData() {
        try{
            InputStream is=getResources().openRawResource(R.raw.books);
            byte[] buffer=new byte[is.available()];
            while (is.read(buffer) != -1);
            is.close();
            String jsonText = new String(buffer);
            JSONObject jsonObject = new JSONObject(jsonText);
            JSONArray books=jsonObject.getJSONArray("books");

            for (int i=0;i<books.length();i++)
            {
                JSONObject obj=books.getJSONObject(i);
                Book book=new Book(obj.getString("title"),obj.getString("author"),obj.getString("authorUrl"),obj.getString("level"),obj.getString("info"),obj.getString("url"),obj.getString("cover"));
                bookList.add(book);
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (JSONException e) {
            Log.e("error", "onCreate: "+e );
        }
    }

    private void setUpRecyclerView(List<Book> books) {
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
            {
                String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,permissionCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case permissionCode:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                else{
                    Toast.makeText(instance, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
