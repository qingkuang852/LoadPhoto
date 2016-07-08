package com.loadphoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        final TextView textViewHeader1 = new TextView(this);
        final TextView textViewHeader2 = new TextView(this);

        textViewHeader1.setText("header 1");
        textViewHeader2.setText("header 2");

        final TextView textViewFooter1 = new TextView(this);
        final TextView textViewFooter2 = new TextView(this);

        textViewFooter1.setText("footer 1");
        textViewFooter2.setText("footer 2");

        LoadUtil.getPhotoLoader(this, new ILoadFinish<LoadPhoto.ImageFolder>() {
            @Override
            public void finish(List<LoadPhoto.ImageFolder> datas) {
                GridAdapter adapter = new GridAdapter(datas);
                HeaderFooterWrapper headerFooterWrapper = new HeaderFooterWrapper(adapter);
                headerFooterWrapper.addHeaderView(textViewHeader1);
                headerFooterWrapper.addHeaderView(textViewHeader2);
                headerFooterWrapper.addFooterView(textViewFooter1);
                headerFooterWrapper.addFooterView(textViewFooter2);

                recyclerView.setAdapter(headerFooterWrapper);
            }
        });


    }

    class GridAdapter extends RecyclerView.Adapter<ViewHolder>{

        private ArrayList<String> images;
        public GridAdapter(List<LoadPhoto.ImageFolder> data){
            //如果是要在一个界面显示所有图片的话，可以把每个文件夹的图片都拿出来放在一个list里面
            images = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                LoadPhoto.ImageFolder folder = data.get(i);
                for (int j = 0; j < folder.getImages().size(); j++) {
                    String path = folder.getImages().get(j);
                    images.add(path);
                }

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView iv = new ImageView(parent.getContext());
            return ViewHolder.createViewHolder(parent.getContext(),iv);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImageView iv = (ImageView) holder.getConvertView();
            //显示图片一般是要正方形，可以先在构造方法里面获取屏幕宽度除以列数，我这里随便写的300
            iv.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            //这里用Picasso框架显示图片，我用了很多，感觉这个加载图片最快，加载本地文件要在前面加上file://
            //Picasso具体用法可以百度
            String filePath = "file://"+images.get(position);
            Picasso.with(holder.getContext()).load(filePath).resize(300,300).centerCrop().into( iv);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }
}
