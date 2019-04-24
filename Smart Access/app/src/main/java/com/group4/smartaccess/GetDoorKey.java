package com.group4.smartaccess;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDoorKey extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;
    String server_response;

    public GetDoorKey(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                server_response = buffer.toString();

            }

            return server_response;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return server_response;
    }

    // Return the information here
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.d("SERVER:", "" + server_response);
        Log.e("Response", "" + server_response); // Log read in Logcat
        //setServer_response(s);
        delegate.processFinish(server_response); // this allows the result to be used in the Activity
    }
}
