package live.livecirc;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class LiveMatchAdapter extends RecyclerView.Adapter<LiveMatchAdapter.LiveMatchViewHolder> {

    ArrayList<LiveModel> liveMatches;
    Context context;
    ScoreCallBack listenter;
    String s;

    public LiveMatchAdapter(ArrayList<LiveModel> liveMatches, Context context,ScoreCallBack listenter) {

        this.liveMatches = liveMatches;
        this.context = context;
        this.listenter=listenter;
    }

    @Override
    public LiveMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = li.inflate(R.layout.item_live_match, parent, false);

        return new LiveMatchViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final LiveMatchViewHolder holder, final int position) {
        final LiveModel currItem = liveMatches.get(position);
        holder.tvTeam1.setText(currItem.getTeam_1());
        holder.tvTeam2.setText(currItem.getTeam_2());
        holder.type.setText(currItem.getType());
        final Handler h=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                s=null;
               s= getScore(currItem.getUnique_key(),currItem.isMatchStarted());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                            if(s!=null && s!=" ") {
                            holder.result.setVisibility(View.VISIBLE);
                            holder.result.setText(s);
                        }else holder.result.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  listenter.onItemClick(currItem.getUnique_key(),currItem.isMatchStarted());
            }
        });
    }

    @Override
    public int getItemCount() {

        return liveMatches.size();
    }


    class LiveMatchViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeam1, tvTeam2;
        TextView type,result;
        View itemView;

        public LiveMatchViewHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            tvTeam1 = (TextView) itemView.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView) itemView.findViewById(R.id.tvTeam2);
            result= (TextView) itemView.findViewById(R.id.match_res);
            type= (TextView) itemView.findViewById(R.id.match_type);

        }
    }
    public void updateAdapter(ArrayList<LiveModel> list){
        this.liveMatches=list;
        notifyDataSetChanged();
    }
    public interface ScoreCallBack{
        void onItemClick(String unique_key,boolean getMatchStarted);
    }
    String getScore(String id,boolean b){
        String score = "";
        try {
            StringBuilder s=new StringBuilder();
            s.append("http://cricapi.com/api/cricketScore?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3&unique_id=");
            s.append(id);
            URL url=new URL(s.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String buffer = "";

            while (buffer != null){

                sb.append(buffer);
                buffer = br.readLine();

            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            if(b) {
                try {
                    score = jsonObject.getString("score");
                }catch (Exception e){

                    score = "null";
                }
            }

        } catch (IOException | JSONException e) {score="null";}
        return  score;
    }
}
