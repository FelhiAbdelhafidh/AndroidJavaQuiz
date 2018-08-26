package com.example.felhi.javaquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;


import com.example.felhi.javaquiz.adapter.AdapterGridShopCategory;
import com.example.felhi.javaquiz.adapter.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterGridShopCategory mAdapter;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent_view = findViewById(R.id.parent_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, dpToPx(this, 8), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<ShopCategory> items = getShoppingCategory(this);

        //set data and list adapter
        mAdapter = new AdapterGridShopCategory(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopCategory.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ShopCategory obj, int position) {
                startActivity(new Intent(MainActivity.this, GenericActivity.class));
               // Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
    public static List<ShopCategory> getShoppingCategory(Context ctx) {
        List<ShopCategory> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_category_icon);
        TypedArray drw_arr_bg = ctx.getResources().obtainTypedArray(R.array.shop_category_bg);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_category_title);
        String brief_arr[] = ctx.getResources().getStringArray(R.array.shop_category_brief);
        for (int i = 0; i < drw_arr.length(); i++) {
            ShopCategory obj = new ShopCategory();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.image_bg = drw_arr_bg.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.brief = brief_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
