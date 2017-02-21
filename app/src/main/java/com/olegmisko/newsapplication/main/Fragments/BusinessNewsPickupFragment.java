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
import com.olegmisko.newsapplication.main.Config.AppConfig;
import com.olegmisko.newsapplication.main.Models.News;
import com.olegmisko.newsapplication.main.Models.NewsGroup;
import com.olegmisko.newsapplication.main.Models.NewsList;
import com.olegmisko.newsapplication.main.Services.NetworkService;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessNewsPickupFragment extends Fragment {
    private Context mContext;
    private RecyclerView newsGroupsRecyclerView;
    private NewsListAdapter newsListAdapter;

    /* These lists hold the latest news from data sources
     * TODO: Simple caching in Realm */
    private List<News> business_insider;
    private List<News> financial_times;
    private List<News> the_new_york_times;
    private List<News> usa_today;
    private List<News> the_wall_street_journal;

    private boolean wasAlreadyLoaded = false;
    private int loadedNewsHeaders = 0;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Business News");
        View fragmentMainView = inflater.inflate(R.layout.fragment_world_news_pickup, container, false);
        mContext = getActivity().getApplicationContext();
        newsGroupsRecyclerView = (RecyclerView) fragmentMainView.findViewById(R.id.news_titles_rv);
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
        NewsGroup business_insider_group = new NewsGroup("Business Insider", R.drawable.business_insider, business_insider);
        NewsGroup financial_times_group = new NewsGroup("Financial times", R.drawable.financial_times_ico, financial_times);
        NewsGroup the_ny_times_group = new NewsGroup("The NY Times", R.drawable.new_york_times, usa_today);
        NewsGroup usa_today_group = new NewsGroup("USA Today", R.drawable.usa_today, the_new_york_times);
        NewsGroup the_ws_group = new NewsGroup("The Wall Street Journal ", R.drawable.wall_street_journal, the_wall_street_journal);
        List allLatestNews = Arrays.asList(business_insider_group, financial_times_group, the_ny_times_group, usa_today_group, the_ws_group);
        newsListAdapter = new NewsListAdapter(mContext, allLatestNews);
        newsGroupsRecyclerView.setAdapter(newsListAdapter);
    }

    /* Perform requests and fetch data from
    * endpoint server with news stored on. */
    private void performNewsHTTPRequests() {
        getNewsListFromSource(AppConfig.BUSINESS_INSIDER);
        getNewsListFromSource(AppConfig.FINANCIAL_TIMES);
        getNewsListFromSource(AppConfig.THE_NEW_YORK_TIMES);
        getNewsListFromSource(AppConfig.USA_TODAY);
        getNewsListFromSource(AppConfig.THE_WALL_STREET_JOURNAL);
    }

    /* Performs HTTP-requests to the endpoint-server
     * and fetches data from source name */
    @Nullable
    private void getNewsListFromSource(final String source) {
        Call<NewsList> newsCall = NetworkService.API.GETLatesNewsList(source, AppConfig.TOP, AppConfig.API_KEY);
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
            case "business-insider":
                business_insider = responseList;
                loadedNewsHeaders++;
                break;
            case "financial-times":
                financial_times = responseList;
                loadedNewsHeaders++;
                break;
            case "the-new-york-times":
                usa_today = responseList;
                loadedNewsHeaders++;
                break;
            case "usa-today":
                the_new_york_times = responseList;
                loadedNewsHeaders++;
                break;
            case "the-wall-street-journal":
                the_wall_street_journal = responseList;
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
