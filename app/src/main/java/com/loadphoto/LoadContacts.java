package com.loadphoto;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenming on 2016/7/7.
 */
public class LoadContacts extends LoadResource {

//    private static final Uri CONTACTS_URI = ContactsContract.Data.CONTENT_URI;
    private static final Uri CONTACTS_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final Uri EMAIL_CONTACTS_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

    public LoadContacts(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public void register() {
        activity.getSupportLoaderManager().initLoader(1,null,this);
    }

    @Override
    public List<?> getDatas(Cursor data) {
        List<ContactPeople> peoples = new ArrayList<>();
        data.moveToFirst();
        do {
            ContactPeople people = new ContactPeople();
            String name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.v("fag","name "+name);
            Log.v("fag","number "+number);
            people.name = name;
            people.number = number;
            peoples.add(people);
        }while (data.moveToNext());
        return peoples;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(activity,CONTACTS_URI,null,null,null,null);
    }

    public final class ContactPeople{
        public String name;
        public String number;
    }

}
