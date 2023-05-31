package com.racme.e06_persistencia_master;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {

    private static final String DATA_BASE = "products.db";

    private static final int DATA_BASE_VERSION = 1;

    SQLiteDatabase dataBase;

    public ProdutoDAO(Context context){
        super(context, DATA_BASE, null, DATA_BASE_VERSION);

        dataBase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlTabelaProduto = "CREATE TABLE product (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL)";

        db.execSQL(sqlTabelaProduto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(Produto P){
        dataBase = getWritableDatabase();

        String name = P.getName();
        Double price = P.getPrice();
        ContentValues contents = new ContentValues();
        contents.put("name", name);
        contents.put("price", price);
        try {
            dataBase.insert("product", null, contents);
        } catch(Exception e){
            Log.d("ERROR " + e.getClass(), e.getMessage());
        }finally {
            dataBase.close();
        }

    }

    public List<Produto> getAll(){

        List<Produto> allProducts = new ArrayList<>();
        dataBase = getReadableDatabase();

        try {

            Cursor line = dataBase.query("product", null, null, null, null, null, "id");

            if (line.moveToFirst()){
                do{

                    Integer id = line.getInt(line.getColumnIndex("id"));
                    String name = line.getString(line.getColumnIndex("name"));
                    Double price = line.getDouble(line.getColumnIndex("price"));

                    Produto product = new Produto(id, name, price);

                    allProducts.add(product);

                }while(line.moveToNext());
            }

        } catch(Exception e){
            Log.d("ERROR " + e.getClass(), e.getMessage());
        }finally {
            dataBase.close();
        }

        return allProducts;
    }

}
