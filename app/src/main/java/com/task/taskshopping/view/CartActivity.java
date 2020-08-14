package com.task.taskshopping.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.task.taskshopping.R;
import com.task.taskshopping.cart.Cart;
import com.task.taskshopping.cart.CartDB;
import com.task.taskshopping.item.ProductRoomDatabase;
import com.task.taskshopping.item.Productdb;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Productdb> items;
    private ArrayList<Productdb> selected = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private String mPhoneNumber;
    private CartDB mCartDb;
    private ProductRoomDatabase productRoomDatabase;
    private TextView mPriceText;

    private RelativeLayout palceorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        palceorder = findViewById(R.id.palceorder);
        selected = new ArrayList<>();
        items = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("cart");
        mRecyclerView = findViewById(R.id.cart_list);
        mPriceText = findViewById(R.id.price);
        mCartDb = Room.databaseBuilder(getApplicationContext(), CartDB.class, "sample-db").build();
        productRoomDatabase = Room.databaseBuilder(getApplicationContext(), ProductRoomDatabase.class, "sample").build();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemsAdapter adapter = new ItemsAdapter(items, getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        int price = 0;
        for (Productdb i : items) {
            String pricef = (i.getPrice().replace("₹", "").trim());
            String ppp = pricef.replace(",", "");

            double doubleValue = Double.parseDouble(ppp.trim());
            int intValue = (int) doubleValue;
            price += intValue * i.getCount();
            Log.e("priceof", ":" + price);
        }
        mPriceText.setText("₹ " + String.valueOf(price));


        palceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() == 0) {
                    Toast.makeText(CartActivity.this, "Add some items to checkout", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("Are you sure you want to checkout?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                 new InsertCartItem().execute();
                               //
                            })
                            .setNegativeButton(android.R.string.no, (dialog, which) -> {
                                dialog.cancel();
                            })
                            .show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertCartItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (Productdb i : items) {
                Cart c = new Cart();
                c.setName(i.getName());
                c.setDiscription(i.getDiscription());
                c.setPrice(i.getPrice());
                c.setCount(i.getCount());
                mCartDb.getCartDao().insert(c);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(CartActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            // CartActivity.this.finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateCartItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (Productdb i : selected) {
                Log.e("name", String.valueOf(i.getId()));
                Log.e("name", String.valueOf(i.getCount()));
               /* Productdb c = new Productdb();

                c.setName();
                c.setCount(i.getCount());*/
                productRoomDatabase.noteDao().update(i.getId(),i.getCount());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(CartActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            // CartActivity.this.finish();
        }
    }

    private class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
        private List<Productdb> item;
        Context context;

        ItemsAdapter(List<Productdb> itemList, Context context) {
            item = new ArrayList<>();
            this.item = itemList;
            this.context = context;
          //  selected = new ArrayList<>();
        }

        @NonNull
        @Override
        public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ItemsAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemsAdapter.ViewHolder holder, final int position) {
            final Productdb itm = item.get(position);
            holder.name.setText(itm.getName());
            holder.price.setText(String.valueOf(itm.getPrice()));
            holder.quantity.setText(String.valueOf(itm.getDiscription()));
            holder.count.setText(String.valueOf(itm.getCount()));


            holder.plus.setOnClickListener(v -> {
                int n = itm.getCount();
                if (n == 20) {
                    Toast.makeText(CartActivity.this, "Only select 20 item ", Toast.LENGTH_LONG).show();
                } else {

                    itm.setCount(itm.getCount() + 1);
                    holder.count.setText(String.valueOf(itm.getCount()));
                    if (!selected.contains(itm))
                        selected.add(itm);
                }

            });

            holder.remove.setOnClickListener(v -> {
                if (!(itm.getCount() <= 0))
                    itm.setCount(itm.getCount() - 1);
                if (itm.getCount() == 0) {
                    item.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    if (!selected.contains(itm))
                        selected.add(itm);
                } else holder.count.setText(String.valueOf(itm.getCount()));
            });

        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView name, price, quantity, count, add;
            private ImageView plus, remove, listimage;


            ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.food_name);
                price = itemView.findViewById(R.id.price_text);
                quantity = itemView.findViewById(R.id.discription_text);
                count = itemView.findViewById(R.id.cart_product_quantity_tv);
                add = itemView.findViewById(R.id.add);
                plus = itemView.findViewById(R.id.cart_plus_img);
                remove = itemView.findViewById(R.id.cart_minus_img);

            }
        }
    }
    @Override
    public void onBackPressed() {
        Log.e("check","check");
        new UpdateCartItem().execute();
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
