package co.pizzayum.pizzayum_android.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "pizza_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(PizzaOrderTableModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PizzaOrderTableModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertOrder(PizzaOrderTableModel order) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PizzaOrderTableModel.PRODUCT_ID, order.getProduct_id());
        values.put(PizzaOrderTableModel.PRODUCT_SIZE, order.getSize());
        values.put(PizzaOrderTableModel.PRODUCT_QUANTITY, order.getProduct_quantity());
        values.put(PizzaOrderTableModel.PRODUCT_CAT, order.getProduct_cat());
        values.put(PizzaOrderTableModel.PRODUCT_NAME, order.getProduct_name());
        values.put(PizzaOrderTableModel.PRODUCT_CONTENT, order.getProduct_content());
        values.put(PizzaOrderTableModel.CRUST_ID, order.getCrust_id());
        values.put(PizzaOrderTableModel.CRUST_DETAILS, order.getCrust_details());
        values.put(PizzaOrderTableModel.TOPPING_ID, order.getTopping_id());
        values.put(PizzaOrderTableModel.TOPPING_DETAILS, order.getTopping_details());
        values.put(PizzaOrderTableModel.EXTRA_CHEESE, order.getExtra_cheese());
        values.put(PizzaOrderTableModel.EXTRA_CHEESE_ID, order.getExtra_cheese_id());
        values.put(PizzaOrderTableModel.BILL, order.getBill());

        // insert row
        long id = db.insert(PizzaOrderTableModel.TABLE_NAME, null, values);

        Log.e("Log","Values Inserted" + id);


        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public PizzaOrderTableModel getOrder(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PizzaOrderTableModel.TABLE_NAME,
                new String[]{
                        PizzaOrderTableModel.PRODUCT_ID,
                        PizzaOrderTableModel.PRODUCT_SIZE,
                        PizzaOrderTableModel.PRODUCT_QUANTITY,
                        PizzaOrderTableModel.PRODUCT_CAT,
                        PizzaOrderTableModel.PRODUCT_NAME,
                        PizzaOrderTableModel.PRODUCT_CONTENT,
                        PizzaOrderTableModel.TOPPING_ID,
                        PizzaOrderTableModel.CRUST_ID,
                        PizzaOrderTableModel.BILL,
                        PizzaOrderTableModel.CRUST_DETAILS,
                        PizzaOrderTableModel.TOPPING_DETAILS,
                        PizzaOrderTableModel.EXTRA_CHEESE,
                        PizzaOrderTableModel.EXTRA_CHEESE_ID
                },
                PizzaOrderTableModel.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        PizzaOrderTableModel ORDER = new PizzaOrderTableModel(
                cursor.getInt(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_SIZE)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_CAT)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_CONTENT)),

                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.TOPPING_ID)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.TOPPING_DETAILS)),

                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.EXTRA_CHEESE)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.EXTRA_CHEESE_ID)),

                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.CRUST_ID)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.CRUST_DETAILS)),
                cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.BILL))
        );

        // close the db connection
        cursor.close();

        return ORDER;
    }

    public List<PizzaOrderTableModel> getAllOrders() {
        List<PizzaOrderTableModel> orders = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PizzaOrderTableModel.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PizzaOrderTableModel order = new PizzaOrderTableModel();
                order.setProduct_id(cursor.getInt(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_ID)));
                order.setSize(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_SIZE)));
                order.setProduct_quantity(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_QUANTITY)));
                order.setProduct_name(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_NAME)));
                order.setProduct_cat(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_CAT)));
                order.setProduct_content(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.PRODUCT_CONTENT)));
                order.setCrust_id(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.CRUST_ID)));
                order.setCrust_details(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.CRUST_DETAILS)));
                order.setTopping_id(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.TOPPING_ID)));
                order.setTopping_details(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.TOPPING_DETAILS)));
                order.setExtra_cheese(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.EXTRA_CHEESE)));
                order.setExtra_cheese_id(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.EXTRA_CHEESE_ID)));
                order.setBill(cursor.getString(cursor.getColumnIndex(PizzaOrderTableModel.BILL)));

                orders.add(order);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return orders;
    }

    public int getOrdersCount() {
        String countQuery = "SELECT  * FROM " + PizzaOrderTableModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateOrder(PizzaOrderTableModel note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PizzaOrderTableModel.PRODUCT_QUANTITY, note.getProduct_quantity());

        // updating row
        return db.update(PizzaOrderTableModel.TABLE_NAME, values, PizzaOrderTableModel.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(note.getProduct_id())});
    }

    public void deleteOrder(PizzaOrderTableModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PizzaOrderTableModel.TABLE_NAME, PizzaOrderTableModel.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(note.getProduct_id())});
        db.close();
    }

    public void getTableColumnNames(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM "+PizzaOrderTableModel.TABLE_NAME+" WHERE 0", null);
        try {
            String[] columnNames = c.getColumnNames();
            for(int i = 0 ; i<columnNames.length; i ++)
                Log.e("Columns","Column Names: "+ columnNames[i]);
        } finally {
            c.close();
        }
    }
}