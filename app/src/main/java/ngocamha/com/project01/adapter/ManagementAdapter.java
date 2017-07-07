package ngocamha.com.project01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.R;

/**
 * Created by PL on 6/25/2017.
 */

public class ManagementAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<ItemModel> mData;
    private LayoutInflater mlayoutInflater;


    public ManagementAdapter(Context mContext, ArrayList<ItemModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mlayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view ==  null){
            viewHolder = new ViewHolder();
            view =  mlayoutInflater.inflate(R.layout.item_account,viewGroup, false );
            viewHolder.tv = (TextView) view.findViewById(R.id.tv_account_name);
            viewHolder.btn  = (Button)  view.findViewById(R.id.btn_delete_account);
            viewHolder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.tv_account_id  = (TextView) view.findViewById(R.id.tv_account_id);
            view.setTag(viewHolder);
        }else{
            viewHolder  = (ViewHolder) view.getTag();
        }

        ItemModel oneItem = mData.get(i);
        viewHolder.tv.setText(oneItem.getTxtAccount());
        DecimalFormat decimalFormat =  new DecimalFormat("###,###");
        String strPrice = decimalFormat.format(oneItem.getPrice());
        viewHolder.tv_price.setText(strPrice);
        viewHolder.tv_account_id.setText(""+oneItem.getId());

        return view;
    }

    public static class ViewHolder{
         TextView tv_account_id;
         TextView tv;
         TextView tv_price;
         Button btn;
    }
}
