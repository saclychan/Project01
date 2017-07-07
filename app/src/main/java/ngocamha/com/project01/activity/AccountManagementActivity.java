package ngocamha.com.project01.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.adapter.ManagementAdapter;
import ngocamha.com.project01.R;

public class AccountManagementActivity extends AppCompatActivity {

    ArrayList<ItemModel> mData;

    ManagementAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        mData =  new ArrayList<>();
        ItemModel item1 =  new ItemModel("Tien mat", 50000);
        mData.add(item1);

        ItemModel item2 =  new ItemModel("The Tin dung", 400000);
        mData.add(item2);

        ItemModel item3 = new ItemModel("Tiet Kiem", 10000);
        mData.add(item3);

        ListView lv =  (ListView) findViewById(R.id.lv);
        adapter  = new ManagementAdapter(AccountManagementActivity.this, mData);

        lv.setAdapter(adapter);
    }
}
