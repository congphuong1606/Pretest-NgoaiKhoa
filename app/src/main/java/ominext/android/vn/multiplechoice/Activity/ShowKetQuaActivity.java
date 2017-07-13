package ominext.android.vn.multiplechoice.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ominext.android.vn.multiplechoice.Adapter.CauAdapter;
import ominext.android.vn.multiplechoice.Model.CauHoi;
import ominext.android.vn.multiplechoice.R;

public class ShowKetQuaActivity extends AppCompatActivity {

    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.tv_giaithich)
    TextView tvGiaithich;
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.drawer_view)
    LinearLayout drawerView;
    ArrayList<CauHoi> cauHois;
    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    @BindView(R.id.mydrawer)
    DrawerLayout mydrawer;
    @BindView(R.id.tva)
    TextView tva;
    @BindView(R.id.tvb)
    TextView tvb;
    @BindView(R.id.tvc)
    TextView tvc;
    @BindView(R.id.tvd)
    TextView tvd;
    @BindView(R.id.tve)
    TextView tve;
    @BindView(R.id.imv_status_a)
    ImageView imvStatusA;
    @BindView(R.id.imv_status_b)
    ImageView imvStatusB;
    @BindView(R.id.imv_status_c)
    ImageView imvStatusC;
    @BindView(R.id.imv_status_d)
    ImageView imvStatusD;
    @BindView(R.id.imv_status_e)
    ImageView imvStatusE;
    @BindView(R.id.tv_socau)
    TextView tvSocau;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    int i = 0;
    private int luaChon;
    private int dapan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ket_qua);
        ButterKnife.bind(this);
        cauHois = new ArrayList<>();
        cauHois = (ArrayList<CauHoi>) getIntent().getSerializableExtra("Arraylist");
        CauAdapter adapter = new CauAdapter(this, cauHois);
        lvMenu.setAdapter(adapter);
        setQuestionView();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mydrawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                contentLayout.setTranslationX(slideOffset * drawerView.getWidth());
                mydrawer.bringChildToFront(drawerView);
                mydrawer.requestLayout();
            }
        };
        mydrawer.setDrawerListener(actionBarDrawerToggle);

        lvMenu.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    lvMenu.bringToFront();
                    mydrawer.requestLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                i = position;
                setQuestionView();
            }
        });

    }

    private void setQuestionView() {
        imvStatusA.setBackground(getDrawable(R.drawable.ic_dot));
        imvStatusB.setBackground(getDrawable(R.drawable.ic_dot));
        imvStatusC.setBackground(getDrawable(R.drawable.ic_dot));
        imvStatusD.setBackground(getDrawable(R.drawable.ic_dot));
        imvStatusE.setBackground(getDrawable(R.drawable.ic_dot));
        tva.setTextSize(14);
        tvb.setTextSize(14);
        tvc.setTextSize(14);
        tvd.setTextSize(14);
        tve.setTextSize(14);

        tva.setTextColor(getResources().getColor(R.color.b));
        tvb.setTextColor(getResources().getColor(R.color.b));
        tvc.setTextColor(getResources().getColor(R.color.b));
        tvd.setTextColor(getResources().getColor(R.color.b));
        tve.setTextColor(getResources().getColor(R.color.b));
        CauHoi cauHoi = cauHois.get(i);
        tvQuestion.setText(cauHoi.getmCauHoi());
        tva.setText(cauHoi.getmCauA());
        tvb.setText(cauHoi.getmCauB());
        tvc.setText(cauHoi.getmCauC());
        tvd.setText(cauHoi.getmCauD());
        tve.setText(cauHoi.getmCauE());
        luaChon = cauHoi.getMluaChon();
        dapan = cauHoi.getmDapAn();
        tvGiaithich.setText(cauHoi.getmGiaiThich());
        tvSocau.setText((i+1)+"/"+cauHois.size());
        setChecked();
    }

    private void setChecked() {

        if (luaChon == 1) {
            imvStatusA.setBackground(getDrawable(R.drawable.ic_failed));
            tva.setTextColor(getResources().getColor(R.color.sai));

        } else if (luaChon == 2) {
            imvStatusB.setBackground(getDrawable(R.drawable.ic_failed));
            tvb.setTextColor(getResources().getColor(R.color.sai));

        } else if (luaChon == 3) {
            imvStatusC.setBackground(getDrawable(R.drawable.ic_failed));
            tvc.setTextColor(getResources().getColor(R.color.sai));

        } else if (luaChon == 4) {
            imvStatusD.setBackground(getDrawable(R.drawable.ic_failed));
            tvd.setTextColor(getResources().getColor(R.color.sai));

        } else if (luaChon == 5) {
            imvStatusE.setBackground(getDrawable(R.drawable.ic_failed));
            tve.setTextColor(getResources().getColor(R.color.sai));
        }
        if (dapan == 1) {
            tva.setTextSize(18);
            imvStatusA.setBackground(getDrawable(R.drawable.ic_tick));
            tva.setTextColor(getResources().getColor(R.color.dung));

        } else if (dapan == 2) {
            tvb.setTextSize(18);
            imvStatusB.setBackground(getDrawable(R.drawable.ic_tick));
            tvb.setTextColor(getResources().getColor(R.color.dung));

        } else if (dapan == 3) {
            tvc.setTextSize(18);
            imvStatusC.setBackground(getDrawable(R.drawable.ic_tick));
            tvc.setTextColor(getResources().getColor(R.color.dung));

        } else if (dapan == 4) {
            tvd.setTextSize(18);
            imvStatusD.setBackground(getDrawable(R.drawable.ic_tick));
            tvd.setTextColor(getResources().getColor(R.color.dung));

        } else if (dapan == 5) {
            tve.setTextSize(18);
            imvStatusE.setBackground(getDrawable(R.drawable.ic_tick));
            tve.setTextColor(getResources().getColor(R.color.dung));
        }
    }

}
