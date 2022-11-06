package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private int index;
    EditText url_txt;
    Button btn_add, btn_prev, btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        url_txt = findViewById(R.id.add_url);

        btn_add = findViewById(R.id.add_btn);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);


        btn_add.setOnClickListener(view -> {
            String url = url_txt.getText().toString();
            if(url.length() > 0 && url.contains("http")) {
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                ArrayList<LogbookEntity> logbooks = databaseHelper.getImages();
                databaseHelper.addNewImage(url);
                url_txt.setText("");
                index = logbooks.size()-1;
                setImage(logbooks.size()-1);
            } else {
                url_txt.requestFocus();
                url_txt.setError("please enter correct url!");
            }
        });

        showFirstImage();

        btn_prev.setOnClickListener(view -> {
            handleControlImage(index - 1);
        });

        btn_next.setOnClickListener(view -> {
            handleControlImage(index + 1);
        });
    }

    private void handleControlImage(int i) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<LogbookEntity> logbooks = databaseHelper.getImages();

        if(i < 0) {
            index = logbooks.size()-1;
            setImage(logbooks.size()-1);
        } else if (i > logbooks.size()-1) {
            index = 0;
            setImage(0);
        } else {
            index = i;
            setImage(i);
        }
    }

    private void setImage(int index) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<LogbookEntity> logbooks = databaseHelper.getImages();
        for (LogbookEntity logbook : logbooks) {
            if(logbook.id == index + 1) {
                Picasso.get().load(logbook.url).into(imageView);
            }
        }
    }

    private void showFirstImage() {
        setImage(0);
    }


}