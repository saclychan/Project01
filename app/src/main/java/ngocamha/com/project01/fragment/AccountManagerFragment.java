package ngocamha.com.project01.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ngocamha.com.project01.R;
import ngocamha.com.project01.adapter.ManagementAdapter;
import ngocamha.com.project01.database.SQLiteDatabase;
import ngocamha.com.project01.model.ItemModel;

/**
 * Created by PL on 7/6/2017.
 */

public class AccountManagerFragment extends Fragment {
    //BƯỚC 1 : TAOj action
    public static final String ACTION_ADD_ACCOUNT =  "ngocamha.com.project01.fragment.ACTION_ADD_ACCOUNT";

    public AccountManagerFragment(){

    }

    public static AccountManagerFragment  newInstance(){
        AccountManagerFragment accountManagerFragment = new AccountManagerFragment();
        return accountManagerFragment;
    }

    private ListView mLv;
    private EditText mEditAddAccount;
    private EditText mEditAddPrice;
    private Button mBtnAdd;

    private ArrayList<ItemModel> mData;
    private ManagementAdapter mManagementAdapter;
    private SQLiteDatabase mSqLiteDatabase;


    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_account_management, container, false);
        mBtnAdd = (Button) mView.findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "btn xoa click", Toast.LENGTH_LONG).show();

            }
        });

        intView();
        ArrayList<ItemModel> data = getAccounts();

        mManagementAdapter =  new ManagementAdapter(getActivity(), data);
        mLv.setAdapter(mManagementAdapter);
        return mView;
    }

    private void intView() {
        mLv = (ListView) mView.findViewById(R.id.lv);
        mEditAddAccount = (EditText) mView.findViewById(R.id.edtAddAccount);
        mEditAddPrice = (EditText) mView.findViewById(R.id.edtAddPrice);
        mBtnAdd = (Button) mView.findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save database
                String accountName = mEditAddAccount.getText().toString();
                int price = Integer.parseInt(mEditAddPrice.getText().toString() );
                mSqLiteDatabase  = new SQLiteDatabase(getActivity());

                ContentValues values = new ContentValues();
                values.put(SQLiteDatabase.COLUMN_ACCOUNT_NAME, accountName);
                values.put(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE, price);
                Toast.makeText(getActivity(), "Add to database", Toast.LENGTH_SHORT).show();
                mSqLiteDatabase.insertAccount(values);

                //add vào data của listview để cập nhật lên giao diện
                ItemModel itemnew  = new ItemModel(accountName, price);
                mData.add(itemnew);
                mManagementAdapter.notifyDataSetChanged();
                mEditAddAccount.setText("");
                mEditAddPrice.setText("");

                sendActionAddAccount(itemnew, "add");
            }
        });
    }

    private void sendActionAddAccount(ItemModel item, String type) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ADD_ACCOUNT);


        intent.putExtra("newItem", item);
        intent.putExtra("type", type);
        getActivity().sendBroadcast(intent);
    }


    public ArrayList<ItemModel> getAccounts() {
        String sqlGetAccounts  = "SELECT * FROM "+ SQLiteDatabase.TABLE_ACCOUNT;
        mSqLiteDatabase = new SQLiteDatabase(getActivity());
        Cursor cursor = mSqLiteDatabase.rawQuery(sqlGetAccounts);
        mData = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()){
            do{
                String strAccountName =  cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_NAME));
                int intPrice = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE));
                int intAccountId = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_ID));
                ItemModel item1 = new ItemModel(strAccountName, intPrice, intAccountId);
                mData.add(item1);
            }while(cursor.moveToNext());
        }

        return mData;
    }
}
