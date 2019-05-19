package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.cartfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.OrderModel;
import com.codesroots.osamaomar.grz.models.entities.Product;
import com.codesroots.osamaomar.grz.models.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.cartfragment.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Product> dataBeans;
    public List<OrderModel.productSize> products = new ArrayList<>();
    private CartFragment cartFragment;
    public CartAdapter(Context mcontext, List<Product> productsbysubcats1, CartFragment cartFragmentt) {
        context = mcontext;
        dataBeans = productsbysubcats1;
        cartFragment = cartFragmentt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.name.setText(dataBeans.get(position).getName());
            holder.amount.setText(context.getText(R.string.remendier) + " " +
                    String.valueOf(dataBeans.get(position).getAmount()) + " " + context.getText(R.string.num));

            holder.price.setText(dataBeans.get(position).getPricewithoutcoin()*Integer.valueOf(holder.products_count.getText().toString())
                    +dataBeans.get(position).getCurrentcurrency()) ;

            products.add(new OrderModel.productSize(dataBeans.get(position).getProductid()));
            if (dataBeans.get(position).isHasoffer()) {
                products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                        dataBeans.get(position).getAfteroffer()));
                products.get(position).setNotice("خصم بسبب العرض رقم " + dataBeans.get(position).getOfferid());
            } else {
                products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                        Float.valueOf(dataBeans.get(position).getPrice())));
            }
            holder.ratingBar.setRating(dataBeans.get(position).getRate());
            holder.rateCount.setText("(" + dataBeans.get(position).getRatecount() + ")");
            Glide.with(context.getApplicationContext())
                    .load(dataBeans.get(position).getPhoto()).placeholder(R.drawable.product).dontAnimate()
                    .into(holder.Image);
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        holder.delete_item.setOnClickListener(v -> {
            cartFragment.onRemoveProduct(products.get(position).getProductsize_id(),position);
        });

        holder.quintityPlus.setOnClickListener(v ->
        {
            if (Integer.valueOf(holder.products_count.getText().toString()) + 1 <= dataBeans.get(position).getAmount()) {
                int newvalue = Integer.valueOf(holder.products_count.getText().toString())+1;
                holder.products_count.setText(String.valueOf(newvalue ));
                products.get(position).setAmount(Integer.valueOf(holder.products_count.getText().toString()));
                if (dataBeans.get(position).isHasoffer()) {
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            dataBeans.get(position).getAfteroffer()));
                } else
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            Float.valueOf(dataBeans.get(position).getPrice())));
            } else
                Toast.makeText(context, context.getText(R.string.requestnotallow), Toast.LENGTH_SHORT).show();

            holder.price.setText(dataBeans.get(position).getPricewithoutcoin()*Integer.valueOf(holder.products_count.getText().toString())
                    +dataBeans.get(position).getCurrentcurrency()) ;


            //  notifyItemChanged(position);
        });

        holder.quintityMinus.setOnClickListener(v -> {
            int newValue = Integer.valueOf(holder.products_count.getText().toString()) - 1;
            if (newValue > 0) {
                holder.products_count.setText(String.valueOf(newValue));
                products.get(position).setAmount(Integer.valueOf(holder.products_count.getText().toString()));
                if (dataBeans.get(position).isHasoffer()) {
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            dataBeans.get(position).getAfteroffer()));
                } else
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            Float.valueOf(dataBeans.get(position).getPrice())));
            }
            holder.price.setText(dataBeans.get(position).getPricewithoutcoin()*Integer.valueOf(holder.products_count.getText().toString())
                    +dataBeans.get(position).getCurrentcurrency()) ;

            //   notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image, delete_item, quintityMinus, quintityPlus;
        private TextView name, rateCount, amount, price;
        private RatingBar ratingBar;
        private EditText products_count;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            amount = mView.findViewById(R.id.quentity);
            rateCount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            delete_item = mView.findViewById(R.id.delete_item);
            quintityMinus = mView.findViewById(R.id.quintityMinus);
            quintityPlus = mView.findViewById(R.id.quintityPlus);
            products_count = mView.findViewById(R.id.quintity_value);
        }
    }
}