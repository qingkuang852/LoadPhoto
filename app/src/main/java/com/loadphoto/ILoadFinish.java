package com.loadphoto;

import java.util.List;

/**
 * Created by wenming on 2016/7/7.
 */
public interface ILoadFinish<T> {
    void finish(List<T> datas);
}
