package live.livecirc;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView bowling,batting,name,fname,birth,currentAge,majorTeams,battingStyle,bowlingStyle,country;
    private ImageButton search;
    private EditText tv_search;
    private ImageView image;
    private Handler handler=new Handler();
    private Boolean matchStarted=true;
    private ProgressDialog progressDialog;
    private  ArrayAdapter<String>  adapter;
    private NestedScrollView nstedView;
    private ListView player;
    private ArrayList<PlayerModel> plist=new ArrayList<>();
    final private static String u = "http://cricapi.com/api/playerFinder?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3&name=";
    final private static String u1 = "http://cricapi.com/api/playerStats?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3&pid=";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_player, container, false);
        name=(TextView)v.findViewById(R.id.name);
        nstedView=(NestedScrollView)v.findViewById(R.id.nesteds);
        player=(ListView)v.findViewById(R.id.list_view);
        fname=(TextView)v.findViewById(R.id.fullName);
        batting=(TextView)v.findViewById(R.id.batting);
        battingStyle=(TextView)v.findViewById(R.id.battingStyle);
        bowling=(TextView)v.findViewById(R.id.bowling);
        bowlingStyle=(TextView)v.findViewById(R.id.bowlingStyle);
        currentAge=(TextView)v.findViewById(R.id.age);
        country=(TextView)v.findViewById(R.id.country);
        birth=(TextView)v.findViewById(R.id.birth);
        majorTeams=(TextView)v.findViewById(R.id.majorTeams);
        tv_search=(EditText) v.findViewById(R.id.tv_search);
        search=(ImageButton) v.findViewById(R.id.img_button);
        image=(ImageView) v.findViewById(R.id.img_v);
        if(((MainActivity)getActivity()).isNetworkConnected())
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPlayers();
                }
            });
        else Toast.makeText(getActivity(),"Not connected to internet",Toast.LENGTH_LONG).show();
        return  v;
    }
   void getPlayers(){
       progressDialog=new ProgressDialog(getActivity());
       progressDialog.setCancelable(false);
       progressDialog.show();
       plist.clear();
       final ArrayList<String> al=new ArrayList<>();
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   String res1=intt(u+tv_search.getText());
                   JSONObject jsonObject = new JSONObject(res1);
                   JSONArray jsonArray = jsonObject.getJSONArray("data");

                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject currItem = jsonArray.getJSONObject(i);
                       plist.add(new PlayerModel(currItem.getString("fullName"),currItem.getString("pid")));
                       al.add(currItem.getString("fullName"));
                   }
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           progressDialog.dismiss();
                         if(plist.size()!=0) {
                             adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,al);
                             player.setVisibility(View.VISIBLE);
                             nstedView.setVisibility(View.GONE);
                             player.setAdapter(adapter);
                             player.setOnItemClickListener(PlayerFragment.this);
                         }else Toast.makeText(getActivity(),"Not found",Toast.LENGTH_SHORT).show();
                       }
                   });
               } catch (JSONException e) {
                   System.out.println("exception "+e);
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           progressDialog.dismiss();
                       }
                   });
                   e.printStackTrace();
               }
           }
       }).start();
    }
    private void getProfile(final String pid) {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res2=intt(u1+pid);
                   final JSONObject job= new JSONObject(res2);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                player.setVisibility(View.GONE);
                                nstedView.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                Picasso.with(getActivity()).load(job.getString("imageURL")).into(image);
                                name.setText(job.getString("name"));
                                fname.setText(job.getString("fullName"));
                                currentAge.setText(job.getString("currentAge"));
                                birth.setText(job.getString("born"));
                                bowlingStyle.setText(job.getString("bowlingStyle"));
                               // bowling.setText(job.getString("bowling"));
                                battingStyle.setText(job.getString("battingStyle"));
                               // batting.setText(job.getString("batting"));
                                country.setText(job.getString("country"));
                                majorTeams.setText(job.getString("majorTeams"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    System.out.println("exception "+e);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    String intt(String ur){
        StringBuilder sb = new StringBuilder();
        try {
            URL url =new URL(ur);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);
            String buffer = "";

            while (buffer != null) {

                sb.append(buffer);
                buffer = br.readLine();

            }
        }catch (IOException e){}
        return sb.toString();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlayerModel pm=plist.get(position);
        getProfile(pm.getPid());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
