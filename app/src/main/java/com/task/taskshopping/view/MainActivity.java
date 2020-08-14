package com.task.taskshopping.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.task.taskshopping.R;
import com.task.taskshopping.item.ProductRoomDatabase;
import com.task.taskshopping.item.Productdb;
import com.task.taskshopping.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    private static ProductRoomDatabase mItemDB;
    private List<Productdb> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ArrayList<Productdb> selected = new ArrayList<>();

    private RelativeLayout cartview;
    private TextView select_count;

    //ProductAdapter productAdapter;
    ArrayList<String> spciallist = new ArrayList<>();
    List<String> pricelist = new ArrayList<>();

    private ProductViewModel productViewModel;
    Toolbar toolbar;
    ArrayList<Productdb> item1 = (ArrayList<Productdb>) selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        cartview = findViewById(R.id.cartview);
        select_count = findViewById(R.id.select_count);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        toolbar.setSubtitle("");


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Productdb>>() {
            @Override
            public void onChanged(List<Productdb> notes) {
                //) loading.show();
                Log.e("notes", String.valueOf(notes));
                ItemsAdapter adapter = new ItemsAdapter(notes, getApplicationContext());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });
        cartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Productdb> item1 = (ArrayList<Productdb>) selected;
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                Bundle b = new Bundle();
                b.putParcelableArrayList("cart", item1);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_one) {

            Toast.makeText(this, "Share Item ", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_two) {
            Toast.makeText(this, "Notice Item", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
        private List<Productdb> item;
        private Context mcontext;

        ItemsAdapter(List<Productdb> itemList, Context context) {
            item = new ArrayList<>();
            selected = new ArrayList<>();
            this.item = itemList;
            this.mcontext = context;
        }

        @NonNull
        @Override
        public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemsAdapter.ViewHolder holder, int position) {
            final Productdb itm = item.get(position);
            holder.name.setText(itm.getName());
            holder.price.setText(itm.getPrice());
            holder.quantity.setText(itm.getDiscription());
            holder.count.setText(String.valueOf(itm.getCount()));


            if(itm.getCount()==0){

                holder.add.setVisibility(View.VISIBLE);
            }else {
                holder.add.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.VISIBLE);
            }


            holder.add.setOnClickListener((v) -> {
                holder.add.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.VISIBLE);
                cartview.setVisibility(View.VISIBLE);
                itm.setCount(itm.getCount() + 1);
                holder.count.setText(String.valueOf(itm.getCount()));
                if (!selected.contains(itm))
                    selected.add(itm);

            });
            holder.plus.setOnClickListener(v -> {
                int n = itm.getCount();
                if (n == 20) {
                    Toast.makeText(mcontext, "Only select 20 item ", Toast.LENGTH_LONG).show();
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
                holder.count.setText(String.valueOf(itm.getCount()));
                if (itm.getCount() == 0) {
                    selected.remove(itm);
                    holder.linearLayout.setVisibility(View.GONE);
                    holder.add.setVisibility(View.VISIBLE);
                }
            });
            for (Productdb i : selected) {


                int intValue = i.getCount();
                Log.e("priceof", ":" + intValue);
                select_count.setText(intValue);
            }

            // loading.dismiss();
        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView name, price, quantity, count, add;
            private ImageView plus, remove, listimage;
            private LinearLayout linearLayout;

            ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.food_name);
                price = itemView.findViewById(R.id.price_text);
                quantity = itemView.findViewById(R.id.discription_text);
                count = itemView.findViewById(R.id.cart_product_quantity_tv);
                add = itemView.findViewById(R.id.add);
                plus = itemView.findViewById(R.id.cart_plus_img);
                remove = itemView.findViewById(R.id.cart_minus_img);
                linearLayout = itemView.findViewById(R.id.addlay);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productViewModel.deleteAll();

    }
    @Override
    public void onResume(){
        super.onResume();
        productViewModel.getAllProduct().observe(this, new Observer<List<Productdb>>() {
            @Override
            public void onChanged(List<Productdb> notes) {
                //) loading.show();
                Log.e("notes1", String.valueOf(notes));
                ItemsAdapter adapter = new ItemsAdapter(notes, getApplicationContext());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
