//package com.example.brenda.assuistant;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


public class CallDB {

    static String url_address = "http://54.88.195.178:3000";

    public static void main(String[] args) {
        System.out.print(sendRecording());
    }

    public static String getTable(String table){
        String table_data = "";
        try {
            URL url = new URL( url_address + table);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            table_data = br.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return table_data;
    }
    public static String sendRecording(){
        String recording_data = "";
        try {
            URL url = new URL( url_address + "/database/POST/recording");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Charset","UTF-8");
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.connect();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("");
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
                recording_data = response.toString();
            } else {
                System.out.println("POST request not worked");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return recording_data;
    }
}
