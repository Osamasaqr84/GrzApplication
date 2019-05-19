package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productdetailsfragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.datalayer.localdata.product.entities.ProductDB;
import com.codesroots.osamaomar.grz.models.entities.Product;
import com.codesroots.osamaomar.grz.models.entities.StoreSetting;
import com.codesroots.osamaomar.grz.models.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.models.usecases.Publicusecase;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.ProductsDetailsViewModel;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productdetailsfragment.adapters.ImagesAdapterForColor;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productdetailsfragment.adapters.ProductSizesAdapter;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productdetailsfragment.adapters.SliderPagerAdapter;

import static com.codesroots.osamaomar.grz.models.entities.names.PRODUCT_ID;


public class ProductDetailsFragment extends Fragment {

    RecyclerView recyclerViewforitemcolors, sizes_rec;
    FrameLayout loading;
    public TextView product_name, description, price, ratecount,
            amount, addtocart, charege, images_count;

    RatingBar ratingBar;
    public ImageView item_img;
    private Group colorscontainer;
    int userid = PreferenceHelper.getUserId();
    ProductSizesAdapter productSizesAdapter;
    ImageView addToFav, share;
    public StoreSetting setting;
    private ViewPager product_images;
    public boolean freecharg = false;
    ProductsDetailsViewModel productsDetailsViewModel;
    ImagesAdapterForColor imagesAdapterForColor;
    private Product product;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        int productid = getArguments().getInt(PRODUCT_ID, 0);
        findViewsFromXml(view);
        share.setOnClickListener(v -> Publicusecase.shareTextUrl(getContext(), "http://grzexpress.com/ar/products/details/" + productid));
        productsDetailsViewModel.getProductDetailsData(productid);

        productsDetailsViewModel.errormessage.observe(this, s ->
                Toast.makeText(getActivity(), getText(R.string.error_in_data), Toast.LENGTH_SHORT).show()
        );

        productsDetailsViewModel.productMutableLiveData.observe(this, this::setProductDetailsToViews);

        addtocart.setOnClickListener(v -> {
            if (userid > 0) {
                ProductDB product = new ProductDB(productid, imagesAdapterForColor.mSelectedItem, productSizesAdapter.mSelectedItem);
                productsDetailsViewModel.AddToCart(product);
            } else
                Toast.makeText(getActivity(), getActivity().getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productsDetailsViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ProductsDetailsViewModel.class);
        productsDetailsViewModel.statues.observe(getActivity(), s ->
        {
            if (s.matches("0"))
                Toast.makeText(getContext(), getText(R.string.aleady_exists), Toast.LENGTH_SHORT).show();
            else if (s.matches("1")) {
                Toast.makeText(getContext(), getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
                ((AddorRemoveCallbacks) getActivity()).onAddProduct();
            } else if (s.matches("2"))
                Toast.makeText(getContext(), getText(R.string.erroroccure), Toast.LENGTH_SHORT).show();
        });
    }

    private void setProductDetailsToViews(Product product) {
        product_name.setText(product.getName());
        product_images.setAdapter(new SliderPagerAdapter(getActivity(), product.getPhotos()));
        images_count.setText(1 + "/" + product.getPhotos().size());
        product_images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.d("dd", i1 + "");
            }

            @Override
            public void onPageSelected(int i) {
                images_count.setText(i + 1 + "/" + product.getPhotos().size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        price.setText(product.getPrice());
        ratecount.setText("(" + product.getRatecount() + ")");
        ratingBar.setRating(product.getRate());
        description.setText(product.getDescription());

        productSizesAdapter = new ProductSizesAdapter(getActivity(), product.getSizes(), this);
        sizes_rec.setAdapter(productSizesAdapter);
        if (product.getColores().size() > 0) {
            colorscontainer.setVisibility(View.VISIBLE);
            imagesAdapterForColor = new ImagesAdapterForColor(getActivity(), product.getColores());
            recyclerViewforitemcolors.setAdapter(imagesAdapterForColor);
        } else
            colorscontainer.setVisibility(View.GONE);
    }

    private void findViewsFromXml(View view) {
        product_images = view.findViewById(R.id.pro_images);
        sizes_rec = view.findViewById(R.id.sizes);
        recyclerViewforitemcolors = view.findViewById(R.id.colors);
        loading = view.findViewById(R.id.progress);
        product_name = view.findViewById(R.id.product_name);
        description = view.findViewById(R.id.description);
        price = view.findViewById(R.id.price);
        ratecount = view.findViewById(R.id.rate_count);
        ratingBar = view.findViewById(R.id.rates);
        item_img = view.findViewById(R.id.item_img);
        addToFav = view.findViewById(R.id.fav);
        amount = view.findViewById(R.id.amount);
        addtocart = view.findViewById(R.id.gotocart);
        charege = view.findViewById(R.id.charge);
        colorscontainer = view.findViewById(R.id.colorscontainer);
        images_count = view.findViewById(R.id.images_count);
        share = view.findViewById(R.id.share);
    }

    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());
    }
}