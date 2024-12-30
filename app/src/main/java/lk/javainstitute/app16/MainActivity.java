package lk.javainstitute.app16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this, "app16.db", null, 1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                        sqLiteDatabase.execSQL("INSERT INTO `user`(`name`,`mobile`,`city`) VALUES ('Sahan','0771234567','Kandy')");
                    }
                }).start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this, "app16.db", null, 1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getReadableDatabase();
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM `user`", new String[]{});

                        while (cursor.moveToNext()) {
                            String name = cursor.getString(1);
                            Log.i("App16Log", name);
                        }
                    }
                }).start();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this, "app16.db", null, 1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("name", "Kasun");
                        contentValues.put("mobile", "0771234567");
                        contentValues.put("city", "Kandy");

                        long id = sqLiteDatabase.insert("user", null, contentValues);
                        Log.i("App16Log", String.valueOf(id));
                    }
                }).start();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteOpenHelper sqLiteHelper = new SQLiteHelper(MainActivity.this, "app16.db", null, 1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getReadableDatabase();

                        String[] projection = new String[]{"id", "name", "city"};

                        String selection = "id=? AND name=?";
                        String[] selectionArgs = new String[]{"2", "Kasun"};

                        Cursor cursor = sqLiteDatabase.query(
                                "user",
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                null
                        );

                        while (cursor.moveToNext()) {
                            String id = cursor.getString(0);
                            String name = cursor.getString(1);
                            String city = cursor.getString(2);
                            Log.i("App16Log", id);
                            Log.i("App16Log", name);
                            Log.i("App16Log", city);
                        }
                    }
                }).start();
            }
        });
    }
}

class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "mobile TEXT NOT NULL, " +
                "city TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }
}