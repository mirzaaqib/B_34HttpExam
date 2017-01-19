package com.pallefire.b_34httpexam;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    Button start;
    TextView t;



    public class Mytask extends AsyncTask<String,Void,String> {


        URL url;
        HttpURLConnection connection;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        String line;
        StringBuilder result;


        @Override
        protected String doInBackground(String... p1) {

            try {
                url=new URL(p1[0]);
                connection= (HttpURLConnection) url.openConnection();//conncetion to establish

                inputStream=connection.getInputStream();//open channel for reading purpose

                inputStreamReader=new InputStreamReader(inputStream);

                bufferedReader=new BufferedReader(inputStreamReader);

                result=new StringBuilder();//Add

                line=bufferedReader.readLine();//read first line

                while(line!=null){

                    result.append(line);    //pileup in string builder
                    line=bufferedReader.readLine();//read next line



                }

                //now all lines of buffer reader are in stringbuilder result
                //now result contains finally the html code coming from server

                return result.toString();




            } catch (MalformedURLException e) {

                Log.d("B34","Inproper url");

                Log.d("B34","exception.."+e.getMessage()+e.getCause());

                // Toast.makeText(getActivity(), "improper url", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {

                Log.d("B34","check internet");

                // Toast.makeText(getActivity(), "check internet", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            finally {  // finally block close connection
                connection.disconnect();
                try {
                    if(connection!=null){

                        inputStream.close();
                        if(inputStreamReader!=null){
                            inputStreamReader.close();

                            if(bufferedReader!=null){


                                bufferedReader.close();


                            }}}} catch (IOException e) {


                    e.printStackTrace();
                }
            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null)

                t.setText(s);
            else
                t.setText("something went wrong");

            super.onPostExecute(s);
        }
    }



    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_fragment_one,container,false);

        start= (Button) v.findViewById(R.id.click);
        t= (TextView) v.findViewById(R.id.text);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //chekc internet connection
                //before connection asynctask class
                ConnectivityManager c= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);


                if(c!=null){

                    NetworkInfo n=c.getActiveNetworkInfo();
                    if(n!=null){


                        if(n.isConnected()==false) {
                            Toast.makeText(getActivity(), "Ple enablr internet", Toast.LENGTH_SHORT).show();
                            return;


                        }

                        Mytask m=new Mytask();

                        m.execute("http://skillgun.com");

                    }}}
        });

        // Inflate the layout for this fragment
        return v;

    }

}
