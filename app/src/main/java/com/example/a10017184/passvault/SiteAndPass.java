package com.example.a10017184.passvault;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 10017184 on 5/9/2017.
 */

public class SiteAndPass implements Serializable {

    String password,site;

    public SiteAndPass(String s,String p){
        site = s;
        password = p;
    }

    public String getPassword(){
        return password;
    }

    public String getSite(){
        return site;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("pass", password);
            obj.put("site", site);
        } catch (JSONException e) {
        }
        return obj;
    }

    public static SiteAndPass getSiteAndPass(JSONObject obj){
        try {
            return new SiteAndPass( (String)obj.get((String)obj.names().get(1)) , (String) obj.get((String)obj.names().get(0)) );
        }catch(JSONException e) {
            return new SiteAndPass("Site", "Password");
        }
    }

    public static String getSite(JSONObject obj){
        try {
            return  (String)obj.get((String)obj.names().get(1));
        }catch(JSONException e) {
            return "";
        }
    }

    public static String getPass(JSONObject obj){
        try {
            return  (String)obj.get((String)obj.names().get(0));
        }catch(JSONException e) {
            return "";
        }
    }

}
