package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.myapplication.view.ColorPicker;
import com.example.administrator.myapplication.view.TextStickerView;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private ImageView backgroundImage;
    private EditText text;
    private Bitmap sourBitmap;
    private GridView bgGv;
    private int[] backgrounds = {R.drawable.sour_pic1, R.drawable.sour_pic2, R.drawable.sour_pic3};
    private BaseAdapter gvAdapter;
    private TextStickerView mTextStickerView;
    private ColorPicker mColorPicker;
    private ImageView mTextColorSelector;
    private int mTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdapter();
        initView();
    }

    private void initAdapter() {
        //背景图的适配器
        gvAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return backgrounds.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder vh = null;
                if (convertView == null) {
                    vh=new ViewHolder();
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_background, null);
                    vh.bg = convertView.findViewById(R.id.background_img);
                    convertView.setTag(vh);
                } else {
                    vh = (ViewHolder) convertView.getTag();
                }
                vh.bg.setImageResource(backgrounds[position]);
                return convertView;
            }
        };
    }

    private class ViewHolder {
        private ImageView bg;
    }

    private void initView() {
        backgroundImage = (ImageView) findViewById(R.id.wartermark_pic);
        text = (EditText) findViewById(R.id.text);
        bgGv = (GridView) findViewById(R.id.bg_gv);
        mTextStickerView = (TextStickerView)findViewById(R.id.text_sticker_panel);
        mTextColorSelector = (ImageView)findViewById(R.id.text_color);
        text.addTextChangedListener(this);
        mTextStickerView.setEditText(text);
        mColorPicker = new ColorPicker(this, 255, 0, 0);
        mTextColorSelector.setOnClickListener(new SelectColorBtnClick());
        //统一颜色设置
        mTextColorSelector.setBackgroundColor(mColorPicker.getColor());
        mTextStickerView.setTextColor(mColorPicker.getColor());
        bgGv.setAdapter(gvAdapter);
        sourBitmap = BitmapFactory.decodeResource(getResources(), backgrounds[0]);
        backgroundImage.setImageBitmap(sourBitmap);
        bgGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sourBitmap = BitmapFactory.decodeResource(getResources(),backgrounds[position]);
                backgroundImage.setImageBitmap(sourBitmap);
            }
        });
    }
    @Override
    public void afterTextChanged(Editable s) {
        //mTextStickerView change
        String text = s.toString().trim();
        mTextStickerView.setText(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    /**
     * 颜色选择 按钮点击
     */
    private final class SelectColorBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mColorPicker.show();
            Button okColor = (Button) mColorPicker.findViewById(R.id.okColorButton);
            okColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTextColor(mColorPicker.getColor());
                    mColorPicker.dismiss();
                }
            });
        }
    }//end inner class

    /**
     * 修改字体颜色
     *
     * @param newColor
     */
    private void changeTextColor(int newColor) {
        this.mTextColor = newColor;
        mTextColorSelector.setBackgroundColor(mTextColor);
        mTextStickerView.setTextColor(mTextColor);
    }


}
