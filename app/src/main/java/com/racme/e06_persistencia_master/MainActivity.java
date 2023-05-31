package com.racme.e06_persistencia_master;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static int FOTO_CODE = 1;

    public static final String SHARED_PREF = "SharedPref";
    private SharedPreferences preferences;
    private SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeLog();

        Button btnTakePicture = findViewById(R.id.btn_takepicture);
        Button btnSavePicture = findViewById(R.id.btn_savepicture);
        ImageView ivPreview = findViewById(R.id.iv_preview);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(fotoIntent, FOTO_CODE);
            }
        });

        btnSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) ivPreview.getDrawable()).getBitmap();
                File file = new File(MainActivity.this.getExternalFilesDir(null), "img.png");

                try {
                    if(Environment.getExternalStorageState(file).equals(Environment.MEDIA_MOUNTED)) {
                        Log.d("FOTO SALVA", file.getAbsolutePath());
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    }
                } catch(Exception e) {
                    Log.e("TESTES", "Erro na gravação do arquivo:" + e.toString());
                }

                Toast.makeText(MainActivity.this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnProducts = findViewById(R.id.btn_products);
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
            }
        });
    }

    public void timeLog() {
        preferences = this.getSharedPreferences(SHARED_PREF,0);
        String time = pattern.format(new Date());
        String hour = preferences.getString("time","");

        if (hour.equals("")){
            Toast.makeText(MainActivity.this, "Data atual: " + time, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Data do último acesso: " + hour, Toast.LENGTH_LONG).show();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("time", time);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ImageView ivPreview = findViewById(R.id.iv_preview);

        if (requestCode == FOTO_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivPreview.setImageBitmap(photo);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}