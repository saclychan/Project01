package ngocamha.com.project01.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ngocamha.com.project01.R;
import ngocamha.com.project01.activity.AddTransction;
import ngocamha.com.project01.database.SQLiteDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransactionFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    public static final String ACTION_ADD_TRANSACTION = "ngocamha.com.project01.fragment.ACTION_ADD_TRANSACTION";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ID  = "transaction_id";
    TextView tvDate;
    TextView tvTime;
    Spinner sp;
    Calendar calendar = Calendar.getInstance();
    SQLiteDatabase mSqLiteDatabase;
    Button mBtnSave;
    RadioGroup mGr;
    RadioButton rd_expenditure;
    int mIdSelected;
    ArrayList<Integer> mDataId;
    Button btnSaveClose;

    TextView mEdtPrice;
    TextView mEdtReason;
    int mTypeTransaction; //1 là thu, -1 là chi



    public AddTransactionFragment() {
        // Required empty public constructor
    }

    public static AddTransactionFragment newInstance(){
        AddTransactionFragment addTransactionFragment =  new AddTransactionFragment();
        return  addTransactionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_add_transaction, container, false);

        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
        sp  = (Spinner) view.findViewById(R.id.sp);
        sp.setOnItemSelectedListener(this);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
        ArrayList<String> data =  new ArrayList<String>();
        mEdtPrice = (TextView) view.findViewById(R.id.edtPrice);
        mEdtReason  = (TextView) view.findViewById(R.id.edt_reason);
        mDataId = new ArrayList<>();
        mGr = (RadioGroup) view.findViewById(R.id.gr);
        mGr.setOnCheckedChangeListener(this);

        btnSaveClose = (Button) view.findViewById(R.id.btn_save_close);
        btnSaveClose.setOnClickListener(this);
        String sql = "SELECT * FROM "+SQLiteDatabase.TABLE_ACCOUNT ;
        mSqLiteDatabase  = new SQLiteDatabase(getActivity());
        Cursor cursor =  mSqLiteDatabase.rawQuery(sql);
        if(cursor != null && cursor.moveToFirst()){
            do{
                String itemAcc = cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_NAME));
                int itemId = cursor.getInt(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_ID));
                mDataId.add(itemId);

                data.add(itemAcc);
            }while(cursor.moveToNext());
            Toast.makeText(getActivity(), sql, Toast.LENGTH_SHORT).show();
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, data);

        sp.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvDate:

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), onDateSetListener, year, month, day).show();
                break;

            case R.id.tvTime:
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true).show();
                break;

            case R.id.btn_save:
                //get Data to save
                insertTransaction();
                break;
            case R.id.btn_save_close:
                insertTransaction();
                getFragmentManager().popBackStack(); //đóng lại fragment này
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            int month = i1 + 1;
            tvDate.setText(i2 + "/" + month + "/" + i);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            tvTime.setText(i + ":" + i1);
        }
    };



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if(i == R.id.rd_expenditure){//tiêu
            mTypeTransaction =  -1;
        }else if (i == R.id.rd_revenue){
            mTypeTransaction =  1;
        }
    }


    private  void insertTransaction(){
        int statusOk = 1;
        ArrayList<String> arrMess = new ArrayList<>();
        String txtPrice = mEdtPrice.getText().toString();
        int intPrice = 0;
        if(!txtPrice.equals("")){
            intPrice = Integer.parseInt(txtPrice);
        }
        String txtReason = mEdtReason.getText().toString();
        String txtDate =  tvDate.getText().toString();
        String txtTime = tvTime.getText().toString();
        String [] arrDate = txtDate.split("/");
        String [] arrTime = txtTime.split(":");
        String actionTime = arrDate[2]+"-"+arrDate[1]+"-"+arrDate[0]+ " "+ arrTime[0]+":"+arrTime[1];



        int intOldPrice = 0;
        Cursor cursor = mSqLiteDatabase.query(SQLiteDatabase.TABLE_ACCOUNT, new String []{"*"}, SQLiteDatabase.COLUMN_ACCOUNT_ID + "=?", new String [] {""+mIdSelected}, null, null , null );
        if(cursor != null && cursor.moveToFirst()){
            do {
                String txtOldPrice = cursor.getString(cursor.getColumnIndex(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE));
                intOldPrice = Integer.parseInt(txtOldPrice);
            }while(cursor.moveToNext());
        }

        int newPrice =  intOldPrice + mTypeTransaction * intPrice;
        if(newPrice < 0){
            statusOk = 0;
        }else{
            //statusOk = 1;
            arrMess.add ("Âm cmnr");
        }

        if(intPrice < 0){
            statusOk = 0;
        }else{
            arrMess.add ("Khong duoc bo trong price");
        }

        if(txtReason.equals("")){
            statusOk = 0;
        }else{
            arrMess.add ("Khong duoc bo trong ly do");
        }

        int checkRes = 0;
        if(statusOk == 1){
            ContentValues values =  new ContentValues();
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_MONEY, txtPrice);
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_REASON, txtReason);
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_TYPE, mTypeTransaction);
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_ACTION_TIME, actionTime);
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_ACCOUNT_ID, mIdSelected);
            values.put(SQLiteDatabase.COLUMN_TRANSACTION_REMAIN_MONEY, newPrice);

            mSqLiteDatabase.insertTransaction(values);

            ContentValues accValues =  new ContentValues();
            accValues.put(SQLiteDatabase.COLUMN_ACCOUNT_BALANCE,newPrice );
            String [] whereId = {""+mIdSelected};
            checkRes = mSqLiteDatabase.updateAccount(accValues, whereId);
        }else{

        }

        if(checkRes != 0){
            Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
            sendActionChangePriceToHomeFragment(newPrice, mIdSelected);
        }else{
            String messText =  "";
            for(String item : arrMess){
                messText += item + " ";
            }
            Toast.makeText(getActivity(), "Thất bại vì lý do: "+messText, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendActionChangePriceToHomeFragment(int newPrice, int mIdSelected) {
        Intent intent = new Intent();
        intent.setAction(AddTransactionFragment.ACTION_ADD_TRANSACTION);
        intent.putExtra(KEY_PRICE,newPrice );
        intent.putExtra(KEY_ID, mIdSelected);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mIdSelected = mDataId.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
