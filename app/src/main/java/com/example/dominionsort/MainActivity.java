package com.example.dominionsort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dominionsort.ui.main.TopActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setViews();
    }
    private void setViews(){
        Button button = (Button)findViewById(R.id.start_button);
        button.setOnClickListener(onClick_button);
    }
    private View.OnClickListener onClick_button = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TopActivity.class);
            startActivity(intent);
        }
    };

}
