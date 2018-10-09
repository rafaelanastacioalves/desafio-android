package com.example.rafaelanastacioalves.moby.repolisting;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.rafaelanastacioalves.moby.repository.DataSourceFactory;
import com.example.rafaelanastacioalves.moby.repository.PagedRepoDataSource;
import com.example.rafaelanastacioalves.moby.vo.Repo;

import java.util.concurrent.Executors;

import static android.arch.lifecycle.Transformations.switchMap;

class LiveDataRepoListViewModel extends ViewModel {

    private LiveData<Boolean> mIsLoading;
    private final LiveData<PagedList<Repo>> mMainEntityPagedList = setupPagedList();

    @NonNull
    private LiveData<PagedList<Repo>> setupPagedList() {

        DataSourceFactory dataSourceFactory = new DataSourceFactory("Java", "start");
        mIsLoading = switchMap(dataSourceFactory.getDataSource(), PagedRepoDataSource::getLiveLoadStatus);

        LivePagedListBuilder<String, Repo> mLivePagedListBuilder =  new LivePagedListBuilder<>(dataSourceFactory, 10);

        return mLivePagedListBuilder
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build();
    }

    public LiveData<PagedList<Repo>> getMainEntityList() {
        return mMainEntityPagedList;
    }


    public LiveData<Boolean> getLoadStatus() {
        return mIsLoading;
    }
}
