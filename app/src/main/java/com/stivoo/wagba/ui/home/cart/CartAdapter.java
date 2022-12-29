package com.stivoo.wagba.ui.home.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.MealModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private static ArrayList<CartItem> cartMealsList = new ArrayList<>();

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_element_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(holder.getImg()).load(cartMealsList.get(position).getImg()).into(holder.img);
        DecimalFormat df = new DecimalFormat("#.00");
        Float price = Float.parseFloat(cartMealsList.get(position).getPrice().substring(4)) * cartMealsList.get(position).getQty();
        holder.price.setText(String.valueOf(df.format(price)));
        holder.name.setText(cartMealsList.get(position).getMeal_name());
        holder.qty.setText(String.valueOf(cartMealsList.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return cartMealsList.size();
    }

    public void setList(ArrayList<CartItem> cartMealsList) {
        this.cartMealsList = cartMealsList;
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView price;
        TextView name;
        TextView qty;
        Button inc;
        Button dec;

        public ImageView getImg() {
            return img;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.cimg_meal);
            price = itemView.findViewById(R.id.ctv_meal_price_val);
            name = itemView.findViewById(R.id.ctv_meal_name);
            qty = itemView.findViewById(R.id.ctv_quantity);
            inc = itemView.findViewById(R.id.cbtn_inc);
            dec = itemView.findViewById(R.id.cbtn_dec);


            inc.setOnClickListener(v -> {
                    qty.setText(String.valueOf(Integer.parseInt(qty.getText().toString()) + 1));
                    DecimalFormat df = new DecimalFormat("#.00");
                    Float price_val = (Float.parseFloat(price.getText().toString())/(Integer.parseInt(qty.getText().toString())-1)) * Integer.parseInt(qty.getText().toString());
                    price.setText(df.format(price_val));

                final DatabaseReference CART_REF = FirebaseDatabase.getInstance().getReference("/CurrentCart");
                final DatabaseReference CHECK_REF =
                        FirebaseDatabase.getInstance().getReference("/CurrentCart"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(name.getText().toString()).child("qty").setValue(Integer.parseInt(qty.getText().toString()));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                };
                CHECK_REF.child(name.getText().toString()).addListenerForSingleValueEvent(eventListener);

            });

            dec.setOnClickListener(v -> {
                qty.setText(String.valueOf(Integer.parseInt(qty.getText().toString()) - 1));
                DecimalFormat df = new DecimalFormat("#.00");
                Float price_val = (Float.parseFloat(price.getText().toString())/(Integer.parseInt(qty.getText().toString())+1)) * Integer.parseInt(qty.getText().toString());
                price.setText(df.format(price_val));

                final DatabaseReference CART_REF = FirebaseDatabase.getInstance().getReference("/CurrentCart");
                final DatabaseReference CHECK_REF =
                        FirebaseDatabase.getInstance().getReference("/CurrentCart"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(name.getText().toString()).child("qty").setValue(Integer.parseInt(qty.getText().toString()));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                };
                CHECK_REF.child(name.getText().toString()).addListenerForSingleValueEvent(eventListener);

                if(Integer.parseInt(qty.getText().toString()) == 0) {
                    int pos = getAdapterPosition();
                    removeAt(pos);

                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(name.getText().toString()).removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    };
                    CHECK_REF.child(name.getText().toString()).addListenerForSingleValueEvent(eventListener2);


                }
            });


        }

        private void removeAt(int position) {
            cartMealsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartMealsList.size());
        }


    }
}
