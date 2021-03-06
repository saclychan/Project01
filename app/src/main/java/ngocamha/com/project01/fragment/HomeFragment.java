package ngocamha.com.project01.fragment;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ngocamha.com.project01.R;
import ngocamha.com.project01.activity.MainActivity;
import ngocamha.com.project01.adapter.CustomAdapter;
import ngocamha.com.project01.adapter.ManagementAdapter;
import ngocamha.com.project01.database.SQLiteDatabase;
import ngocamha.com.project01.model.ItemModel;



/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = HomeFragment.class.getName();
    ArrayList<ItemModel> mData;
    CustomAdapter adapter;
    OnAddTransactionListener onAddTransactionListener;
    OnStatisTransactionListener onStatisTransactionListener;
    OnAccountManagerListener onAccountManagerListener;
    private SQLiteDatabase mSqLiteDatabase;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAddTransactionListener  = (OnAddTransactionListener) context;
        onStatisTransactionListener =  (OnStatisTransactionListener) context;
        onAccountManagerListener = (OnAccountManagerListener) context;
    }

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //lang nghe su kien add_account
        registerBroadcastAddAccount();
        registerBroadcastChangePrice();



        Toast.makeText(getContext(), "HomeFragment onCreateView", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ListView lv = (ListView) view.findViewById(R.id.lv);
        mSqLiteDatabase =  new SQLiteDatabase(getContext());
        String sqlCheck = "SELECT * FROM "+SQLiteDatabase.TABLE_ACCOUNT  ;
        Cursor cursorCheck = mSqLiteDatabase.rawQuery(sqlCheck);
        if(  !(cursorCheck != null && cursorCheck.moveToFirst() && cursorCheck.getColumnCount() > 0) ){
            ContentValues values =  new ContentValues();
            values.put(SQLiteDatabase.COLUMN_ACCOUNT_NAME, "Tien mat");
            values.put(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE, 100000);
            values.put(SQLiteDatabase.COLUMN_ACCOUNT_CREATED_AT, "2017-07-06 10:15:20");
            values.put(SQLiteDatabase.COLUMN_ACCOUNT_UPDATED_AT, "2017-07-06 10:15:20");
            mSqLiteDatabase.insertAccount(values);

            cursorCheck.close();
        }
        String sql = "SELECT * FROM "+SQLiteDatabase.TABLE_ACCOUNT ;
        Cursor cursor =  mSqLiteDatabase.rawQuery(sql);

        mData =  new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()){
            do{
                String accountName = cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_NAME));
                int accountMoney = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE));
                int id = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_ID));
                Log.d("HomeFragment", accountName);
                ItemModel item1 = new ItemModel(accountName, accountMoney,id);
                mData.add(item1);
            }while (cursor.moveToNext());
            cursor.close();
        }


        /*ItemModel item1 = new ItemModel("Tien Mat", 500000);
        mData.add(item1);

        ItemModel item2 =  new ItemModel("Thẻ tín dụng", 1000000);
        mData.add(item2);

        ItemModel item3 = new ItemModel("Tiết kiểm", 200000);
        mData.add( item3 );*/

        adapter = new CustomAdapter(getContext(), mData);
        lv.setAdapter(adapter);

        Button accountManagement = (Button) view.findViewById(R.id.account_management);
        accountManagement.setOnClickListener(this);

        Button addTransaction = (Button) view.findViewById(R.id.add_transaction);
        addTransaction.setOnClickListener(this);

        Button reportTransaction = (Button) view.findViewById(R.id.report_transaction);
        reportTransaction.setOnClickListener(this);

        Button about = (Button) view.findViewById(R.id.btn_about);
        about.setOnClickListener(this);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterBroadcastAddAccount();
        unregisterBroadcastChangePrice();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action  = intent.getAction();
            if(AccountManagerFragment.ACTION_ADD_ACCOUNT.equals(action)){

                String type = intent.getStringExtra("type");
                if(type.equals( "add" ) ){
                    ItemModel itemReceiver  = (ItemModel) intent.getSerializableExtra("newItem");
                    mData.add(itemReceiver);
                    adapter.notifyDataSetChanged();
                } else if (type.equals("remove")){
                    int accountId = Integer.parseInt(intent.getStringExtra(ManagementAdapter.KEY_ACTION_DELETE));
                    Log.d(TAG, "accountID=" + accountId);
                    for (ItemModel item : mData ) {
                        int tmpId = item.getId();
                        Log.d(TAG, "tmpId=" + tmpId);
                        if (tmpId == accountId){
                            mData.remove(item);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
    };

    BroadcastReceiver broadcastReceiver1  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(AddTransactionFragment.ACTION_ADD_TRANSACTION.equals(action)){
                int accountKey = intent.getIntExtra(AddTransactionFragment.KEY_ID, 0);
                int newPrice = intent.getIntExtra(AddTransactionFragment.KEY_PRICE, 0);
                for(ItemModel item : mData){
                    int tmpId = item.getId();
                    if(tmpId == accountKey){
                        item.setPrice(newPrice);
                    }
                }
            }
        }
    };

    private void registerBroadcastChangePrice(){
        IntentFilter intentFilter =  new IntentFilter();
        intentFilter.addAction(AddTransactionFragment.ACTION_ADD_TRANSACTION);
        getActivity().registerReceiver(broadcastReceiver1, intentFilter);
    }

    private void unregisterBroadcastChangePrice() {
        getActivity().unregisterReceiver(broadcastReceiver1);
    }

    private void registerBroadcastAddAccount(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AccountManagerFragment.ACTION_ADD_ACCOUNT);
        // registerRecerver phair ger Activity
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastAddAccount() {
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.account_management:

                /*Intent intentAddAccount  =  new Intent(MainActivity.this, AccountManagementActivity.class);
                startActivity(intentAddAccount);*/
                onAccountManagerListener.onAccountManager();
                break;
            case R.id.add_transaction:
                /*Intent intentAddTransaction = new Intent(MainActivity.this, AddTransction.class);
                startActivity(intentAddTransaction);*/
                onAddTransactionListener.onAddTransaction();
                break;
            case R.id.report_transaction:
                onStatisTransactionListener.onStatisTransaction();
                /*Intent intentReportTransaction = new Intent(MainActivity.this, StatisTransactionActivity.class);
                startActivity(intentReportTransaction);*/
                break;

            case R.id.btn_about:
                showAboutDialog();
                break;



        }
    }

    public interface OnAddTransactionListener{
        void onAddTransaction();
    }

    public interface OnStatisTransactionListener{
        void onStatisTransaction();
    }

    public interface OnAccountManagerListener{
        void onAccountManager();
    }

    private void showAboutDialog(){
        final Dialog dialog =  new Dialog(getActivity());
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater =  LayoutInflater.from(getActivity());
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
}
