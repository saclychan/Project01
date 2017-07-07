package ngocamha.com.project01.fragment;


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

import java.util.ArrayList;

import ngocamha.com.project01.R;
import ngocamha.com.project01.adapter.ManagementAdapter;
import ngocamha.com.project01.database.SQLiteDatabase;
import ngocamha.com.project01.model.ItemModel;

/**
 * Created by PL on 7/6/2017.
 */

public class AccountManagerFragment extends Fragment {
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
    private SQLiteDatabase mSqLiteDatabase;
    private ArrayList<ItemModel> mData;
    private ManagementAdapter mManagementAdapter;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_account_management, container, false);



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
