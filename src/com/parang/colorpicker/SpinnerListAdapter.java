package com.parang.colorpicker;


import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerListAdapter extends BaseAdapter {
	 
    private List<KeyValue> listData;
 
    private LayoutInflater layoutInflater;
    public boolean useResources = true;
    
    
    public SpinnerListAdapter(Context context, List<KeyValue> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
 
    @Override
    public int getCount() {
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.spinner_row, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            if(useResources)
            	holder.titleTextView.setText(listData.get(position).getText());
            else
            	holder.titleTextView.setText(listData.get(position).getText());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
 
	        if(useResources)
	        	holder.titleTextView.setText(listData.get(position).getText());
	        else
	        	holder.titleTextView.setText(listData.get(position).getText());
        }
        
        return convertView;
    }
 
    static class ViewHolder {
        TextView titleTextView;
    }
 
}