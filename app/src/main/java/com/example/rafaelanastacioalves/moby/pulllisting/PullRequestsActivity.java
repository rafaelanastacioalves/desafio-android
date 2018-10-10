package com.example.rafaelanastacioalves.moby.pulllisting;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rafaelanastacioalves.moby.R;
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener;
import com.example.rafaelanastacioalves.moby.vo.Pull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class PullRequestsActivity extends AppCompatActivity implements RecyclerViewClickListener {


    public static final String ARG_CREATOR = "creator_arg";
    public static final String ARG_REPOSITORY = "repository_arg";
    private final RecyclerViewClickListener clickListener = this;
    @BindView(R.id.pulls_list_recycler_view)
    RecyclerView mPullsListRecyclerView;
    private LiveDataEntityDetailsViewModel mLiveDataEntityDetailsViewModel;
    private PullsListAdapter mPullsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        subscribe();
        loadData();
    }

    private void setupView() {
        setContentView(R.layout.pull_request_activity);
        ButterKnife.bind(this);
        setupActionBar();
        setupRecyclerView(mPullsListRecyclerView);


    }

    private void setupRecyclerView(RecyclerView mPullsListRecyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPullsListRecyclerView.setLayoutManager(layoutManager);
        if (mPullsListAdapter == null) {
            mPullsListAdapter = new PullsListAdapter();
        }
        mPullsListAdapter.setRecyclerViewClickListener(clickListener);
        mPullsListRecyclerView.setAdapter(mPullsListAdapter);
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View view, int position) {
        Pull aPull = (Pull) view.getTag();
        Timber.i("Url: " + Uri.parse(aPull.getPullUrl()));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aPull.getPullUrl()));
        startActivity(browserIntent);
    }

    private void loadData() {
        String mCreatorString = getIntent().getStringExtra(ARG_CREATOR);
        String mRepositoryString = getIntent().getStringExtra(ARG_REPOSITORY);
        mLiveDataEntityDetailsViewModel.loadData(mCreatorString, mRepositoryString);
    }

    private void subscribe() {
        mLiveDataEntityDetailsViewModel = ViewModelProviders.of(this).get(LiveDataEntityDetailsViewModel.class);
        mLiveDataEntityDetailsViewModel.getEntityDetails().observe(this, this::setViewsWith);

    }

    private void setViewsWith(ArrayList<Pull> pull) {
        mPullsListAdapter.setItems(pull);
    }


}
