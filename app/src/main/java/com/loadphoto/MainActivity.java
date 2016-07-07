package com.loadphoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final GridView gridView = (GridView) findViewById(R.id.gridview);

        LoadUtil.getPhotoLoader(this, new ILoadFinish<LoadPhoto.ImageFolder>() {
            @Override
            public void finish(List<LoadPhoto.ImageFolder> datas) {
                gridView.setAdapter(new MyAdapter( datas));
            }
        });

        LoadUtil.getContacts(this, new ILoadFinish<LoadContacts.ContactPeople>() {
            @Override
            public void finish(List<LoadContacts.ContactPeople> datas) {
                Log.v("fag", "-------------> " + datas.size());
            }
        });

    }

    /*
        自定义的gridView适配器
     */
    class MyAdapter extends BaseAdapter{

        private List<LoadPhoto.ImageFolder> folderList;

        private ArrayList<String> images;

        public MyAdapter(List<LoadPhoto.ImageFolder> folderList){
            this.folderList = folderList;

            //如果是要在一个界面显示所有图片的话，可以把每个文件夹的图片都拿出来放在一个list里面
            images = new ArrayList<>();
            for (int i = 0; i < folderList.size(); i++) {
                LoadPhoto.ImageFolder folder = folderList.get(i);
                for (int j = 0; j < folder.getImages().size(); j++) {
                    String path = folder.getImages().get(j);
                    images.add(path);
                }

            }
        }

        @Override
        public int getCount() {
            return images.size();
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
            //gridview优化的写法,这个很简单，具体可以百度
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = new ImageView(parent.getContext());
                holder.iv = (ImageView) convertView;
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //显示图片一般是要正方形，可以先在构造方法里面获取屏幕宽度除以列数，我这里随便写的300
            holder.iv.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            //这里用Picasso框架显示图片，我用了很多，感觉这个加载图片最快，加载本地文件要在前面加上file://
            //Picasso具体用法可以百度
            String filePath = "file://"+images.get(position);
            Picasso.with(parent.getContext()).load(filePath).resize(300,300).centerCrop().into( holder.iv);

            return convertView;
        }

        class ViewHolder{
            ImageView iv;

        }
    }
}
