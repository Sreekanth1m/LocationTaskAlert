package taskalert.location.sm.locationalert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class TasksSQLiteOpenHelper {

    private static final String TAG = "TasksSQLiteOpenHelper"; //used for logging database version changes

    //data base details
    public static final int VERSION = 1; //version number to be incremented when there is a change in the database structure
    public static final String DB_NAME = "tasksdb.sqlite";
    public static final String TASKS_TABLE = "taskstb";
    //column names
    public static final String TASK_ID = "rowid";
    public static final String TASK_NAME = "name";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_COMPLETE = "complete";
    public static final String TASK_TGLONOFF = "tglonoff";
    public static final String TASK_ADDRESS = "address1";
    public static final String TASK_LATITUDE = "latitude1";
    public static final String TASK_LONGITUDE = "longitude1";
    public static final String TASK_KEY_ID = "_id";


    private static String[] ALL_COLUMNS = new String[]{TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_COMPLETE, TASK_TGLONOFF, TASK_ADDRESS, TASK_LATITUDE, TASK_LONGITUDE};
    private static String[] ALARM_COLUMNS = new String[]{TASK_ID, TASK_NAME, TASK_COMPLETE, TASK_TGLONOFF};

    //column numbers for table columns

    public static final int C_TASK_ID = 0;
    public static final int C_TASK_NAME = 1;
    public static final int C_TASK_DESCRIPTION = 2;
    public static final int C_TASK_COMPLETE = 3;
    public static final int C_TASK_TGLONOFF = 4;
    public static final int C_TASK_ADDRESS = 5;
    public static final int C_TASK_LATITUDE = 6;
    public static final int C_TASK_LONGITUDE = 7;

    private Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public TasksSQLiteOpenHelper(Context context) {
        this.context=context;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public TasksSQLiteOpenHelper open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to be inserted into the database.
    public long insertRow(String task_name, String task_desc, int complete, int tglonoff, String address, String lat, String lng) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(TASK_NAME, task_name);
        insertValues.put(TASK_DESCRIPTION, task_desc);
        insertValues.put(TASK_COMPLETE, complete);
        insertValues.put(TASK_TGLONOFF, tglonoff);
        insertValues.put(TASK_ADDRESS, address);
        insertValues.put(TASK_LATITUDE, lat);
        insertValues.put(TASK_LONGITUDE, lng);

        // Insert the data into the database.
        return db.insert(TASKS_TABLE, null, insertValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = TASK_ID + "=" + rowId;
        return db.delete(TASKS_TABLE, where, null) != 0;
    }


    // Delete completed alarms from the database, by complete column id
    public boolean deleteRow(int complete) {
        String where = TASK_COMPLETE + "=" + complete;
        return db.delete(TASKS_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(TASK_ID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = db.query(true, TASKS_TABLE, ALL_COLUMNS, where, null, null, null, null, null);
        //Cursor c = db.rawQuery(" SELECT " + TASK_ID + " as _id,"+ TASK_NAME +","+ TASK_COMPLETE, TASK_TGLONOFF + FROM tasksdb;",null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAlarmRows() {
        String where = null;
        Cursor c = db.query(true, TASKS_TABLE, ALARM_COLUMNS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = TASK_ID + "=" + rowId;
        Cursor c = db.query(true, TASKS_TABLE, ALL_COLUMNS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String task_name, String task_desc, int complete, int tglonoff, String address, String lat, String lng) {

        ContentValues newValues = new ContentValues();
        String where = TASK_ID + "=" + rowId;
        newValues.put(TASK_NAME, task_name);
        newValues.put(TASK_DESCRIPTION, task_desc);
        newValues.put(TASK_COMPLETE, complete);
        newValues.put(TASK_TGLONOFF, tglonoff);
        newValues.put(TASK_ADDRESS, address);
        newValues.put(TASK_LATITUDE, lat);
        newValues.put(TASK_LONGITUDE, lng);

        // Insert it into the database.
        return db.update(TASKS_TABLE, newValues, where, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context,DB_NAME,null,VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("drop table if exists " + TASKS_TABLE + ";");
            createTables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_ADDRESS + " TEXT");
            db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_LATITUDE + " TEXT");
            db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_LONGITUDE + " TEXT");
        }

        protected void createTables(SQLiteDatabase db) {
            db.execSQL(
                    "create table " + TASKS_TABLE + " ( " +
                            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            TASK_NAME + " TEXT, " +
                            TASK_DESCRIPTION + " TEXT, " +
                            TASK_COMPLETE + " INTEGER, " +
                            TASK_TGLONOFF + " INTEGER, " +
                            TASK_ADDRESS + " TEXT, " +
                            TASK_LATITUDE + " TEXT, " +
                            TASK_LONGITUDE + " TEXT " +
                            ");"
            );
        }

    }
 }
