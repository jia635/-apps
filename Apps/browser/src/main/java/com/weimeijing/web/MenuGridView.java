package com.weimeijing.web;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjie on 2016/10/22.
 */
public class MenuGridView extends LinearLayout {

    private GridView gridView;

    private int[] icon = {R.mipmap.ic_setting,R.mipmap.ic_night,R.mipmap.menu_share_right,R.mipmap.ic_bookmark, R.mipmap.ic_bookmark_add,
            R.mipmap.ic_history,R.mipmap.ic_photo,R.mipmap.no_cache_none,R.mipmap.ic_download,R.mipmap.ic_exit};
    private String[] iconName = {"设置","夜晚","分享","收藏网址","添加收藏","历史记录","图片","无痕模式","下载","退出"};

    private List<Map<String,Object>> datalist;

    private SimpleAdapter simpleAdapter;

    public MenuGridView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.menu_gridview,this);
        gridView = (GridView)findViewById(R.id.gridview);
        getData();
        String[] from = {"image","text"};
        int[] to = {R.id.item_image,R.id.item_text};
        simpleAdapter = new SimpleAdapter(this.getContext(),datalist,R.layout.item_menu,from,to);
        gridView.setAdapter(simpleAdapter);
    }
    public void getData(){
        datalist = new ArrayList<Map<String,Object>>();
        for(int i = 0;i<icon.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("image",icon[i]);
            map.put("text",iconName[i]);
            datalist.add(map);
        }
    }
}
