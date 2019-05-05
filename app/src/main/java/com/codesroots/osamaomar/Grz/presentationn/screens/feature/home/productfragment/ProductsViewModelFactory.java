package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.codesroots.osamaomar.Grz.datalayer.apidata.ApiClient;
import com.codesroots.osamaomar.Grz.datalayer.apidata.ServerGateway;

public class ProductsViewModelFactory implements ViewModelProvider.Factory {


    private Application application;
    private int subcategry_id,user_Id,type;

    public ProductsViewModelFactory(Application application1, int id,int userid,int type1) {
        application = application1;
        subcategry_id = id;
        user_Id = userid;
        type =type1;
    }

    @SuppressWarnings("SingleStatementInBlock")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//         if (modelClass == ProductsDetailsViewModel.class)
//        {
//            return (T) new ProductsDetailsViewModel(getApiService());
//        }

        throw new IllegalArgumentException("Invalid view model class type");
    }


    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }

}
