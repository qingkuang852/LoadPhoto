package com.loadphoto;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

/**
 * Created by wenming on 2016/7/7.
 */
public abstract class LoadResource implements LoaderManager.LoaderCallbacks<Cursor> {
    protected FragmentActivity activity;
    private ILoadFinish iLoadFinish;

    public LoadResource(FragmentActivity activity){
        this.activity = activity;
    }

    public void register(ILoadFinish iLoadFinish){
        this.iLoadFinish = iLoadFinish;
        register();
    };

    public abstract void register();

    public abstract List<?> getDatas(Cursor data);

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data!=null && data.getCount() > 0) {
            iLoadFinish.finish(getDatas(data));
        }else {
            Log.v(getClass().getName(),"no data");
        }
    }


}
