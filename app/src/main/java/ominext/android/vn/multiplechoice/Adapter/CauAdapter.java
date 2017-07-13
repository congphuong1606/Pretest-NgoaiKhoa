package ominext.android.vn.multiplechoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.multiplechoice.Model.CauHoi;
import ominext.android.vn.multiplechoice.R;

/**
 * Created by MyPC on 07/07/2017.
 */

public class CauAdapter extends ArrayAdapter<CauHoi> {

    public CauAdapter(Context context, ArrayList<CauHoi> caus) {
        super(context, 0, caus);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        CauHoi cau = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_menu_cau, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_menu_cau);
        int socau = position + 1;
        textView.setText("Câu:"+socau + "");

        return convertView;

    }

}
