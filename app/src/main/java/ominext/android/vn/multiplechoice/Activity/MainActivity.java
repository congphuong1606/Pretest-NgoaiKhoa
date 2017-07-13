package ominext.android.vn.multiplechoice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.multiplechoice.R;

public class MainActivity extends AppCompatActivity {
String typeQuery;
    @BindView(R.id.btn_tuchon)
    Button btnTuchon;
    @BindView(R.id.btn_chonchuong)
    Button btnChonchuong;
    @BindView(R.id.btn_luyentap)
    Button btnLuyentap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_tuchon, R.id.btn_chonchuong, R.id.btn_luyentap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tuchon:
                Intent ie=new Intent(MainActivity.this, CustomExamActivity.class);
                startActivity(ie);
                break;
            case R.id.btn_chonchuong:
                Intent i=new Intent(MainActivity.this, ListChapterActivity.class);
                startActivity(i);
                break;
            case R.id.btn_luyentap:

                Intent intent=new Intent(MainActivity.this, QuizActivity.class);
                typeQuery = "select * from Question ORDER BY RANDOM() LIMIT 100";
                intent.putExtra("typeQuery", typeQuery);
                startActivity(intent);
                break;
        }
    }
}
