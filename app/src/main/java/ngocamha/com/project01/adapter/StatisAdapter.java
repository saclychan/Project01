package ngocamha.com.project01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ngocamha.com.project01.R;
import ngocamha.com.project01.model.ItemModelStatisTransaction;

/**
 * Created by PL on 6/25/2017.
 */

public class StatisAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<ItemModelStatisTransaction> mData;
    private LayoutInflater mLayoutInflater;
    public StatisAdapter(Context mContext, ArrayList<ItemModelStatisTransaction> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
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
            view = mLayoutInflater.inflate(R.layout.item_statis_transaction, viewGroup, false);

            viewHolder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.tvReason = (TextView) view.findViewById(R.id.tv_reason);
            viewHolder.tvPrice  = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.tvBalance = (TextView) view.findViewById(R.id.tv_balance);
            viewHolder.tvTypeAcc  = (TextView) view.findViewById(R.id.tv_type_acc);
            viewHolder.tvTypeTransaction = (TextView) view.findViewById(R.id.tv_type_transaction);
            view.setTag(viewHolder);
        }else{
             viewHolder = (ViewHolder) view.getTag();
        }
        ItemModelStatisTransaction oneItem =  mData.get(i);
        viewHolder.tvDate.setText(oneItem.getDateTime());
        viewHolder.tvReason.setText(oneItem.getReason());
        viewHolder.tvPrice.setText(""+oneItem.getPrice());
        viewHolder.tvBalance.setText(""+oneItem.getBalance());
        viewHolder.tvTypeAcc.setText(oneItem.getTypeAccount());
        viewHolder.tvTypeTransaction.setText(oneItem.getTypeTransaction());
        return view;
    }

    public static class ViewHolder{
        TextView tvDate;
        TextView tvReason;
        TextView tvPrice;
        TextView tvBalance;
        TextView tvTypeAcc;
        TextView tvTypeTransaction;
    }
}
