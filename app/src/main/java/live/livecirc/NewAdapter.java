package live.livecirc;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NewAdapter  extends RecyclerView.Adapter<NewAdapter.NewsViewHolder> {

    ArrayList<NewsModel> nelist;
    Context context;
    NewAdapter.ScoreCallBack listenter;
    String s;

    public NewAdapter(ArrayList<NewsModel> nelist, Context context,NewAdapter.ScoreCallBack listenter) {

        this.nelist = nelist;
        this.context = context;
        this.listenter=listenter;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = li.inflate(R.layout.item_news, parent, false);

        return new NewsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        final NewsModel currItem = nelist.get(position);
        Picasso.with(context).load(currItem.getImgUrl()).into(holder.img);
        holder.titel.setText(currItem.getTitel());
        holder.desc.setText(currItem.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listenter.onItemClick(currItem.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {

        return nelist.size();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView titel, desc;
        ImageView img;
        View itemView;

        public NewsViewHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            titel = (TextView) itemView.findViewById(R.id.titel);
            desc = (TextView) itemView.findViewById(R.id.descrip);
            img= (ImageView) itemView.findViewById(R.id.news_img);

        }
    }
    public void updateAdapter(ArrayList<NewsModel> list){
        this.nelist=list;
        notifyDataSetChanged();
    }
    public interface ScoreCallBack{
        void onItemClick(String url);
    }

}
