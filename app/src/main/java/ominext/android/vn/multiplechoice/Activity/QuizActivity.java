package ominext.android.vn.multiplechoice.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.multiplechoice.Adapter.CauAdapter;
import ominext.android.vn.multiplechoice.Database.Database;
import ominext.android.vn.multiplechoice.Model.Cau;
import ominext.android.vn.multiplechoice.Model.CauHoi;
import ominext.android.vn.multiplechoice.R;

public class QuizActivity extends AppCompatActivity {
    static final String DATABASE_NAME = "NgoaiKhoaPreTest.sqlite";
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<CauHoi> cauHoiList = new ArrayList<>();
    ArrayList<Cau> caus = new ArrayList<Cau>();
    @BindView(R.id.rb_aq)
    RadioButton rbAq;
    @BindView(R.id.rb_bq)
    RadioButton rbBq;
    @BindView(R.id.rb_cq)
    RadioButton rbCq;
    @BindView(R.id.rb_dq)
    RadioButton rbDq;
    @BindView(R.id.rb_eq)
    RadioButton rbEq;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private String giaithich;
    private int luaChon;
    private float lastTranslate = 0.0f;
    private int answer = 0;
    private int idCauhoi;
    private int soChuong;
    private int score = 0;
    private int i = 0;
    private int s;
    private int time = 30;
    private int prosetbar = 30;
    private int cout = 0;
    private String typeQuery = "";
    private Handler handler;
    private Timer timer;
    int dem = 0;
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.mydrawer)
    DrawerLayout mydrawer;
    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_question)
    TextView tvQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        typeQuery = (String) bundle.get("typeQuery");
        readCauhois();
        setQuestionView();
        startGame();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1000, 1000);
        CauAdapter adapter = new CauAdapter(this, cauHoiList);
        lvMenu.setAdapter(adapter);
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

    private void readCauhois() {
        SQLiteDatabase db = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(typeQuery, null);
        cauHoiList.clear();
        cout = cursor.getCount();
        if (cursor != null && cout > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int idCauhoi = cursor.getInt(0);
                    int soChuong = cursor.getInt(1);
                    String question = cursor.getString(2);
                    int dapan = cursor.getInt(3);
                    String a = cursor.getString(4);
                    String b = cursor.getString(5);
                    String c = cursor.getString(6);
                    String d = cursor.getString(7);
                    String e = cursor.getString(8);
                    String giathich = cursor.getString(10);
                    int luaChon = 10;
                    cauHoiList.add(new CauHoi(idCauhoi, soChuong, question, dapan,
                            a, b, c, d, e, giathich, luaChon));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
    }



    public void startGame() {
        handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == 1 && time >= 0) {
                    progressbar.setProgress(prosetbar);
                    tvTime.setText(time + "s");
                    prosetbar--;
                    time--;

                } else {
                    setQuestionView();
                }


            }
        };
    }

    private void setQuestionView() {
        time = 30;
        prosetbar = 30;
        if (i >= cout) {
            return;
        }
        CauHoi cauHoi = cauHoiList.get(i);
        soChuong = cauHoi.getmSoChuong();
        idCauhoi = cauHoi.getmId();
        tvQuestion.setText(cauHoi.getmCauHoi());
        rbAq.setText(cauHoi.getmCauA());
        rbBq.setText(cauHoi.getmCauB());
        rbCq.setText(cauHoi.getmCauC());
        rbDq.setText(cauHoi.getmCauD());
        rbEq.setText(cauHoi.getmCauE());
        luaChon = cauHoi.getMluaChon();
        s = cauHoi.getmDapAn();
        tvIndex.setText((i + 1 )+ "/"+cout);
        setChecked();
        i++;
        if (i > 1) {
            tvBack.setVisibility(View.VISIBLE);
        } else {
            tvBack.setVisibility(View.GONE);
        }
    }

    private void backQuestion() {
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        i--;
        i--;
        setQuestionView();

    }

    public void nextQuestion() {
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        if (s == answer) {
            score++;
        }
        if (i < cout) {
            setQuestionView();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("Arraylist", cauHoiList);
            startActivity(intent);
            finish();
        }
    }

    private void upDateAnswer() {
        int number = answer;
        CauHoi cauHoi = cauHoiList.get(i - 1);
        cauHoi.setMluaChon(number);
    }


    private void setChecked() {

        if (luaChon == 1) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbAq.getId());
            rbAq.setTextColor(getResources().getColor(R.color.sai));
            rbBq.setTextColor(getResources().getColor(R.color.b));
            rbCq.setTextColor(getResources().getColor(R.color.b));
            rbDq.setTextColor(getResources().getColor(R.color.b));
            rbEq.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 2) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbBq.getId());
            rbAq.setTextColor(getResources().getColor(R.color.b));
            rbBq.setTextColor(getResources().getColor(R.color.sai));
            rbCq.setTextColor(getResources().getColor(R.color.b));
            rbDq.setTextColor(getResources().getColor(R.color.b));
            rbEq.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 3) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbCq.getId());
            rbAq.setTextColor(getResources().getColor(R.color.b));
            rbBq.setTextColor(getResources().getColor(R.color.b));
            rbCq.setTextColor(getResources().getColor(R.color.sai));
            rbDq.setTextColor(getResources().getColor(R.color.b));
            rbEq.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 4) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbDq.getId());
            rbAq.setTextColor(getResources().getColor(R.color.b));
            rbBq.setTextColor(getResources().getColor(R.color.b));
            rbCq.setTextColor(getResources().getColor(R.color.b));
            rbDq.setTextColor(getResources().getColor(R.color.sai));
            rbEq.setTextColor(getResources().getColor(R.color.b));
        } else if (luaChon == 5) {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.check(rbEq.getId());
            rbAq.setTextColor(getResources().getColor(R.color.b));
            rbBq.setTextColor(getResources().getColor(R.color.b));
            rbCq.setTextColor(getResources().getColor(R.color.b));
            rbDq.setTextColor(getResources().getColor(R.color.b));
            rbEq.setTextColor(getResources().getColor(R.color.sai));
        } else {
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            radioGroup1.clearCheck();
            rbAq.setTextColor(getResources().getColor(R.color.b));
            rbBq.setTextColor(getResources().getColor(R.color.b));
            rbCq.setTextColor(getResources().getColor(R.color.b));
            rbDq.setTextColor(getResources().getColor(R.color.b));
            rbEq.setTextColor(getResources().getColor(R.color.b));
        }
    }


    @OnClick({R.id.rb_aq, R.id.rb_bq, R.id.rb_cq, R.id.rb_dq, R.id.rb_eq, R.id.tv_next, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_aq:
                answer = 1;
                rbAq.setTextColor(getResources().getColor(R.color.sai));
                rbBq.setTextColor(getResources().getColor(R.color.b));
                rbCq.setTextColor(getResources().getColor(R.color.b));
                rbDq.setTextColor(getResources().getColor(R.color.b));
                rbEq.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();
                break;
            case R.id.rb_bq:
                answer = 2;
                rbAq.setTextColor(getResources().getColor(R.color.b));
                rbBq.setTextColor(getResources().getColor(R.color.sai));
                rbCq.setTextColor(getResources().getColor(R.color.b));
                rbDq.setTextColor(getResources().getColor(R.color.b));
                rbEq.setTextColor(getResources().getColor(R.color.b));

                upDateAnswer();
                break;
            case R.id.rb_cq:
                answer = 3;
                rbAq.setTextColor(getResources().getColor(R.color.b));
                rbBq.setTextColor(getResources().getColor(R.color.b));
                rbCq.setTextColor(getResources().getColor(R.color.sai));
                rbDq.setTextColor(getResources().getColor(R.color.b));
                rbEq.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();
                break;
            case R.id.rb_dq:
                answer = 4;
                rbAq.setTextColor(getResources().getColor(R.color.b));
                rbBq.setTextColor(getResources().getColor(R.color.b));
                rbCq.setTextColor(getResources().getColor(R.color.b));
                rbDq.setTextColor(getResources().getColor(R.color.sai));
                rbEq.setTextColor(getResources().getColor(R.color.b));
                upDateAnswer();

                break;
            case R.id.rb_eq:
                answer = 5;
                rbAq.setTextColor(getResources().getColor(R.color.b));
                rbBq.setTextColor(getResources().getColor(R.color.b));
                rbCq.setTextColor(getResources().getColor(R.color.b));
                rbDq.setTextColor(getResources().getColor(R.color.b));
                rbEq.setTextColor(getResources().getColor(R.color.sai));
                upDateAnswer();
                break;
            case R.id.tv_next:
                nextQuestion();
                break;
            case R.id.tv_back:
                backQuestion();
                break;
        }
    }

}
