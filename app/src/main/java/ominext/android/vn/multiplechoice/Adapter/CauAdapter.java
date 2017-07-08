package ominext.android.vn.multiplechoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.multiplechoice.Model.Cau;
import ominext.android.vn.multiplechoice.R;

/**
 * Created by MyPC on 07/07/2017.
 */

public class CauAdapter extends ArrayAdapter<Cau> {

    public CauAdapter(Context context, ArrayList<Cau> caus) {
        super(context, 0, caus);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        Cau cau = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_menu_cau, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_menu_cau);

        textView.setText(cau.getTitle());

        return convertView;

    }

}
