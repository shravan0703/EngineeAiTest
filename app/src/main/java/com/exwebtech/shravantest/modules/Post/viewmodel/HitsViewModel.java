package com.exwebtech.shravantest.modules.Post.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;
import com.exwebtech.shravantest.network.MyApiService;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HitsViewModel extends ViewModel {

    private Disposable disposable=null;
    private MutableLiveData<HitsResponseModel> hitsResponseMutableLiveData;


    public HitsViewModel(){
        hitsResponseMutableLiveData=new MutableLiveData<>();
        addMoreData();
    }


    private void addMoreData() {
        hitsResponseMutableLiveData.setValue(new HitsResponseModel());
    }


    public void getHitsData(int pageNumber){

        disposable= MyApiService.getService().getHitsData(pageNumber).subscribe(new Consumer<HitsResponseModel>() {
            @Override
            public void accept(HitsResponseModel hitsResponseDataModel) throws Exception {
                if(hitsResponseDataModel.getHits()!=null) {
                    hitsResponseMutableLiveData.setValue(hitsResponseDataModel);
                    disposable.dispose();
                }else {
                    disposable.dispose();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }


    public MutableLiveData<HitsResponseModel> getPostModelMutableLiveData() {
        return hitsResponseMutableLiveData;
    }


    public void clearViewDataModel(){
        hitsResponseMutableLiveData.setValue(null);
    }

}
