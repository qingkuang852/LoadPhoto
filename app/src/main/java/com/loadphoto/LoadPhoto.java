package com.loadphoto;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenming on 2016/3/17.
 */
public class LoadPhoto extends LoadResource {
    //读取图片文件的参数
    private static final String STORE_IMAGES[] = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
    };

//    private LoadFinishCallback loadFinishCallback;

    public LoadPhoto(FragmentActivity activity){
        super(activity);

    }

    @Override
    public void register() {
        activity.getSupportLoaderManager().initLoader(0,null,this);
    }

    @Override
    public List<ImageFolder> getDatas(Cursor data) {
        List<ImageFolder> folderList = new ArrayList<>();
        int count = data.getCount();
        if (count>0){
            data.moveToFirst();
            do {
                String path = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
                File imageFile = new File(path);
                File folderFile = imageFile.getParentFile();
                ImageFolder folder = new ImageFolder();
                folder.path = folderFile.getAbsolutePath();
                folder.name = folderFile.getName();
                if (!folderList.contains(folder)){
                    List<String> imageList = new ArrayList<>();
                    imageList.add(path);
                    folder.images = imageList;
                    folderList.add(folder);
                }else {
                    ImageFolder folder1 = folderList.get(folderList.indexOf(folder));
                    folder1.images.add(path);
                }
            }while (data.moveToNext());
        }
        return folderList;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES, null, null, STORE_IMAGES[2] + " DESC");
    }

    /**
     * 图片文件夹类
     */
    public class ImageFolder {
        //文件夹名字
        public String name;
        //文件夹的路径
        public String path;
        //文件夹里面的所有图片的路径
        public List<String> images;

        @Override
        public boolean equals(Object o) {
            try {
                ImageFolder other = (ImageFolder) o;
                return this.path.equalsIgnoreCase(other.path);
            }catch (ClassCastException e){
                e.printStackTrace();
            }
            return super.equals(o);
        }

        public List<String> getImages() {
            return images;
        }
    }
}

