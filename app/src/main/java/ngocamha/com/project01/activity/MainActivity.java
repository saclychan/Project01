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
        homeFragmentTransaction.replace(R.id.main_framelayout, homeFragment);
        homeFragmentTransaction.commit();

    }

    private void showAboutDialog(){
        final Dialog dialog =  new Dialog(MainActivity.this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater =  LayoutInflater.from(MainActivity.this);
        View view = layoutInflater.inflate(R.layout.item_custom_dialog, null, false);
        Button btnClose = (Button) view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);

        dialog.show();
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
        fragmentTransaction.replace(R.id.main_framelayout, accountManagerFragment);
        fragmentTransaction.addToBackStack("onAccountManager");
        fragmentTransaction.commit();

    }
}
