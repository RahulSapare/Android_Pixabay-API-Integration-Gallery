package com.example.androcid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public final String taskDB="taskDB.db";
    public final String primaryKey="primaryKey";
    public final String taskId="taskId";
    public final String taskName="taskName";
    public final String taskDiscription="taskDiscription";
    public final String taskTable="taskTable";
    public final String taskDate="taskDate";

    public DBHelper(Context context){
        super(context,"taskDB.db",null,1);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " +taskTable+ "(" + primaryKey+ " INTEGER PRIMARY KEY,"+ taskName+ " TEXT,"+ taskDiscription + " TEXT,"+taskDate+" Date)";

        Log.i("onCrate","Creating table");

        db.execSQL(CREATE_TABLE);

        Log.i("onCrate","Created table");

    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+taskTable);
        onCreate(database);
    }

    void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(taskId, task.taskId);
        values.put(taskName, task.taskName);
        values.put(taskDiscription, task.taskDiscription);
        values.put(taskDate, dateFormat.format(date));
        Log.i("task","adding");

        // Inserting Row
        db.insert(taskTable, null, values);
        Log.i("task","added");
        //2nd argument is String containing nullColumnHack
        db.close();
        Log.i("task","Closed");
    }
    public int getTaskCount(){//SELECT COUNT(*) AS RowCnt FROM yourTable
        String selectQuery = "SELECT  * FROM " + taskTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }
    Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.query(taskTable, new String[] { primaryKey,taskId,
                        taskName, taskDiscription, taskDate}, taskId + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        Task task=new Task();

        if (cursor != null&&cursor.moveToFirst()) {
            task.taskId = Integer.parseInt(cursor.getString(0));
            task.taskName= cursor.getString(2);
            task.taskDiscription= cursor.getString(3);
            task.taskDate= cursor.getString(4);
        }else {

            task.taskId=id;
            task.taskName="null";
            task.taskDiscription="Can't find";
        }
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + taskTable;

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        int i=1;
        Cursor cursor=db.rawQuery("SELECT * FROM "+taskTable+" ORDER BY "+taskDate+" DESC Limit 10",null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task= new Task();

                //primary=Integer.parseInt(cursor.getString(0));
                task.taskId=i++;
                task.taskName=cursor.getString(2);
                task.taskDiscription=cursor.getString(3);
                task.taskDate=cursor.getString(4);

                taskList.add(task);

            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;

    }

    public void deleteAllTask(){
        SQLiteDatabase database=this.getWritableDatabase();
        String sqlQuery="DELETE FROM "+taskTable;
        //database.delete(orderTable);
        database.execSQL(sqlQuery);
    }

}
