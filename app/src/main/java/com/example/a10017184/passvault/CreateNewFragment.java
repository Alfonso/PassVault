package com.example.a10017184.passvault;


import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewFragment extends Fragment {

    EditText editText;
    TextView passwordView,length;
    String password,site;
    Button generate;
    ImageButton enter;
    SeekBar seekBar;
    int passLength;
    ArrayList<SiteAndPass> testList = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    String fileName = "data.json";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public CreateNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_new, container, false);
        passLength = 0;
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setMax(16);
        length = (TextView) view.findViewById(R.id.textView_length);
        generate = (Button) view.findViewById(R.id.button_generate);
        passwordView = (TextView) view.findViewById(R.id.textView_generate_pass);
        password = "";
        site = "";
        enter = (ImageButton) view.findViewById(R.id.imageButton_accept);
        editText = (EditText) view.findViewById(R.id.editText);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                length.setText("Length: "+i);
                passLength = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                site = editable.toString();
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "";
                String[] chars = {"a", "b", "c", "d", "e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w", "x", "y", "z", "0", "1", "2","3","4","5","6","7", "8", "9", "10","A","B","C","D","E","F","G","H","I","J","K","L","M","N","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
                for(int x = 0;x<passLength;x++)
                    password+=chars[(int) (Math.random() * 62)];

                passwordView.setText("Password: "+password);
            }
        });


        //reading
        try {
            FileInputStream in = getContext().openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            jsonArray = new JSONArray(reader.readLine());

            for(int x=0;x<jsonArray.length();x++){
                if(SiteAndPass.getPass(jsonArray.getJSONObject(x))!="")
                    testList.add( SiteAndPass.getSiteAndPass(jsonArray.getJSONObject(x)));
                if(testList.get(x).getPassword()=="")
                    testList.remove(x);
            }

            reader.close();
            inputStreamReader.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(site!="" && password!=""){
                    SiteAndPass newTemp = new SiteAndPass(site,password);
                    testList.add(newTemp);

                    jsonArray = new JSONArray();
                    for (int i=0; i < testList.size(); i++) {
                        jsonArray.put(testList.get(i).getJSONObject());
                    }
                    Log.d("tag",jsonArray.toString());
                    //writing
                    try{
                        OutputStreamWriter writer = new OutputStreamWriter(getContext().openFileOutput(fileName,getContext().MODE_PRIVATE));
                        writer.write(jsonArray.toString());
                        writer.close();
                    }catch(IOException e){}
                    fragmentManager =  getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    ListViewFragment listViewFragment = new ListViewFragment();
                    fragmentTransaction.replace(R.id.layout_bot,listViewFragment);
                    fragmentTransaction.commitNow();

                }else {
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
