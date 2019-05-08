package com.codesroots.osamaomar.Grz.datalayer.apidata;

import com.codesroots.osamaomar.Grz.models.entities.Currency;
import com.codesroots.osamaomar.Grz.models.entities.DefaultAdd;
import com.codesroots.osamaomar.Grz.models.entities.AddToFavModel;
import com.codesroots.osamaomar.Grz.models.entities.CartItems;
import com.codesroots.osamaomar.Grz.models.entities.Favoriets;
import com.codesroots.osamaomar.Grz.models.entities.LoginResponse;
import com.codesroots.osamaomar.Grz.models.entities.MainView;
import com.codesroots.osamaomar.Grz.models.entities.MyOrders;
import com.codesroots.osamaomar.Grz.models.entities.OrderModel;
import com.codesroots.osamaomar.Grz.models.entities.ProductDetails;
import com.codesroots.osamaomar.Grz.models.entities.ProductRate;
import com.codesroots.osamaomar.Grz.models.entities.Products;
import com.codesroots.osamaomar.Grz.models.entities.Register;
import com.codesroots.osamaomar.Grz.models.entities.Sidemenu;
import com.codesroots.osamaomar.Grz.models.entities.StoreSetting;
import com.codesroots.osamaomar.Grz.models.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.Grz.models.entities.UserLocations;
import com.codesroots.osamaomar.Grz.models.entities.offers;
import java.util.ArrayList;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerGateway {

    @GET("Products/mainpage.json")
    Observable<MainView> getMainViewData();

    @GET("Categories/allcategories.json")
    Observable<Sidemenu> getSideMenuData();

    @GET("Products/productdetails/{product_id}.json")
    Observable<ProductDetails> getProductDetails(
            @Path("product_id") int product_id
    );


    @GET("Subcats/getsubcats/{cat_id}/{user_id}.json")
    Observable<SubCategriesWithProducts> getSubCatswithProducts(
            @Path("cat_id") int cat_id,
            @Path("user_id") int user_id
    );


    @GET("Products/getproductsbycatid/{cat_id}.json")
    Observable<Products> getProducts(
            @Path("cat_id") int cat_id
    );


    @FormUrlEncoded
    @POST("Favourites/addfavourite.json")
        Observable<AddToFavModel> addToFave(
            @Field("user_id") int user_id,
            @Field("product_id") int product_id
    );


    @GET("Favourites/delete/{favid}.json")
    Observable<DefaultAdd> DeleteFav(
            @Path("favid") int favid
    );


    @GET("Favourites/getproductsfav/{userid}.json")
    Observable<Favoriets> getFavProducts(
            @Path("userid") int userid
    );


    @FormUrlEncoded
    @POST("Products/viewproduct.json")
    Observable<CartItems> getCartProducts(
            @Field("arrayofid[]") ArrayList<Integer> ids
    );


    @GET("orders/getuserorder/{userid}.json")
    Observable<MyOrders> retrieveUserOrders(
            @Path("userid") int userid
    );


    @GET("BillingAddress/index/{userid}.json")
    Observable<UserLocations> retrieveUserLocations(
            @Path("userid") int userid
    );



    @GET("Offers/getoffers.json")
    Observable<offers> retrieveOffers();

    @GET("Products/ratedetails/{product_id}.json")
    Observable<ProductRate> getProductRates(
            @Path("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("Productrates/addrate.json")
    Observable<DefaultAdd> addProductRate(
            @Field("user_id") int user_id,
            @Field("product_id") int product_id,
            @Field("rate") float rate,
            @Field("comment") String comment
    );


    ////////////// make order
    @POST("Orders/addorder.json")
    @Headers("Accept: Application/json")
    Call<ResponseBody> makeOrder(
            @Body OrderModel orderModel
    );


    ////////////// get  Settings
    @GET("Storesettings.json")
    @Headers("Accept: Application/json")
    Observable<StoreSetting> getStorSetting();

    ////////////// get  search results
    @FormUrlEncoded
    @POST("productsUseCase/searchbyname/{type}/{userid}.json")
    Observable<Products> getSearchResult(
            @Field("name") String  name,
            @Path("type") String type
    );

    ////////////// get currency
    @GET("Currencies/currency.json")
    @Headers("Accept: Application/json")
    Observable<Currency> Currency();


    @FormUrlEncoded
    @POST("users/add.json")
    Observable<Register> userregister(
            @Field("username") String  username,
            @Field("userpass") String password,
            @Field("status") int email_verified,
            @Field("user_roll") int user_group_id
    );

    @FormUrlEncoded
    @POST("users/token.json")
    Observable<LoginResponse> userlogin(
            @Field("username") String  username,
            @Field("userpass") String password
    );

}
