package ominext.android.vn.multiplechoice.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.multiplechoice.Model.CauHoi;
import ominext.android.vn.multiplechoice.R;

/**
 * Created by MyPC on 13/07/2017.
 */

public class KetQuaAdapter extends RecyclerView.Adapter<KetQuaAdapter.ViewHolder> {
    ArrayList<CauHoi> cauHois = new ArrayList<>();

    public KetQuaAdapter(ArrayList<CauHoi> cauHois, Context context, int layout) {
        this.cauHois = cauHois;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ketqua_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CauHoi cauHoi = cauHois.get(position);
        if(cauHoi.getmDapAn()==cauHoi.getMluaChon()){
            holder.imv_failed.setVisibility(View.GONE);
            holder.imv_true.setVisibility(View.VISIBLE);
        }else {
            holder.imv_failed.setVisibility(View.VISIBLE);
            holder.imv_true.setVisibility(View.GONE);
        }
        int a=position+1;
        holder.textView.setText("Câu:"+a);

    }

    @Override
    public int getItemCount() {
        return cauHois.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imv_true;
        ImageView imv_failed;

        public ViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_cau_ketqua);
            imv_failed = (ImageView) itemView.findViewById(R.id.imv_failed_ketqua);
            imv_true = (ImageView) itemView.findViewById(R.id.imv_true_ketqua);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}