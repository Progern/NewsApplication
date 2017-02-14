package com.olegmisko.newsapplication.main.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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


public class WorldNewsPickupFragment extends Fragment {

    private Context mContext;
    private RecyclerView newsGroupsRecyclerView;
    private NewsListAdapter newsListAdapter;
    private List<News> bbc_l;
    private List<News> guardian_l;
    private List<News> die_zeit_l;
    private List<News> cnn_l;
    private List<News> new_york_magazine_l;
    private Handler mTimeHandler;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("World News");
        View fragmentMainView = inflater.inflate(R.layout.fragment_world_news_pickup, container, false);
        mContext = getActivity().getApplicationContext();
        newsGroupsRecyclerView = (RecyclerView) fragmentMainView.findViewById(R.id.news_titles_rv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsGroupsRecyclerView.getContext(),
                new LinearLayoutManager(mContext).getOrientation());
        newsGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newsGroupsRecyclerView.addItemDecoration(dividerItemDecoration);
        dialog = ProgressDialog.show(getContext(), "Loading News", "Please wait. Getting news data");
        performNewsHTTPRequests();
        dismissDialogAndLoadData();
        return fragmentMainView;
    }

    /* Fetch data, create NewsGroups */
    private void getLatestNews() {
        NewsGroup bbc = new NewsGroup("BBC", R.drawable.bbc_news_icon, bbc_l);
        NewsGroup theGuardian = new NewsGroup("The Guardian", R.drawable.the_guardian_icon, guardian_l);
        NewsGroup cnn = new NewsGroup("CNN", R.drawable.cnn_news, cnn_l);
        NewsGroup die_zeit = new NewsGroup("Die Zeit", R.drawable.die_zeit_icon, die_zeit_l);
        NewsGroup ny_magazine = new NewsGroup("New York Magazine", R.drawable.nymag_icon, new_york_magazine_l);
        List allLatestNews = Arrays.asList(bbc, theGuardian, cnn, die_zeit, ny_magazine);
        newsListAdapter = new NewsListAdapter(mContext, allLatestNews);
        newsGroupsRecyclerView.setAdapter(newsListAdapter);
    }

    /* Perform requests and fetch data from
    * endpoint server with news stored on. */
    private void performNewsHTTPRequests() {
        getNewsListFromSource(AppConfig.BBC_SOURCE);
        getNewsListFromSource(AppConfig.NEW_YORK_MAGAZINE);
        getNewsListFromSource(AppConfig.THE_GUARDIAN_SOURCE);
        getNewsListFromSource(AppConfig.DIE_ZEIT);
        getNewsListFromSource(AppConfig.CNN);
    }


    /* Uses Retrofit2 to perform HTTP-requests and
     * convert response into NewsGroup model */
    @Nullable
    private void getNewsListFromSource(final String source) {
        Call<NewsList> newsCall = NetworkService.API.GETLatesNewsList(source, AppConfig.TOP, AppConfig.API_KEY);
        newsCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    checkSourceAndFetchData(source, response.body().getNewsList());
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
            case "bbc-news":
                bbc_l = responseList;
                break;
            case "the-guardian-uk":
                guardian_l = responseList;
                break;
            case "cnn":
                cnn_l = responseList;
                break;
            case "die-zeit":
                die_zeit_l = responseList;
                break;
            case "new-york-magazine":
                new_york_magazine_l = responseList;
                break;
        }
    }

    private void dismissDialogAndLoadData() {
        mTimeHandler = new Handler();
        mTimeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                getLatestNews();
            }
        }, 5000);
    }
}
