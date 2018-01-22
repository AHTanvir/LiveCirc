package live.livecirc;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import static live.livecirc.R.id.Relative_layoutfor_fragments;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment implements LiveMatchAdapter.ScoreCallBack {
    private RecyclerView liverecy;
    private LiveMatchAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<LiveModel> list=new ArrayList<>();
    private Handler handler=new Handler();
    private Boolean matchStarted=true;
    private ProgressDialog progressDialog;
    private Thread thread;
    final private static String u = "http://cricapi.com/api/matches?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3";
    public LiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_live, container, false);
        liverecy=(RecyclerView)v.findViewById(R.id.LiveMatch);
        liverecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new LiveMatchAdapter(list,getActivity(),this);
        liverecy.setAdapter(adapter);
        if(((MainActivity)getActivity()).isNetworkConnected())
            getMatch();
        else Toast.makeText(getActivity(),"Not connected to internet",Toast.LENGTH_LONG).show();
        FloatingActionButton fab=(FloatingActionButton)v.findViewById(R.id.score_refrsh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).isNetworkConnected())
                    getMatch();
                else Toast.makeText(getActivity(),"Not connected to internet",Toast.LENGTH_LONG).show();
            }
        });
        return  v;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(thread !=null){
            thread.interrupt();
        }
    }

    void getMatch(){
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
                    System.out.println("objrct "+sb);;
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("matches");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject currItem = jsonArray.getJSONObject(i);

                        if (matchStarted == currItem.getBoolean("squad")) {

                            list.add( new LiveModel(
                                    currItem.getString("unique_id"),
                                    currItem.getString("team-1"),
                                    currItem.getString("team-2"),
                                    null,
                                   // currItem.getString("winner_team"),
                                    currItem.getString("type"),
                                    currItem.getBoolean("matchStarted")));

                        } else {

                            //do nothing

                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                adapter.updateAdapter(list);
                            }
                        });
                    }
                }catch (MalformedURLException e){} catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onItemClick(String unique_key, boolean getMatchStarted) {
        ScoreFragment s=new ScoreFragment().newInstance(unique_key,matchStarted);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(Relative_layoutfor_fragments, s,
                s.getTag()).addToBackStack(null).commit();
    }
}
