package ngocamha.com.project01.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ngocamha.com.project01.adapter.CustomAdapter;
import ngocamha.com.project01.fragment.AccountManagerFragment;
import ngocamha.com.project01.fragment.AddTransactionFragment;
import ngocamha.com.project01.fragment.HomeFragment;
import ngocamha.com.project01.fragment.StatisTransactionFragment;
import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.R;

public class MainActivity extends AppCompatActivity  implements HomeFragment.OnAddTransactionListener,
        HomeFragment.OnStatisTransactionListener, HomeFragment.OnAccountManagerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager homeFragmentManager =  getSupportFragmentManager();
        FragmentTransaction homeFragmentTransaction = homeFragmentManager.beginTransaction();
        HomeFragment homeFragment =  HomeFragment.newInstance();
        homeFragmentTransaction.replace(R.id.main_framelayout, homeFragment, "HomeFragment");
        homeFragmentTransaction.commit();

    }


    @Override
    public  void onAddTransaction() {
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();

        AddTransactionFragment addTransactionFragment =  AddTransactionFragment.newInstance();
        fragmentTransaction.replace(R.id.main_framelayout , addTransactionFragment);
        fragmentTransaction.addToBackStack("ADdTransaction");
        fragmentTransaction.commit();
    }

    @Override
    public void onStatisTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatisTransactionFragment statisTransactionFragment  = StatisTransactionFragment.newInstance();
        fragmentTransaction.replace(R.id.main_framelayout, statisTransactionFragment);
        fragmentTransaction.addToBackStack("StatisTransaction");
        fragmentTransaction.commit();
    }


    @Override
    public void onAccountManager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AccountManagerFragment accountManagerFragment = AccountManagerFragment.newInstance();
        HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentByTag("HomeFragment"); //gọi tag
        fragmentTransaction.hide(homeFragment); //ẩn theo tag
        fragmentTransaction.add(R.id.main_framelayout, accountManagerFragment); //add cái frag ment mới
        fragmentTransaction.addToBackStack("onAccountManager");
        fragmentTransaction.commit();

    }
}
