package com.exwebtech.shravantest.network;

import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataService extends MyApiService {

    //Holds service instance
    private MyService service;

    // Constructor
    RemoteDataService() {
        service = new RetrofitHelper().getMyService();
    }


    @Override
    public Observable<HitsResponseModel> getHitsData(int page) {
        return service.getHitsData(page)
                .subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()); // “listen” on UIThread;
    }
}
