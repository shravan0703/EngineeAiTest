package com.exwebtech.shravantest.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.exwebtech.shravantest.R;
import com.exwebtech.shravantest.modules.Post.adapter.RecyclerViewAdapter;
import com.exwebtech.shravantest.modules.Post.interfaces.ItemSelectedInterface;
import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;
import com.exwebtech.shravantest.modules.Post.viewmodel.HitsViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ItemSelectedInterface {

    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private HitsViewModel hitsViewModel;

    private int pageNumber = 1;

    private List<HitsResponseModel.HitsData> hitsList = new ArrayList<>();

    private RecyclerViewAdapter recyclerViewAdapter;
    private int count=0;

    private int totalPageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
    }

    public void initViews(){

        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swip_refresh_lyt);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.item_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        toolbar.setTitle("Checked posts = " + (count));

        hitsViewModel = ViewModelProviders.of(this).get(HitsViewModel.class);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        hitsViewModel.getHitsData(pageNumber);

        hitsViewModel.getPostModelMutableLiveData().observe(this, new Observer<HitsResponseModel>() {
            @Override
            public void onChanged(HitsResponseModel hitsResponseDataModel) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (hitsResponseDataModel != null) {

                    if (pageNumber == 1) {

                        hitsList = new ArrayList<>();
                        hitsList.addAll(hitsResponseDataModel.getHits());
                        totalPageCount = hitsResponseDataModel.getNbPages();
                    } else {
                        hitsList.addAll(hitsResponseDataModel.getHits());
                    }
                    setRecyclerViewData(hitsList);
                }

            }
        });

        swipeToRefreshHits();
    }


    private void swipeToRefreshHits() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO write code to refresh all hits data
                count=0;
                toolbar.setTitle("Checked posts = " + (count));
                progressBar.setVisibility(View.VISIBLE);
                pageNumber = 1;
                hitsViewModel.getHitsData(pageNumber);

            }
        });

    }

    private void setRecyclerViewData(List<HitsResponseModel.HitsData> hitsDataList) {
        if (pageNumber == 1) {
            recyclerViewAdapter = new RecyclerViewAdapter(HomeActivity.this, hitsDataList);
            recyclerViewAdapter.itemSelectedInterface = this;
            recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            recyclerViewAdapter.notifyDataSetChanged();
        }

        recyclerViewAdapter.setLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onAddMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (hitsDataList.size() > 0) {
                            progressBar.setVisibility(View.GONE);
                            loadMoreHitsData();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hitsViewModel != null) {
            hitsViewModel.clearViewDataModel();
        }
    }

    private void loadMoreHitsData() {
        pageNumber++;
        if (pageNumber <= totalPageCount) {
            progressBar.setVisibility(View.VISIBLE);
            hitsViewModel.getHitsData(pageNumber);

            recyclerViewAdapter.setLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
                @Override
                public void onAddMore() {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (hitsList.size() > 0) {
                                progressBar.setVisibility(View.GONE);
                                loadMoreHitsData();
                            }
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "All " + totalPageCount + " pages data loaded no more pages to load data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(HitsResponseModel.HitsData hitsData) {
        if (hitsData != null) {
            if (hitsData.isSelected()) {
                count = count + 1;
                toolbar.setTitle("Checked posts = " + (count));
            } else {
                count = count - 1;
                toolbar.setTitle("Checked posts = " + (count));
            }
        }
    }
}
