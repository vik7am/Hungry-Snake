package com.vikrant.hungrysnake;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity{

    ListView listView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        listView=findViewById(R.id.listView1);
        myAdapter= new MyAdapter(this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    static class MyAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, android.content.DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener {

        Context context;
        GameData gameData;
        int index;
        SeekBar seekBar,seekBar1,seekBar2,seekBar3;
        TextView textView,textView1,textView2,textView3;
        Button button;
        View view;
        int red,green,blue;
        int RGB;
        int SIZE_SPEED;
        AlertDialog.Builder builder;
        String [] label={"Difficulty","Color","Size","Speed","Head","Tail","Background"};
        String [] level={"Easy","Medium","Hard","Very Hard","Custom"};
        String [] color={"Red","Green","Blue","Custom"};

        public MyAdapter(Context context) {
            this.context=context;
            gameData=new GameData(context);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null) {
                convertView= LayoutInflater.from(context).inflate(R.layout.setting_row, parent,false);
            }
            ((TextView)convertView.findViewById(R.id.textView1)).setText(label[position]);
            ((TextView)convertView.findViewById(R.id.textView2)).setText(getData(position, convertView));
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDialog(position);
        }

        public String getData(int n,View v)
        {
            switch(n)
            {
                case 0: return ""+level[gameData.MODE];
                case 1: return ""+color[gameData.DEFAULT_COLOR];
                case 2: return ""+gameData.SIZE;
                case 3: return ""+gameData.SPEED;
                case 4: (v.findViewById(R.id.textView3)).setBackgroundColor(gameData.HRGB);
                        (v.findViewById(R.id.textView3)).setVisibility(View.VISIBLE);break;
                case 5: (v.findViewById(R.id.textView3)).setBackgroundColor(gameData.TRGB);
                        (v.findViewById(R.id.textView3)).setVisibility(View.VISIBLE);break;
                case 6: (v.findViewById(R.id.textView3)).setBackgroundColor(gameData.BRGB);
                        (v.findViewById(R.id.textView3)).setVisibility(View.VISIBLE);break;
            }
            return "";
        }
        @SuppressLint({"InflateParams", "SetTextI18n"})
        public void showDialog(int position) {
            index=position;
            builder=new AlertDialog.Builder(context);
            switch(position) {
                case 0:builder.setItems(level, this);  break;
                case 1:builder.setItems(color, this);  break;
                case 2: case 3:
                view=LayoutInflater.from(context).inflate(R.layout.dialog, null);
                seekBar= view.findViewById(R.id.seekBar);
                textView= view.findViewById(R.id.textView);
                seekBar.setOnSeekBarChangeListener(this);
                switch(position) {
                    case 2:SIZE_SPEED=gameData.SIZE; break;
                    case 3:SIZE_SPEED=gameData.SPEED; break;
                }
                seekBar.setProgress(SIZE_SPEED);
                textView.setText(""+SIZE_SPEED);
                builder.setPositiveButton("ok", this);
                builder.setNegativeButton("cancel", this);
                builder.setView(view); break;
                case 4: case 5: case 6:
                view=LayoutInflater.from(context).inflate(R.layout.color_palette, null);
                seekBar1= view.findViewById(R.id.seekBar1);
                seekBar2= view.findViewById(R.id.seekBar2);
                seekBar3= view.findViewById(R.id.seekBar3);
                seekBar1.setMax(255);seekBar2.setMax(255);seekBar3.setMax(255);
                textView1= view.findViewById(R.id.textView2);
                textView2= view.findViewById(R.id.textView4);
                textView3= view.findViewById(R.id.textView6);
                button= view.findViewById(R.id.button1);
                seekBar1.setOnSeekBarChangeListener(this);
                seekBar2.setOnSeekBarChangeListener(this);
                seekBar3.setOnSeekBarChangeListener(this);
                switch(position) {
                    case 4: RGB=gameData.HRGB; break;
                    case 5: RGB=gameData.TRGB; break;
                    case 6: RGB=gameData.BRGB; break;
                }
                red= Color.red(RGB);green=Color.green(RGB);blue=Color.blue(RGB);
                seekBar1.setProgress(red);
                seekBar2.setProgress(green);
                seekBar3.setProgress(blue);
                button.setBackgroundColor(RGB);
                builder.setPositiveButton("ok", this);
                builder.setNegativeButton("cancel", this);
                builder.setView(view); break;
            }
            builder.setTitle(label[position]);
            builder.show();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==-1) {
                switch(index) {
                    case 2: gameData.SIZE=SIZE_SPEED;  gameData.MODE=4;break;
                    case 3:	gameData.SPEED=SIZE_SPEED; gameData.MODE=4;break;
                    case 4: gameData.HRGB=Color.rgb(red, green, blue); gameData.DEFAULT_COLOR=3;break;
                    case 5: gameData.TRGB=Color.rgb(red, green, blue); gameData.DEFAULT_COLOR=3; break;
                    case 6: gameData.BRGB=Color.rgb(red, green, blue); gameData.DEFAULT_COLOR=3; break;
                }
                gameData.save();
            }
            else if(which==-2) {
                return;
            }
            else {
                switch(index) {
                    case 0:
                        switch(which) {
                            case 0:gameData.MODE=0; gameData.SPEED=20; break;
                            case 1:gameData.MODE=1; gameData.SPEED=15; break;
                            case 2:gameData.MODE=2; gameData.SPEED=10; break;
                            case 3:gameData.MODE=3; gameData.SPEED=5; break;
                            case 4:gameData.MODE=4;
                        }
                        gameData.SIZE=(gameData.DEVICE_WIDTH/100)*10;
                        break;
                    case 1:
                        switch(which) {
                            case 0:gameData.DEFAULT_COLOR=0; gameData.HRGB=Color.rgb(100,100,100); gameData.TRGB=Color.rgb(244,67,54); gameData.BRGB=Color.rgb(255,255,255); break;
                            case 1:gameData.DEFAULT_COLOR=1; gameData.HRGB=Color.rgb(100,100,100); gameData.TRGB=Color.rgb(0,150,136); gameData.BRGB=Color.rgb(255,255,255); break;
                            case 2:gameData.DEFAULT_COLOR=2; gameData.HRGB=Color.rgb(100,100,100); gameData.TRGB=Color.rgb(63,81,181); gameData.BRGB=Color.rgb(255,255,255); break;
                            case 3:gameData.DEFAULT_COLOR=3;  break;
                        }
                        break;
                }
                gameData.save();
            }
            notifyDataSetChanged();
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
            switch(seekbar.getId()) {
                case R.id.seekBar : if(progress==0) progress++; textView.setText(""+progress); SIZE_SPEED=progress; break;
                case R.id.seekBar1 : textView1.setText(""+progress); red=progress;
                    button.setBackgroundColor(Color.rgb(red, green, blue)); break;
                case R.id.seekBar2 : textView2.setText(""+progress); green=progress;
                    button.setBackgroundColor(Color.rgb(red, green, blue)); break;
                case R.id.seekBar3 : textView3.setText(""+progress); blue=progress;
                    button.setBackgroundColor(Color.rgb(red, green, blue)); break;
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}

