package com.example.rssnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ListView lvNew;
    ArrayList<String> arrayTitle;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        lvNew = (ListView) findViewById(R.id.listView);

        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, arrayTitle);

        lvNew.setAdapter(adapter);
        String URL = "https://vnexpress.net/rss/tin-moi-nhat.rss";
        new ReadRSS().execute(URL);

        lvNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //Toast.makeText(MainActivity.this, arrayLink.get(i), Toast.LENGTH_SHORT).show();

                Intent toIntent = new Intent(MainActivity.this, NewsDetailActivity.class);
                toIntent.putExtra("duongDan",arrayLink.get(i));//
                startActivity(toIntent);

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class ReadRSS extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            return readContentFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            //dat noi dung vao bien document
            Document documemt= parser.getDocument(s);

            NodeList nodeList = documemt.getElementsByTagName("item");

            String title ="";


            for(int i = 0; i< nodeList.getLength(); i++)
            {
                Element element = (Element) nodeList.item(i);
                title =  parser.getValue(element, "title") ;
                arrayTitle.add(title);
                arrayLink.add(parser.getValue(element, "link"));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private static String readContentFromURL(String theUrl)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

}
