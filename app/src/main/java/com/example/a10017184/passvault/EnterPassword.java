package com.example.a10017184.passvault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPassword extends AppCompatActivity {

    EditText p1;
    Button button;

    String pWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        SharedPreferences settings = getSharedPreferences("PREPS",0);
        pWord = settings.getString("password","");

        p1 = (EditText) findViewById(R.id.editText_enter_password);
        button = (Button) findViewById(R.id.button_enter_password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enter = p1.getText().toString();

                if(enter.equals(pWord)){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(EnterPassword.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
