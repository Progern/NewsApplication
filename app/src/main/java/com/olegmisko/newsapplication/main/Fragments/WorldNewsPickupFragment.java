package com.olegmisko.newsapplication.main.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Adapters.NewsListAdapter;
import com.olegmisko.newsapplication.main.Models.News;
import com.olegmisko.newsapplication.main.Models.NewsGroup;

import java.util.Arrays;
import java.util.List;


public class WorldNewsPickupFragment extends Fragment {

    private Context mContext;
    private RecyclerView newsGroupsRecyclerView;
    private NewsListAdapter newsListAdapter;
    // news_titles_rv

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("World News");
        View fragmentMainView = inflater.inflate(R.layout.fragment_world_news_pickup, container, false);
        mContext = getActivity().getApplicationContext();
        News firstOneBBC = new News("Trump executive order on refugee and travel suspension",
                "US President Donald Trump has signed a wide-ranging executive order, halting all refugee admissions and barring " +
                        "temporarily people from seven Muslim-majority countries. His decision has been sharply criticised by rights groups.");

        News secondOneBBC = new News("Trump executive order", "President Donald Trump's order suspending immigration from seven " +
                "Muslim-majority countries for 90 days has left many foreigners in limbo. " +
                "Here are some of their experiences:");

        News guardianOne = new News("François Fillon warns: 'Leave my wife out of the election'", "The beleaguered rightwing French presidential candidate François Fillon has used a speech at a Paris " +
                "rally to hit back at claims that his wife was paid €500,000 over eight years for a fake job as a " +
                "parliamentary assistant, warning: “Leave my wife out of the election.”");

        News guardianTwo = new News("US commando dies in Yemen raid as Trump counter-terror plans take shape", "The Pentagon did not address rumors of civilian casualties " +
                "currently circulating on social media. " +
                "An aircraft malfunction led to what the Pentagon called a " +
                "“hard landing in a nearby location”. Commandos intentionally destroyed the aircraft, which local residents and officials said was a helicopter.");

        NewsGroup bbc = new NewsGroup("BBC", R.drawable.bbc_news_icon, Arrays.asList(firstOneBBC, secondOneBBC));
        NewsGroup theGuardian = new NewsGroup("The Guardian", R.drawable.the_guardian_icon, Arrays.asList(guardianOne, guardianTwo));
        List newsGroups = Arrays.asList(bbc, theGuardian);

        newsGroupsRecyclerView = (RecyclerView) fragmentMainView.findViewById(R.id.news_titles_rv);
        newsListAdapter = new NewsListAdapter(mContext, newsGroups);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsGroupsRecyclerView.getContext(),
                new LinearLayoutManager(mContext).getOrientation());
        newsGroupsRecyclerView.setAdapter(newsListAdapter);
        newsGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newsGroupsRecyclerView.addItemDecoration(dividerItemDecoration);
        return fragmentMainView;
    }
}
