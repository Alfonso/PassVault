package com.example.a10017184.passvault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Credits extends AppCompatActivity {

    ImageButton back,delete;
    String fileName = "data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits2);


        back = (ImageButton) findViewById(R.id.imageButton_credits_back);
        delete = (ImageButton) findViewById(R.id.imageButton_delete_vault);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent output = new Intent();
                setResult(RESULT_OK, output);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //writing
                try{

                    JSONArray jsonArray = new JSONArray();
                    //jsonArray.put(new SiteAndPass("","").getJSONObject());
                    OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(fileName,getBaseContext().MODE_PRIVATE));
                    writer.write(jsonArray.toString());
                    writer.close();
                }catch(IOException e){}
            }
        });

    }
}
