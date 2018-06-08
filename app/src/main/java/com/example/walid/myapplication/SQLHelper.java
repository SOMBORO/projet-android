package com.example.walid.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper{
    public static final int databaseVersion = 1;
    private static final String DATABASE_NAME = "EtudiantDB";
    private static final String TABLE_NAME = "etudiant";
    private static final String ID = "id";
    private static final String NOM = "nom";
    private static final String EMAIL = "email";
    private static final String OPTION = "option";
    private static final String ABS = "abs";

    public SQLHelper(Context context){
        super(context, DATABASE_NAME, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ETUDIANT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"("
                + ID + " INTEGER PRIMARY KEY, "
                + NOM + " TEXT, "
                + EMAIL + " TEXT, "
                + OPTION + " TEXT, "
                + ABS + " INTEGER "
                +")";
        db.execSQL(CREATE_ETUDIANT_TABLE);

    }
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        String q = "DELETE FROM "+TABLE_NAME;
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEtudiant(Etudiant etudiant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, etudiant.getId());
        values.put(NOM, etudiant.getNom());
        values.put(EMAIL, etudiant.getEmail());
        values.put(OPTION, etudiant.getOption());
        values.put(ABS, etudiant.getAbs());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getByOption(String option){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, OPTION+"=?", new String[]{option}, null, null, null);
    }

    public Cursor getAllEtudiant(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, new String[]{}, null, null, null);
    }

    public int getEtudiantCount(){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
