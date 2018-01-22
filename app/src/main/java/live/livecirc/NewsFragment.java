package live.livecirc;


import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewAdapter.ScoreCallBack {
    private NewAdapter adapter;
    private Thread thread;
    private RecyclerView recyclerView;
    private ArrayList<NewsModel> list=new ArrayList<>();
    private Handler handler=new Handler();
    private ProgressDialog progressDialog;
    private String u="https://newsapi.org/v2/top-headlines?sources=espn-cric-info&apiKey=7a3cf6383cd54365809d3b2932833b94";
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.news_r);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new NewAdapter(list,getActivity(),this);
        recyclerView.setAdapter(adapter);
        getNews();
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        if(thread !=null){
            thread.interrupt();
        }
    }
    private void getNews() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.show();
        thread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url =new URL(u);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    BufferedReader br = new BufferedReader(isr);
                    String buffer = "";
                    StringBuilder sb = new StringBuilder();

                    while (buffer != null) {

                        sb.append(buffer);
                        buffer = br.readLine();

                    }
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    JSONArray jsonArray =jsonObject.getJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject currItem = jsonArray.getJSONObject(i);
                            list.add( new NewsModel(
                                    currItem.getString("title"),
                                    currItem.getString("description"),
                                    currItem.getString("url"),currItem.getString("urlToImage")));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                adapter.updateAdapter(list);
                            }
                        });
                    }
                }catch (MalformedURLException e){} catch (IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            adapter.updateAdapter(list);
                        }
                    });
                    e.printStackTrace();
                } catch (JSONException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            adapter.updateAdapter(list);
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onItemClick(String url) {
        try {
            Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }catch (ActivityNotFoundException e){}
    }
}
