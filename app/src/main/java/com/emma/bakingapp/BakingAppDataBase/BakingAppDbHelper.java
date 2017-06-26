package com.emma.bakingapp.BakingAppDataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class BakingAppDbHelper extends SQLiteOpenHelper{

    //name of the table
    private static final String TABLE_NAME = "baking.db";

    //version
    private static final int VERSION = 1;

    public BakingAppDbHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_BAKING_APP_TABLE = "CREATE TABLE " + BakingAppContract.BakingAppContractFiles.TABLE_NAME + "( " +
                BakingAppContract.BakingAppContractFiles._ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BakingAppContract.BakingAppContractFiles.INGREDIENT + " TEXT NOT NULL, " +
                BakingAppContract.BakingAppContractFiles.RECIPE_NAME + " TEXT NOT NULL, " +
                BakingAppContract.BakingAppContractFiles.RECIPE_STEPS + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(CREATE_BAKING_APP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + BakingAppContract.BakingAppContractFiles.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
