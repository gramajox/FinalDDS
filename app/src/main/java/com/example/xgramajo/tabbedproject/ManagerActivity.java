package com.example.xgramajo.tabbedproject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity implements CartaFragment.SendProducts{

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Log.d(TAG, "onCreate: Starting.");

        /**Toolbar*/
        Toolbar managerToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(managerToolbar);
        getSupportActionBar().setTitle("Resto App");
        getSupportActionBar().setSubtitle("Mesa actual: " + FirebaseController.tableNumber);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /**ESTO DICE EN QUE TAB ARRANCA LA ACTIVITY*/
        mViewPager.setCurrentItem(0);

    }

    private void setupViewPager(ViewPager viewPager) {
        mSectionsPageAdapter.addFragment(new CartaFragment(), "Carta");
        mSectionsPageAdapter.addFragment(new PedidosFragment(), "Pedidos");
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    /**Interfaz 1*/
    @Override
    public void setSelectedList(ArrayList<ProductClass> selectedList){

        PedidosFragment pedidosFragment = (PedidosFragment) mSectionsPageAdapter.getItem(1);

        pedidosFragment.setProducts(selectedList);

    }

    /**Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawer_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_LogOut:

                logOut();

                return true;

            case R.id.menu_home:

                Intent homeIntent = new Intent(ManagerActivity.this, HomeActivity.class);
                startActivity(homeIntent);

                return true;

            case R.id.menu_scan:

                Intent homeScanIntent = new Intent(ManagerActivity.this, HomeActivity.class);
                startActivity(homeScanIntent);

            default:
                return false;

        }
    }
        private void logOut() {

            LoginActivity.logOut();
            sendToLogin();
        }

    @Override
    protected void onStart() {
        super.onStart();

        if (LoginActivity.getCurrentUser() == null) {

            sendToLogin();

        }
    }
        private void sendToLogin() {

            Intent loginIntent = new Intent(ManagerActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }
}