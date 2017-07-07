package ngocamha.com.project01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ngocamha.com.project01.model.ItemModel;
import ngocamha.com.project01.R;

/**
 * Created by PL on 6/21/2017.
 */

public class CustomAdapter extends BaseAdapter{
    private Context mcontext;
    private LayoutInflater mlayoutInflater;
    private ArrayList<ItemModel> itemModel;

    public CustomAdapter(Context mcontext, ArrayList<ItemModel> itemModel) {
        this.mcontext = mcontext;
        this.itemModel = itemModel;
        mlayoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return itemModel.size();
    }

    @Override
    public Object getItem(int i) {
        return itemModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder =  new ViewHolder();
            view  = mlayoutInflater.inflate(R.layout.item_list_account, viewGroup, false);
            viewHolder.txtAccountName = (TextView) view.findViewById(R.id.txtAccountName);
            viewHolder.txtPrice  = (TextView) view.findViewById(R.id.txtPrice);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ItemModel oneItem = itemModel.get(i);
        viewHolder.txtAccountName.setText(oneItem.getTxtAccount());
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(oneItem.getPrice()) ;
        viewHolder.txtPrice.setText( price );
        return view;
    }


    public static class ViewHolder{
        TextView txtAccountName;
        TextView txtPrice;
    }
}
