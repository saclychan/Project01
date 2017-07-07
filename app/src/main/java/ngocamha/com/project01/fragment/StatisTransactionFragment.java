package ngocamha.com.project01.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ngocamha.com.project01.R;
import ngocamha.com.project01.activity.StatisTransactionActivity;
import ngocamha.com.project01.adapter.StatisAdapter;
import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.model.ItemModelStatisTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisTransactionFragment extends Fragment implements View.OnClickListener{

    ArrayList<ItemModel> mData;
    Spinner spTypeTransaction;
    Spinner spTypeAccount;
    ListView lv;
    TextView mTvStartDate;
    TextView mTvEndDate;
    Calendar mcalendar =  Calendar.getInstance();


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
        ItemModelStatisTransaction item1 = new ItemModelStatisTransaction("19/12/2017", "Trả nợ", 10000000, 1000000, "Ví điện tử", "Chi");
        data.add(item1);

        StatisAdapter adapter = new StatisAdapter(getContext(), data);
        lv.setAdapter(adapter);
    }

    private void setupTypeAccount() {
        ArrayList<String> dataTypeAccount =  new ArrayList<>();
        dataTypeAccount.add("Tien mat");
        dataTypeAccount.add("Tiet kiem");
        dataTypeAccount.add("Tin dung");

        ArrayAdapter<String> spadapter  = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dataTypeAccount);
        spTypeAccount.setAdapter(spadapter);
    }

    private void setupTypeTransaction() {
        ArrayList<String> dataTypeTransaction  = new ArrayList<>();
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

}
