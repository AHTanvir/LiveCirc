package live.livecirc;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static live.livecirc.R.id.Relative_layoutfor_fragments;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private static final String TAG_LIVE_FRAGMENT = "livematch";
    private TabLayout tabLayout;
    FragmentManager fragmentManager;
    int selectedtab=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tab_view);
        tabLayout.addOnTabSelectedListener(this);
        if(selectedtab !=0)
            onTabSelected(tabLayout.getTabAt(0));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        selectedtab=position;
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                if (position == 0 ) {
                    FragmentManager fm = getSupportFragmentManager();
                    LiveFragment live =(LiveFragment)fm.findFragmentByTag(TAG_LIVE_FRAGMENT);
                    if(live==null){
                        try {
                            live=new LiveFragment();
                            fm.beginTransaction().replace(Relative_layoutfor_fragments, live, TAG_LIVE_FRAGMENT).commit();
                        }catch (IllegalStateException ex){
                            ex.printStackTrace();
                        }
                    }
                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    //fragmentManager.beginTransaction().replace(Relative_layoutfor_fragments, chatListFragment,Constant.TAG_CONTACTLIST_FRAGMENT).commit();
                }
                if (position == 1) {
                    ScheduleFragment scheduleFrag = new ScheduleFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(Relative_layoutfor_fragments, scheduleFrag, scheduleFrag.getTag()).commit();
                }
                if (position == 2) {
                    NewsFragment news = new NewsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(Relative_layoutfor_fragments, news, news.getTag()).commit();
                }
                if (position == 3) {
                    PlayerFragment pla = new PlayerFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(Relative_layoutfor_fragments, pla, pla.getTag()).commit();
                }
            }
        });
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
