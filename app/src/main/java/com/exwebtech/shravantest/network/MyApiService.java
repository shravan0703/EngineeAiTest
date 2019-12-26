package com.exwebtech.shravantest.network;

import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Query;

public abstract class MyApiService {

    private static MyApiService service;

    private static void setServiceType() {
        service = new RemoteDataService();
    }

    public static MyApiService getService() {
        if (service == null) {
            setServiceType();
        }

        return service;
    }

    // getting Data
    public abstract Observable<HitsResponseModel> getHitsData(@Query("page") int page);
}
