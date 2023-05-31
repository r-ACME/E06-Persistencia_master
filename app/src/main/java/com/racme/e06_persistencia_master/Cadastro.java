package com.racme.e06_persistencia_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Cadastro extends AppCompatActivity {

    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        produtoDAO = new ProdutoDAO(Cadastro.this);

        Button btnSaveProduct = findViewById(R.id.btn_saveproduct);
        Button btnUpdateList = findViewById(R.id.btn_updatelist);
        EditText txtProductName = findViewById(R.id.txt_name);
        EditText txtProductPrice = findViewById(R.id.txt_price);

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtProductName.getText().toString();
                Double price = Double.parseDouble(txtProductPrice.getText().toString());

                Produto product = new Produto(null, name, price);

                produtoDAO.insert(product);

                List<Produto> data = produtoDAO.getAll();
                TextView lblDataBase = findViewById(R.id.lbl_database);

                String info = "";

                for (Produto current: data) {
                    info += current.toString() + "\n";
                }

                lblDataBase.setText(info);
            }
        });

        btnUpdateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Produto> data = produtoDAO.getAll();
                TextView lblDataBase = findViewById(R.id.lbl_database);

                String info = "";

                for (Produto current: data) {
                    info += current.toString() + "\n";
                }

                lblDataBase.setText(info);

            }
        });
    }
}