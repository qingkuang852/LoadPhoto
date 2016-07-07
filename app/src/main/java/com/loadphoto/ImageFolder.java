package com.loadphoto;

import java.util.List;

/**
 * 图片文件夹类
 * Created by wenming on 2016/3/16.
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
