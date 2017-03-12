package com.olegmisko.newsapplication.main.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Adapters.NewsListAdapter;
import com.olegmisko.newsapplication.main.Config.AppConstants;
import com.olegmisko.newsapplication.main.Models.News;
import com.olegmisko.newsapplication.main.Models.NewsGroup;
import com.olegmisko.newsapplication.main.Models.NewsList;
import com.olegmisko.newsapplication.main.Services.NetworkService;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SportsNewsPickupFragment extends Fragment {
    private Context mContext;
    @BindView(R.id.news_titles_rv) RecyclerView newsGroupsRecyclerView;

    /* These lists hold the latest news from data sources
     */
    private List<News> espn;
    private List<News> football_italia;
    private List<News> nfl;
    private List<News> sky_sports;
    private List<News> talksport;

    private boolean wasAlreadyLoaded = false;
    private int loadedNewsHeaders = 0;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Sports News");
        View fragmentMainView = inflater.inflate(R.layout.fragment_world_news_pickup, container, false);
        mContext = getActivity().getApplicationContext();
        ButterKnife.bind(this, fragmentMainView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsGroupsRecyclerView.getContext(),
                new LinearLayoutManager(mContext).getOrientation());
        newsGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newsGroupsRecyclerView.addItemDecoration(dividerItemDecoration);
        dialog = ProgressDialog.show(getContext(), "Loading News", "Please wait. Getting news data");

        /* To prevent double, triple... request sending */
        if (!wasAlreadyLoaded) {
            performNewsHTTPRequests();
            wasAlreadyLoaded = true;
        }
        return fragmentMainView;
    }

    /* Fetch data, create NewsGroups */
    private void getLatestNews() {
        NewsGroup espn_group = new NewsGroup("ESPN", R.drawable.espn, espn);
        NewsGroup football_italia_group = new NewsGroup("Football Italia", R.drawable.football_italia, football_italia);
        NewsGroup nfl_group = new NewsGroup("NFL", R.drawable.nfl, sky_sports);
        NewsGroup sky_sports_group = new NewsGroup("SkySports", R.drawable.skysports, nfl);
        NewsGroup talksport_group = new NewsGroup("Talksport", R.drawable.talksport, talksport);
        List allLatestNews = Arrays.asList(espn_group, football_italia_group, nfl_group, sky_sports_group, talksport_group);
        NewsListAdapter newsListAdapter = new NewsListAdapter(mContext, allLatestNews);
        newsGroupsRecyclerView.setAdapter(newsListAdapter);
    }

    /* Perform requests and fetch data from
    * endpoint server with news stored on. */
    private void performNewsHTTPRequests() {
        getNewsListFromSource(AppConstants.ESPN);
        getNewsListFromSource(AppConstants.FOOTBALL_ITALIA);
        getNewsListFromSource(AppConstants.NFL);
        getNewsListFromSource(AppConstants.SKY_SPORTS);
        getNewsListFromSource(AppConstants.TALKSPORT);
    }

    /* Performs HTTP-requests to the endpoint-server
     * and fetches data from source name */
    private void getNewsListFromSource(final String source) {
        Call<NewsList> newsCall = NetworkService.API.GETLatesNewsList(source, AppConstants.TOP, AppConstants.API_KEY);
        newsCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    checkSourceAndFetchData(source, response.body().getNewsList());
                    loadedAnotherHeader();
                    Log.d("MY_LOG", "Response is successful");
                } else {
                    Log.d("MY_LOG", "Response is unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Log.d("MY_LOG", "Response failed");
            }
        });
    }

    /* Checks due to request into what
    * array list we should fetch our data */
    private void checkSourceAndFetchData(String source, List<News> responseList) {
        switch (source) {
            case "espn":
                espn = responseList;
                loadedNewsHeaders++;
                break;
            case "football-italia":
                football_italia = responseList;
                loadedNewsHeaders++;
                break;
            case "nfl-news":
                sky_sports = responseList;
                loadedNewsHeaders++;
                break;
            case "sky-sports-news":
                nfl = responseList;
                loadedNewsHeaders++;
                break;
            case "talksport":
                talksport = responseList;
                loadedNewsHeaders++;
                break;
        }
    }

    /* Checks how many headers were loaded
     * and if all headers are loaded dismisses
     * the dialog and fetches data*/
    private void loadedAnotherHeader() {
        loadedNewsHeaders++;
        Log.d("MY_LOG", "Already loaded " + loadedNewsHeaders + " news headers");
        if (loadedNewsHeaders == 10) {
            dialog.dismiss();
            getLatestNews();
        }
    }
}
