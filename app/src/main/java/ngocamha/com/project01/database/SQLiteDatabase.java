package ngocamha.com.project01.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PL on 7/4/2017.
 */

public class SQLiteDatabase extends SQLiteOpenHelper {
    public static  final  String DB_NAME = "QuanLyChiTieu.db";
    public static final int DB_VERSION = 3;
    public static final String TABLE_ACCOUNT = "tbl_account";
    public static final String TABLE_TRANSACTION = "tbl_transaction";

    public static final String COLUMN_ACCOUNT_ID  = "_id";
    public static final String COLUMN_ACCOUNT_NAME  = "acc_name";
    public static final String COLUMN_ACCOUNT_BALANCE  = "acc_balance";
    public static final String COLUMN_ACCOUNT_CREATED_AT  = "created_at";
    public static final String COLUMN_ACCOUNT_UPDATED_AT  = "updated_at";

    public static final String COLUMN_TRANSACTION_ID  = "_id";
    public static final String COLUMN_TRANSACTION_TYPE  = "type";
    public static final String COLUMN_TRANSACTION_MONEY  = "transaction_money";
    public static final String COLUMN_TRANSACTION_REASON  = "reason";
    public static final String COLUMN_TRANSACTION_ACTION_TIME  = "action_date_time";
    public static final String COLUMN_TRANSACTION_ACCOUNT_ID  = "account_id";
    public static final String COLUMN_TRANSACTION_REMAIN_MONEY  = "remain_money";


    public static final String CREATE_TABLE_ACCOUNT  = "CREATE TABLE "+TABLE_ACCOUNT + "("
            +  COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ACCOUNT_NAME + " TEXT NOT NULL , "
            + COLUMN_ACCOUNT_BALANCE + " INTEGER NOT NULL ,"
            + COLUMN_ACCOUNT_CREATED_AT + " TEXT , "
            + COLUMN_ACCOUNT_UPDATED_AT + " TEXT ); "
            ;

    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE "+ TABLE_TRANSACTION + "( "
            + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TRANSACTION_TYPE + " TEXT ,"
            + COLUMN_TRANSACTION_MONEY + " INTEGER ,"
            + COLUMN_TRANSACTION_REASON + " TEXT ,"
            + COLUMN_TRANSACTION_ACTION_TIME + " TEXT ,"
            + COLUMN_TRANSACTION_ACCOUNT_ID + " INTEGER ,"
            + COLUMN_TRANSACTION_REMAIN_MONEY + " INTEGER ); "
            ;


    public SQLiteDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);

//        ContentValues values =  new ContentValues();
//        values.put(COLUMN_ACCOUNT_NAME, "Tien mat");
//        values.put(COLUMN_ACCOUNT_BALANCE, 100000);
//        values.put(COLUMN_ACCOUNT_CREATED_AT, "2017-07-06 10:15:20");
//        values.put(COLUMN_ACCOUNT_UPDATED_AT, "2017-07-06 10:15:20");
//        insertAccount(values);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop1 = "DROP TABLE IF EXITS "+ TABLE_ACCOUNT;
        sqLiteDatabase.execSQL(drop1);

        String drop2 = "DROP TABLE IF EXITS "+ TABLE_TRANSACTION;
        sqLiteDatabase.execSQL(drop2);
    }

    public long insertAccount(ContentValues values){
        return getWritableDatabase().insert(TABLE_ACCOUNT, null, values );
    }
    public long insertTransaction(ContentValues values){
        return getWritableDatabase().insert(TABLE_TRANSACTION, null, values);
    }

    public int updateAccount(ContentValues values, String[] id){
        return getWritableDatabase().update(TABLE_ACCOUNT, values, COLUMN_ACCOUNT_ID +" =?", id );
    }

    public int updateTransaction(ContentValues values, String[] id){
        return getWritableDatabase().update(TABLE_TRANSACTION, values, COLUMN_TRANSACTION_ID + " =? ", id);
    }
    public int deleteAccount(String [] id){
        return getWritableDatabase().delete(TABLE_ACCOUNT, COLUMN_ACCOUNT_ID + " =? ", id);
    }
    public int deleteTransaction(String[] id) {
        return getWritableDatabase().delete(TABLE_TRANSACTION, COLUMN_TRANSACTION_ID + " =? ", id);
    }
    public Cursor rawQuery(String sql){
        return getReadableDatabase().rawQuery(sql, null);
    }
     
}
