package com.example.a10017184.passvault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePassword extends AppCompatActivity {

    EditText first,second;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        first = (EditText) findViewById(R.id.editText_new);
        second = (EditText) findViewById(R.id.editText_new2);
        button = (Button) findViewById(R.id.button_confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1 = first.getText().toString();
                String p2 = second.getText().toString();

                if(p1.equals("") || p2.equals("")){
                    Toast.makeText(CreatePassword.this, "Please Enter a Password!", Toast.LENGTH_SHORT).show();
                }else{
                    if(p1.equals(p2)){
                        SharedPreferences settings = getSharedPreferences("PREPS",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password",p1);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CreatePassword.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
