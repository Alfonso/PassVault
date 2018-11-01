package com.example.a10017184.passvault;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button credits,vault;

    static final int NUMBER_CODE = 12345;
    static final String CODE = "CODE";
    ArrayList<SiteAndPass> list;
    String fileName = "data.json";
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vault = (Button) findViewById(R.id.button_vault);
        credits = (Button) findViewById(R.id.button_credits);

        vault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToOpenApp = new Intent(MainActivity.this,Vault.class);
                startActivityForResult(intentToOpenApp,NUMBER_CODE);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToOpenApp= new Intent(MainActivity.this,Credits.class);
                startActivityForResult(intentToOpenApp,NUMBER_CODE);
            }
        });


        /*
        list = new ArrayList<>();
        list.add(new SiteAndPass("FaceBook","password"));
        list.add(new SiteAndPass("Instagram","password"));
        list.add(new SiteAndPass("Twitter","password"));
        list.add(new SiteAndPass("Reddit","password"));
        list.add(new SiteAndPass("Imgur","password"));
        list.add(new SiteAndPass("Unity","password"));
        list.add(new SiteAndPass("MySpace","password"));

        jsonArray = new JSONArray();
        for (int i=0; i < list.size(); i++) {
            jsonArray.put(list.get(i).getJSONObject());
        }

        //writing
        try{
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(fileName,Context.MODE_PRIVATE));
            writer.write(jsonArray.toString());
            writer.close();
        }catch(IOException e){

        }

        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NUMBER_CODE && resultCode == RESULT_OK){

        }
    }
    private boolean fileExists(Context _context, String _filename) {
        File temp = _context.getFileStreamPath(_filename);
        if(temp == null || !temp.exists()) {
            return false;
        }
        return true;
    }
}


