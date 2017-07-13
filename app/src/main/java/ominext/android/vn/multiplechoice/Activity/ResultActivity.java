package ominext.android.vn.multiplechoice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.multiplechoice.Adapter.KetQuaAdapter;
import ominext.android.vn.multiplechoice.Model.CauHoi;
import ominext.android.vn.multiplechoice.R;

public class ResultActivity extends Activity {
    @BindView(R.id.ratingbar1)
    RatingBar ratingBar1;
    @BindView(R.id.textResult)
    TextView textResult;
    @BindView(R.id.recycleview_ketqua)
    RecyclerView recycleviewKetqua;
    ArrayList<CauHoi> cauHois;
    KetQuaAdapter ketQuaAdapter;
    @BindView(R.id.tv_socaudung)
    TextView tvSocaudung;
    @BindView(R.id.tv_socausai)
    TextView tvSocausai;
    @BindView(R.id.btn_xem_lai)
    Button btnXemLai;
    private RecyclerView.LayoutManager layoutManager;
    private int indexTrue = 0;
    private int index;
    int b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        cauHois = new ArrayList<>();
        cauHois = (ArrayList<CauHoi>) getIntent().getSerializableExtra("Arraylist");
        index = cauHois.size();
        for (CauHoi cauHoi : cauHois) {
            if (cauHoi.getMluaChon() == cauHoi.getmDapAn()) {
                indexTrue++;
            }
        }
        layoutManager = new GridLayoutManager(this, 1);
        recycleviewKetqua.setLayoutManager(layoutManager);
        recycleviewKetqua.setHasFixedSize(true);
        ketQuaAdapter = new KetQuaAdapter(cauHois, this, R.layout.item_ketqua_layout);
        recycleviewKetqua.setAdapter(ketQuaAdapter);
        setB();
        setRating();
        tvSocaudung.setText(indexTrue+"");
        tvSocausai.setText((index-indexTrue)+"");

    }

    private void setRating() {
        ratingBar1.setNumStars(5);
        ratingBar1.setStepSize(0.2f);
        ratingBar1.setRating(b);
        switch (b) {
            case 0:
                textResult.setText("Chúc bạn may mắn lần sau, kết quả của bạn là " + indexTrue + "/" + index + " câu");
            case 1:
                textResult.setText("Chúc bạn may mắn lần sau, kết quả của bạn là " + indexTrue + "/" + index + " câu");
            case 2:
                textResult.setText("Đừng bỏ cuộc hãy thử lại, kết quả của bạn là " + indexTrue + "/" + index + " câu");
                break;
            case 3:
            case 4:
                textResult.setText("Hmmmm.Bạn cũng được, kết quả của bạn là " + indexTrue + "/" + index + " câu");
                break;
            case 5:
                textResult.setText("Bạn làm rất tốt, kết quả của bạn là " + indexTrue + "/" + index + " câu");
                break;
        }
    }

    private void setB() {
        float in = (float) index;
        float intru = (float) indexTrue;
        float a = intru / in;
        if (a > 0 && a <= 0.2) {
            b = 1;
        } else if (a > 0.2 && a <= 0.4) {
            b = 2;
        } else if (a > 0.4 && a <= 0.6) {
            b = 3;
        } else if (a > 0.6 && a <= 0.8) {
            b = 4;
        } else if (a > 0.8 && a <= 1) {
            b = 5;
        }
    }

    @OnClick(R.id.btn_xem_lai)
    public void onViewClicked() {
        Intent intent = new Intent(ResultActivity.this, ShowKetQuaActivity.class);
        intent.putExtra("Arraylist", cauHois);
        startActivity(intent);
        finish();
    }
}
