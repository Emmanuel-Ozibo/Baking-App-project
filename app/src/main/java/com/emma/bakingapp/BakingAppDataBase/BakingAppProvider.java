package com.emma.bakingapp.BakingAppDataBase;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emma.bakingapp.Utils.ToastMessageUtil;

public class BakingAppProvider extends ContentProvider{
    private BakingAppDbHelper bakingOpenDbHelper;

    //matcher of a directory
    private static final int DIRECTORY = 100;

    //matcher for a single item
    private static final int SINGLE_ITEM = 101;

    //URI MATCHER
    private UriMatcher mUrimatcher = getUriMatcher();

    private UriMatcher getUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //match with directory
        matcher.addURI(BakingAppContract.AUTHORITY, BakingAppContract.PATH, 100);

        //match with a single item
        matcher.addURI(BakingAppContract.AUTHORITY, BakingAppContract.PATH + "/#" , 101);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        bakingOpenDbHelper = new BakingAppDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        //get a ref to a readable data base
        final SQLiteDatabase readDb = bakingOpenDbHelper.getReadableDatabase();

        //matchs to know the action to take
        int MATCH = mUrimatcher.match(uri);

        //returned cursor
        Cursor mCursor = null;

        switch (MATCH){
            //which is also the case for this project
            case DIRECTORY:

               mCursor =  readDb.query(BakingAppContract.BakingAppContractFiles.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        BakingAppContract.BakingAppContractFiles._ID);
                break;

            default:
                throw new UnsupportedOperationException("Cant query: " + uri);

        }
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //ref to a writeable database
        final SQLiteDatabase writeDb = bakingOpenDbHelper.getWritableDatabase();

        //matcher
        int MATCH = mUrimatcher.match(uri);

        //uri returned
        Uri mUri  = null;

        switch (MATCH){
            //WHICH IS THE CASE
            case DIRECTORY:

                long id = writeDb.insert(BakingAppContract.BakingAppContractFiles.TABLE_NAME, null, contentValues);

                if (id > 0){
                    //the insertion was successful
                    mUri = ContentUris.withAppendedId(uri, id);

                }else {
                    throw new SQLException("Cant insert in: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("OPERATION IS UNSUPPORTED:");

        }
        return mUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
