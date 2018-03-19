package com.example.xgramajo.tabbedproject;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity implements CartaFragment.SendProducts{

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    /*Drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /**ESTO DICE EN QUE TAB ARRANCA LA ACTIVITY*/
        mViewPager.setCurrentItem(1);

    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    private void setupViewPager(ViewPager viewPager) {
        mSectionsPageAdapter.addFragment(new PromoFragment(), "Promo");
        mSectionsPageAdapter.addFragment(new CartaFragment(), "Carta");
        mSectionsPageAdapter.addFragment(new PedidosFragment(), "Pedidos");
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    /**ACA MANDA LA CARTA LA LISTA SELECCIONADA (selectedList)*/
    @Override
    public void setSelectedList(ArrayList<ProductClass> selectedList){

        PedidosFragment pedidosFragment = (PedidosFragment) mSectionsPageAdapter.getItem(2);

        pedidosFragment.setProducts(selectedList);

    }
}