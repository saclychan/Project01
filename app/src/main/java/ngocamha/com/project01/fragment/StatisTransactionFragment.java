package ngocamha.com.project01.fragment;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ngocamha.com.project01.R;
import ngocamha.com.project01.activity.StatisTransactionActivity;
import ngocamha.com.project01.adapter.StatisAdapter;
import ngocamha.com.project01.database.SQLiteDatabase;
import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.model.ItemModelStatisTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisTransactionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    ArrayList<ItemModel> mData;
    Spinner spTypeTransaction;
    Spinner spTypeAccount;
    ListView lv;
    TextView mTvStartDate;
    TextView mTvEndDate;
    Calendar mcalendar =  Calendar.getInstance();
    SQLiteDatabase mSqLiteDatabase;
    ArrayList<Integer> mDataId;
    int mIdAccountSelected;
    ArrayList<String> dataTypeAccount;

    public StatisTransactionFragment() {
        // Required empty public constructor
    }

    public static StatisTransactionFragment newInstance(){
        StatisTransactionFragment statisTransactionFragment =  new StatisTransactionFragment();
        return statisTransactionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_statis_transaction, container, false);
        Toast.makeText(getActivity(), "Báo cáo Fragment", Toast.LENGTH_SHORT).show();
        initView(view);
        setupTypeTransaction();
        setupTypeAccount();
        setupListReport();

        return view;
    }


    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv);
        spTypeAccount = (Spinner) view.findViewById(R.id.sp_type_account);
        spTypeTransaction = (Spinner) view.findViewById(R.id.sp_type_transaction);
        mTvStartDate = (TextView) view.findViewById(R.id.tv_start_date);
        mTvStartDate.setOnClickListener(this);
        mTvEndDate = (TextView) view.findViewById(R.id.tv_end_date);
        mTvEndDate.setOnClickListener(this);

    }

    private void setupListReport() {
        ArrayList<ItemModelStatisTransaction> data = new ArrayList<ItemModelStatisTransaction>();

        String startDate = mTvStartDate.getText().toString();
        String endDate = mTvEndDate.getText().toString();


        //sql get transaction
        //SELECT * FROM tbl_transaction where account_id =2 and action_date_time between '2017-06-20 10' and '2017-06-30'  and type = 1
        String sqlGetTransaction = "SELECT * FROM tbl_transaction  ";

        Cursor cursor = mSqLiteDatabase.rawQuery(sqlGetTransaction);
        if(cursor != null && cursor.moveToFirst()){
            do {
                String  transactionTime = cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_ACTION_TIME));
                String transactionReason =  cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_REASON));
                int transactionMoney =   cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_MONEY));
                int transactionRemainMoney  = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_REMAIN_MONEY));
                int transactionType = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_TYPE ));
                int accountId   = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_TRANSACTION_ACCOUNT_ID));
                //String accountName = dataTypeAccount.get();
                String strAccountName = transactionType==1?"Thu":"Chi";
                ItemModelStatisTransaction item1 = new ItemModelStatisTransaction(transactionTime, transactionReason, transactionMoney, transactionRemainMoney, "Ví điện tử"+accountId, strAccountName);
                data.add(item1);
            }while(cursor.moveToNext());


        }


        ItemModelStatisTransaction item2 = new ItemModelStatisTransaction("19/12/2017", "Trả nợ", 10000000, 1000000, "Ví điện tử", "Chi");
        data.add(item2);

        StatisAdapter adapter = new StatisAdapter(getContext(), data);
        lv.setAdapter(adapter);
    }

    private void setupTypeAccount() {
        dataTypeAccount =  new ArrayList<>();
        mSqLiteDatabase =  new SQLiteDatabase(getActivity());
        String sql_get_account  = "SELECT * FROM "+SQLiteDatabase.TABLE_ACCOUNT;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql_get_account);
        mDataId  = new ArrayList<>();
        dataTypeAccount.add("Chọn tài khoản");
        if(cursor != null && cursor.moveToFirst()){
            do{
                String accountName = cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_NAME));
                int accountId = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_ID));
                dataTypeAccount.add(accountName);
                Log.d("StatisTranscaction","id selected "+mIdAccountSelected);
                mDataId.add(accountId);
            }while(cursor.moveToNext());
        }
        /*dataTypeAccount.add("Tien mat");
        dataTypeAccount.add("Tiet kiem");
        dataTypeAccount.add("Tin dung");*/

        ArrayAdapter<String> spadapter  = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dataTypeAccount);
        spTypeAccount.setAdapter(spadapter);
    }

    private void setupTypeTransaction() {
        ArrayList<String> dataTypeTransaction  = new ArrayList<>();
        dataTypeTransaction.add("Chon loại giao dịch");
        dataTypeTransaction.add("Thu");
        dataTypeTransaction.add("Chi");
        ArrayAdapter<String> adapterTypeTransaction =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,dataTypeTransaction );
        spTypeTransaction.setAdapter(adapterTypeTransaction);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mTvStartDate.setText(i2+ "/"+ (i1+1) + "/" + i);
        }
    };

    DatePickerDialog.OnDateSetListener onTimeSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mTvEndDate.setText(i2+ "/"+ (i1+1) + "/" + i);
        }
    };
    public void onClick(View view) {
        int year = mcalendar.get(Calendar.YEAR);
        int month = mcalendar.get(Calendar.MONTH) + 1;
        int day = mcalendar.get(Calendar.DAY_OF_MONTH);

        switch (view.getId()){
            case R.id.tv_start_date:
                new DatePickerDialog(getContext(), onDateSetListener, year, month, day ).show();
                break;

            case R.id.tv_end_date:
                new DatePickerDialog(getContext(), onTimeSetListener2, year, month, day).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mIdAccountSelected = mDataId.get(i);

        Toast.makeText(getActivity(), "item selected "+mIdAccountSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
