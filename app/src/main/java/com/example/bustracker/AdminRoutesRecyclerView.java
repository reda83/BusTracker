package com.example.bustracker;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AdminRoutesRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    String location;
    Context context = this;
    ArrayList<Route> routesList = new ArrayList<>();
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_routes_recycler_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(tabListener);
        this.selectedFragment = new GoingFragment(context);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
//        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.w("test", "onCreateOptionsMenu: ");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (this.selectedFragment != null && this.selectedFragment instanceof IOnMainMenuEventListener) {

            int id = item.getItemId();
            if (id == R.id.add_line) {
                ((IOnMainMenuEventListener) this.selectedFragment).onAddClicked();
                return true;
            } else {
                ((IOnMainMenuEventListener) this.selectedFragment).onRemoveClicked();
                return true;
            }
        }

        return false;
    }

    TabLayout.OnTabSelectedListener tabListener =
            new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    switch (tab.getPosition()) {
                        case 0:
                            selectedFragment = new GoingFragment(context);
                            break;
                        case 1:
                            selectedFragment = new ReturningFragment(context);
                            break;
//                        case R.id.nav_search:
//                            selectedFragment = new SearchFragment();
//                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {


                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };


}

