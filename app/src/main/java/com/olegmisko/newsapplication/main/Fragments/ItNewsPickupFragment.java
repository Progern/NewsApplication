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


public class ItNewsPickupFragment extends Fragment {
    private Context mContext;
    @BindView(R.id.news_titles_rv) RecyclerView newsGroupsRecyclerView;

    /* These lists hold the latest news from data sources */
    private List<News> ign;
    private List<News> reddit;
    private List<News> techcrunch;
    private List<News> t3n;
    private List<News> google;

    private boolean wasAlreadyLoaded = false;
    private int loadedNewsHeaders = 0;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("IT News");
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
        NewsGroup ign_group = new NewsGroup("IGN", R.drawable.ign, ign);
        NewsGroup reddit_group = new NewsGroup("Reddit", R.drawable.reddit, reddit);
        NewsGroup techcrunch_group = new NewsGroup("Techcrunch", R.drawable.techcrunch, techcrunch);
        NewsGroup t3n_group = new NewsGroup("T3N", R.drawable.t3n, t3n);
        NewsGroup google_group = new NewsGroup("Google", R.drawable.google_news, google);
        List allLatestNews = Arrays.asList(ign_group, reddit_group, techcrunch_group, t3n_group, google_group);
        NewsListAdapter newsListAdapter = new NewsListAdapter(mContext, allLatestNews);
        newsGroupsRecyclerView.setAdapter(newsListAdapter);
    }

    /* Perform requests and fetch data from
    * endpoint server with news stored on. */
    private void performNewsHTTPRequests() {
        getNewsListFromSource(AppConstants.IGN);
        getNewsListFromSource(AppConstants.REDDIT);
        getNewsListFromSource(AppConstants.TECHCRUNCH);
        getNewsListFromSource(AppConstants.T3N);
        getNewsListFromSource(AppConstants.GOOGLE);
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
            case "ign":
                ign = responseList;
                loadedNewsHeaders++;
                break;
            case "reddit-r-all":
                reddit = responseList;
                loadedNewsHeaders++;
                break;
            case "t3n":
                t3n = responseList;
                loadedNewsHeaders++;
                break;
            case "techcrunch":
                techcrunch = responseList;
                loadedNewsHeaders++;
                break;
            case "google-news":
                google = responseList;
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
