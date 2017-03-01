package com.lostntkdgmail.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class LiftDatabaseAccessor extends DatabaseAccessor {
    protected static String databaseName = "Lift.db";
    protected static String tableName = "Lift";
    protected static String[] columns = {"ID","Type","Lift"};

    public LiftDatabaseAccessor(Context context) {
        super(context, tableName, columns);
    }

    public boolean insert(String type,String lift) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("Debug","Inserting: \"" + type +", "+ lift +", \" into \"" + tableName + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],type);
        contentValues.put(col[2],lift);

        long result = db.insert(tableName,null ,contentValues);
        if(result == -1) {
            Log.d("Debug", "Failed to inserted");
            return false;
        }
        Log.d("Debug", "Successfully inserted");
        return true;
    }
    public boolean updateData(String id,String type,String lift) {
        Log.d("Debug","Replacing id: " + id + " with: " + type +" "+ lift + " into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],type);
        contentValues.put(col[2],lift);
        db.update(tableName, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    public Cursor getCursor(String type, String lift, String sorting) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        if(type != null && lift != null) {
            String[] selection = {type,lift};
            res = db.query(tableName, col, col[1] + " = ? and " + col[2] + " = ?", selection, null, null,sorting);
        }
        else if(type != null) {
            String[] selection = {type};
            res = db.query(tableName, col, col[1] + " = ?", selection, null, null,sorting);
        }
        else if(lift != null) {
            String[] selection = {lift};
            res = db.query(tableName, col, col[2] + " = ?", selection, null, null,sorting);
        }
        else
            res = db.query(tableName,col,null,null,null,null,sorting);
        return res;
    }
    public Cursor getCursor(String type, String lift) {
        return getCursor(type,lift,col[1]+" ASC");
    }
}