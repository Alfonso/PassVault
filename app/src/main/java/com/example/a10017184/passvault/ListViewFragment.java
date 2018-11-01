package com.example.a10017184.passvault;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    ListView lView;
    ArrayList<SiteAndPass> testList = new ArrayList<>();
    final String fileName="data.json";
    JSONArray jsonArray = new JSONArray();


    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //reading
        try {
            FileInputStream in = getContext().openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            jsonArray = new JSONArray(reader.readLine());
            for(int x=0;x<jsonArray.length();x++){

                testList.add( SiteAndPass.getSiteAndPass(jsonArray.getJSONObject(x)));
            }
            reader.close();
            inputStreamReader.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        }



        View view = inflater.inflate(R.layout.fragment_list_view,container,false);
        lView = (ListView) view.findViewById(R.id.listView);

        CustomAdapter myAdapter = new CustomAdapter(getActivity().getBaseContext(),R.layout.listview,testList);
        lView.setAdapter(myAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return view;
    }

    public class CustomAdapter extends ArrayAdapter<SiteAndPass> {
        List<SiteAndPass> nList;
        Context mainContext;
        ImageButton copy,delete;
        JSONArray jsonArray2;

        public CustomAdapter(Context context, int resource, List<SiteAndPass> pObjects){
            super(context,resource,pObjects);

            nList = pObjects;
            mainContext=context;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mainContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View layoutView = inflater.inflate(R.layout.listview,null);
            TextView passTextView = (TextView) layoutView.findViewById(R.id.textView_password);
            TextView siteTextView = (TextView) layoutView.findViewById(R.id.textView_site);
            passTextView.setText(nList.get(position).getPassword());
            siteTextView.setText(nList.get(position).getSite());
            copy  = (ImageButton) layoutView.findViewById(R.id.imageButton_copy);
            delete = (ImageButton) layoutView.findViewById(R.id.imageButton_delete_one);


            final int pos = position;


            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("password",nList.get(pos).getPassword());
                    clipboard.setPrimaryClip(clip);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag","Clicked");
                    testList.remove(pos);
                    jsonArray2 = new JSONArray();
                    for (int i=0; i < testList.size(); i++) {
                        jsonArray2.put(testList.get(i).getJSONObject());
                    }

                    //writing
                    try{
                        OutputStreamWriter writer = new OutputStreamWriter(getContext().openFileOutput(fileName,getContext().MODE_PRIVATE));
                        writer.write(jsonArray2.toString());
                        writer.close();
                        Log.d("tag","write");
                    }catch(IOException e){}
                    notifyDataSetChanged();

                }
            });

            return layoutView;
        }
    }



}
