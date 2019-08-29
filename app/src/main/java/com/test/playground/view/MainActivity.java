package com.test.playground.view;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.test.playground.adapters.DataAdapter;
import com.test.playground.api.RequestInterface;
import com.test.playground.models.ITunes;
import com.test.playground.models.Results;
import com.test.playground.utils.AppUtils;
import com.test.playground.utils.Constants;
import com.test.playground.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Results> mArrayList;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    /*
     * Initialize UI components of the Main Activity Layout
     * */
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dpToPx(10, MainActivity.this), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    /*
     *  Load data from API after click on search button of soft keypad
     * */
    private void loadJSON(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ITunes> call = request.getJSON(term);
        call.enqueue(new Callback<ITunes>() {
            @Override
            public void onResponse(Call<ITunes> call, Response<ITunes> response) {

                ITunes jsonResponse = response.body();
                mArrayList = new ArrayList<Results>(Arrays.asList(jsonResponse.getResults()));
                mAdapter = new DataAdapter(mArrayList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ITunes> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
     *  This function used when user put something in search box.
     * */
    private void search(final SearchView searchView) {

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (AppUtils.isNetworkConnected(MainActivity.this)) {
                    loadJSON(s);
                } else {
                    Toast.makeText(MainActivity.this, "No internet!!", Toast.LENGTH_LONG).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }


}
