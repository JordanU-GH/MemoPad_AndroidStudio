package edu.jsu.mcis.cs408.memopad.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.jsu.mcis.cs408.memopad.model.Memo;

public class MemoDAO {
    private final DAOFactory daoFactory;

    // Default constructor that provides us with a way to access our database
    MemoDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Method to create a new memo
    public void create(Memo m) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_MEMO, m.getMemo());

        SQLiteDatabase db = daoFactory.getWritableDatabase();

        db.insert(DAOFactory.TABLE_MEMOS, null, values);
        db.close();
    }

    // Method to delete a memo
    public void delete(Integer id) {
        String[] values = {String.valueOf(id)};
        String whereClause = DAOFactory.COLUMN_ID + " = ?";
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        db.delete(DAOFactory.TABLE_MEMOS, whereClause, values);
        db.close();
    }

    // Method to find a single memo using its id
    public Memo find(int id) {
        String query = "SELECT * FROM " + DAOFactory.TABLE_MEMOS + " WHERE " + DAOFactory.COLUMN_ID + " = " + id;

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Memo m = null;

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int newId = cursor.getInt(0);
            String newName = cursor.getString(1);
            cursor.close();
            m = new Memo(newId, newName);
        }

        db.close();
        return m;
    }

    // Method to get all memos as a list
    public List<Memo> list() {
        String query = "SELECT * FROM " + DAOFactory.TABLE_MEMOS;

        List<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int newId = cursor.getInt(0);
                String newMemo = cursor.getString(1);
                allMemos.add( new Memo(newId, newMemo) );
            }
            while ( cursor.moveToNext() );
        }

        db.close();
        return allMemos;
    }
}
