package jrl.acdat.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnConexionHTTP, btnConexionAsincrona, btnConexionAAHC, btnConexionVolley, btnDescargaImagenesArchivos, btnSubirArchivos;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexionHTTP = (Button)findViewById(R.id.btnConexionHTTP);
        btnConexionHTTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, ConexionHTTPActivity.class);
                startActivity(i);
            }
        });
        btnConexionAsincrona = (Button)findViewById(R.id.btnConexionAsincrona);
        btnConexionAsincrona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, ConexionAsincronaActivity.class);
                startActivity(i);
            }
        });
        btnConexionAAHC = (Button)findViewById(R.id.btnConexionAAHC);
        btnConexionAAHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, ConexionAAHCActivity.class);
                startActivity(i);
            }
        });
        btnConexionVolley = (Button)findViewById(R.id.btnConexionVolley);
        btnConexionVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, ConexionVolleyActivity.class);
                startActivity(i);
            }
        });
        btnDescargaImagenesArchivos = (Button)findViewById(R.id.btnDescargaImagenesArchivos);
        btnDescargaImagenesArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, DescargaImagenesArchivosActivity.class);
                startActivity(i);
            }
        });
        btnSubirArchivos = (Button)findViewById(R.id.btnSubirArchivos);
        btnSubirArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, SubirArchivosActivity.class);
                startActivity(i);
            }
        });
    }
}
