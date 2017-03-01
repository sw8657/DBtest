package com.eslee.dbtest;

import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.eslee.dbtest.database.DataBases;
import com.eslee.dbtest.database.DbOpenHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView mDetailListView;
    private ListViewAdapter mAdapter;

    private EditText mTxtTitle;
    private EditText mTxtPointValue;
    private EditText mTxtPointDetail;
    private Button mSelectButton;
    private Button mInsertButton;
    private Button mUpdateButton;
    private Button mDeleteButton;

    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;

    private void setLayout(){
        mTxtTitle = (EditText)  findViewById(R.id.txtUseWhere); // 제목, 사용처
        mTxtPointValue = (EditText)  findViewById(R.id.txtPointValue); // 포인트
        mTxtPointDetail = (EditText)  findViewById(R.id.txtUseType); // 포인트 상세정보 적립, 사용
        mSelectButton = (Button) findViewById(R.id.btn_search); // 검색
        mInsertButton = (Button) findViewById(R.id.btn_insert); // 입력
        mUpdateButton = (Button) findViewById(R.id.btn_update); // 수정
        mDeleteButton = (Button) findViewById(R.id.btn_delete); // 삭제

        mDetailListView = (ListView) findViewById(R.id.myinfo_details_listview); // 사용내역
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout(); // 컨트롤

        // DB연결
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

//        mDbOpenHelper.insertUserInfo("유재석","youjs@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("강호동","kang@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("김민석","kim@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("지성","js@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("피고인","pigo@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("재밌다","funny@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("프리즌","prison@gmail.com","일반");
//        mDbOpenHelper.insertUserInfo("브레이크","break@gmail.com","일반");

        mAdapter = new ListViewAdapter();
        mDetailListView.setAdapter(mAdapter);

//        mAdapter.addItem("주말이벤트","2017.02.11 15:28",1000,"이벤트");
//        mAdapter.addItem("스타벅스", "2017.02.05 10:43",-500,"사용");
//        mAdapter.addItem("걷기","2017.02.04 19:00",500,"걷기적립");
//        mAdapter.addItem("걷기","2017.02.03 19:00",450,"걷기적립");
//        mAdapter.addItem("버거킹","2017.02.01 12:20",-400,"사용");

        cursorToAdapter();

        mTxtPointDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = new String[]{"사용","적립"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("사용타입을 선택하세요")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedIndex[0] = i;
                                if(mTxtPointDetail!=null){
                                    mTxtPointDetail.setText(items[selectedIndex[0]]);
                                }
                            }
                        }).create().show();
            }
        });


        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursorToAdapter();
            }
        });

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userid = 1;
                String usetype = "사용";
                int usepoint = 1000;
                String useWhere = "주말이벤트";
                String location = "x,y";

                usetype = mTxtPointDetail.getText().toString().trim();
                usepoint = Integer.parseInt(mTxtPointValue.getText().toString());
                useWhere = mTxtTitle.getText().toString().trim();

                mDbOpenHelper.insertPoint(userid,usetype,usepoint,useWhere,location);

                cursorToAdapter();
            }
        });

        mDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListViewItem listViewItem = (ListViewItem)view.getTag();
                String msg = String.valueOf(listViewItem.getId()) + " " + listViewItem.getTitle() + " " + String.valueOf(listViewItem.getPoint()) + " " + listViewItem.getDateTime();
                Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cursorToAdapter(){
        int id = -1;
        String sTitle = "";
        String sUseDate = "";
        int sPoint = 0;
        String sUseDetail = "";

        mAdapter.Clear();

        mCursor =  null;
        mCursor = mDbOpenHelper.getAllPoint();

        while (mCursor.moveToNext()){
            id = mCursor.getInt(mCursor.getColumnIndex(DataBases.PointTable._ID));
            sTitle = mCursor.getString(mCursor.getColumnIndex(DataBases.PointTable.USE_WHERE));
            sUseDate = mCursor.getString(mCursor.getColumnIndex(DataBases.PointTable.USE_DATE));
            sPoint = mCursor.getInt(mCursor.getColumnIndex(DataBases.PointTable.USE_POINT));
            sUseDetail = mCursor.getString(mCursor.getColumnIndex(DataBases.PointTable.USE_TYPE));

            mAdapter.addItem(id,sTitle,sUseDate,sPoint,sUseDetail);
        }
        mCursor.close();
        mAdapter.notifyDataSetChanged();
    }


}
