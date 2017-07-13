package ominext.android.vn.multiplechoice.Activity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.multiplechoice.R;

public class CustomExamActivity extends AppCompatActivity {
    String hinhthucthi = "";

    @BindView(R.id.tv_chon_so_cau)
    TextView tvChonSoCau;
    @BindView(R.id.sb_chon_so_cau)
    SeekBar sbChonSoCau;
    @BindView(R.id.tv_thoi_gian)
    TextView tvThoiGian;
    @BindView(R.id.btn_batdauthi)
    Button btnBatdauthi;
    @BindView(R.id.tv_status_rdb)
    TextView tvStatusRdb;
    @BindView(R.id.rb_chay_ban)
    RadioButton rbChayBan;
    @BindView(R.id.rb_thi_giay)
    RadioButton rbThiGiay;
    @BindView(R.id.tv_status_sb)
    TextView tvStatusSb;
    private String typeQuery;
    private int socau = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        sbChonSoCau.setMax(50);
        sbChonSoCau.setProgress(socau);
        tvChonSoCau.setText(socau + " câu");
        upDateTime();


    }

    private void upDateTime() {
        SimpleDateFormat housFomart = new SimpleDateFormat("mm:ss");
        tvThoiGian.setText(housFomart.format(socau * 30 * 1000));
        sbChonSoCau.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SimpleDateFormat housFomart = new SimpleDateFormat("mm:ss");
                tvThoiGian.setText(housFomart.format(seekBar.getProgress() * 30 * 1000));
                tvChonSoCau.setText(seekBar.getProgress() + " câu");
                tvStatusSb.setVisibility(View.GONE);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick(R.id.btn_batdauthi)
    public void onViewClicked() {
        socau=sbChonSoCau.getProgress();
        if (socau!= 0) {
            if (hinhthucthi.equals("chayban") || hinhthucthi.equals("thigiay")) {
                Intent intent = new Intent(CustomExamActivity.this, QuizActivity.class);
                typeQuery = "SELECT * FROM Question ORDER BY RANDOM() LIMIT "+ socau;
                intent.putExtra("typeQuery", typeQuery);
                intent.putExtra("hinhthucthi", hinhthucthi);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                tvStatusRdb.setVisibility(View.VISIBLE);
            }
        } else {
            tvStatusSb.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rb_chay_ban, R.id.rb_thi_giay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_chay_ban:
                hinhthucthi = "chayban";
                tvStatusRdb.setVisibility(View.GONE);
                break;
            case R.id.rb_thi_giay:
                hinhthucthi = "thigiay";
                tvStatusRdb.setVisibility(View.GONE);
                break;
        }
    }
}
