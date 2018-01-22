package live.livecirc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anwar on 1/21/2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    ArrayList<ScheduleModel> sclist;
    Context context;
    LiveMatchAdapter.ScoreCallBack listenter;

    public ScheduleAdapter(ArrayList<ScheduleModel> sc, Context context) {
        this.sclist = sc;
        this.context = context;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = li.inflate(R.layout.item_sche, parent, false);

        return new ScheduleViewHolder(itemView);

    }

    public void onBindViewHolder(ScheduleViewHolder holder, final int position) {

        final ScheduleModel currItem = sclist.get(position);

        holder.tvTeam1.setText(currItem.getTeam_1());
        holder.tvTeam2.setText(currItem.getTeam_2());
        holder.date.setText(currItem.getDate());
    }

    @Override
    public int getItemCount() {

        return sclist.size();
    }


    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeam1, tvTeam2,date;
        View itemView;

        public ScheduleViewHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            tvTeam1 = (TextView) itemView.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView) itemView.findViewById(R.id.tvTeam2);
            date= (TextView) itemView.findViewById(R.id.match_date);

        }
    }
    public void updateAdapter(ArrayList<ScheduleModel> list){
        this.sclist=list;
        notifyDataSetChanged();
    }
    public interface ScoreCallBack{
        void onItemClick(String unique_key,boolean getMatchStarted);
    }
}
