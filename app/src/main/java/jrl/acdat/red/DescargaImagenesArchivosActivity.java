package jrl.acdat.red;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;

public class DescargaImagenesArchivosActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txvFichero;
    ScrollView scvDesplazamiento;
    EditText edtUrl;
    Button btnDescargarImagenes;
    Button btnDescargarFicheros;
    ImageView imgvImagen;
    Memoria memoria;
    Resultado resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_imagenes_archivos);
        txvFichero = (TextView)findViewById(R.id.txvFichero);
        txvFichero.setVisibility(View.INVISIBLE);
        scvDesplazamiento = (ScrollView)findViewById(R.id.scvDesplazamiento);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnDescargarImagenes = (Button) findViewById(R.id.btnDescargarImagenes);
        btnDescargarImagenes.setOnClickListener(this);
        btnDescargarFicheros = (Button)findViewById(R.id.btnDescargarFicheros);
        btnDescargarFicheros.setOnClickListener(this);
        imgvImagen = (ImageView) findViewById(R.id.imgvImagen);
        memoria = new Memoria(this);
        resultado = new Resultado();
    }
    @Override
    public void onClick(View v) {
        String url = edtUrl.getText().toString();
        if (v == btnDescargarImagenes) {
            txvFichero.setVisibility(View.INVISIBLE);
            scvDesplazamiento.setVisibility(View.INVISIBLE);
            imgvImagen.setVisibility(View.VISIBLE);
            descargaImagen(url);
        }
        if (v == btnDescargarFicheros) {
            imgvImagen.setVisibility(View.INVISIBLE);
            scvDesplazamiento.setVisibility(View.VISIBLE);
            txvFichero.setVisibility(View.VISIBLE);
            descargaFichero(url);
        }
    }
    private void descargaImagen(String url) {
        /*
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvImagen);
        */

        //utilizar OkHttp3

        OkHttpClient client = new OkHttpClient();
            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(client))
                    .build();

        Picasso.with(this).load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvImagen);
    }

    private void descargaFichero(String url) {
        final ProgressDialog progreso = new ProgressDialog(this);
        File fichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(fichero) {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                txvFichero.setText("StatusCode: " + statusCode + "\nMessage: " + throwable.getMessage());
                Toast.makeText(getApplicationContext(), "Fallo en la descarga", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Descarga con éxito", Toast.LENGTH_LONG).show();
                if(memoria.disponibleEscritura()) {
                    resultado = memoria.leerExterna(response.getName(), "UTF-8");
                    if(resultado.getCodigo())
                        txvFichero.setText(resultado.getContenido());
                    else {
                        txvFichero.setText("StatusCode: " + statusCode);
                        Toast.makeText(DescargaImagenesArchivosActivity.this, "Error al leer " + response.getAbsolutePath() + " " + resultado.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    txvFichero.setText("StatusCode: " + statusCode);
                    Toast.makeText(DescargaImagenesArchivosActivity.this, "Memoria externa no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
