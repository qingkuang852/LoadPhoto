package com.loadphoto;

import android.support.v4.app.FragmentActivity;

/**
 * Created by wenming on 2016/7/7.
 */
public class LoadUtil {

    public static void getPhotoLoader(FragmentActivity activity,ILoadFinish<LoadPhoto.ImageFolder> iLoadFinish){
        new LoadPhoto(activity).register(iLoadFinish);
    }

    public static void getContacts(FragmentActivity activity,ILoadFinish<LoadContacts.ContactPeople> iLoadFinish){
        new LoadContacts(activity).register(iLoadFinish);
    }
}
