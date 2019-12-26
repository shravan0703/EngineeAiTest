package com.exwebtech.shravantest.network;


import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;
import com.exwebtech.shravantest.utils.ApiConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyService {
    //TODO getting post title & created at
    @GET(ApiConstants.GET_HITS_DATA)
    Observable<HitsResponseModel> getHitsData(@Query("page") int page);
}
