package com.group4.smartaccess;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AccessRoom extends AsyncTask<String, Void, String> implements NfcAdapter.CreateNdefMessageCallback {

    String server_response;
    String request;
    String transactionID;
    String msg;
    public SendNDEF delegate = null;

    public AccessRoom(SendNDEF delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection conn = null;

        try{
            url = new URL(strings[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // HERE ARE THE PARAMETERS FOR THE JSON OBJECT //
            JSONObject params = new JSONObject();
            params.put("$class", "org.example.basic.checkIn");
            params.put("guest", "resource:org.example.basic.Guest#9208");
            params.put("room", "resource:org.example.basic.Room#2593");

            Log.i("JSON", params.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(params.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            request = conn.getRequestMethod();
            int code = conn.getResponseCode();

            if(code == HttpURLConnection.HTTP_OK){
                server_response = readStream(conn.getInputStream());
                Log.v("CatalogClient", server_response); // Log read in Logcat
            }
            else{
                server_response = "Failed: code was " + code + ". Not \"OK\"";
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }

        return server_response;
    }

    // Return the information here
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        transactionID = server_response.substring(150, 214);
        msg = transactionID;
        Log.d("SERVER:", "" + server_response);
        Log.e("Response", "" + server_response); // Log read in Logcat
        NdefRecord record1 = NdefRecord.createMime("application/vnd.com.royce.nfcapp_04", msg.getBytes()); // mimetype may have to be changed
        NdefMessage ndef = new NdefMessage(record1);
        delegate.sendMessage(ndef);
        //setServer_response(s);

    }

    // Convert inputstream to String
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
