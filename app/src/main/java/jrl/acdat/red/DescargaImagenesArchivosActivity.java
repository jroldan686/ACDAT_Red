package jrl.acdat.red;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class DescargaImagenesArchivosActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    Button btnDescargarImagenes;
    Button btnDescargarFicheros;
    ImageView imgvImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_imagenes_archivos);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnDescargarImagenes = (Button) findViewById(R.id.btnDescargarImagenes);
        btnDescargarImagenes.setOnClickListener(this);
        btnDescargarFicheros = (Button)findViewById(R.id.btnDescargarFicheros);
        btnDescargarFicheros.setOnClickListener(this);
        imgvImagen = (ImageView) findViewById(R.id.imgvImagen);
    }
    @Override
    public void onClick(View v) {
        String url = edtUrl.getText().toString();
        if (v == btnDescargarImagenes)
            descargaImagen(url);
    }
    private void descargaImagen(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvImagen);
        //utilizar OkHttp3
        OkHttpClient client = new OkHttpClient();
            Picasso picasso = new Picasso().Builder(this)
                    .downloader(new OkHttp3Downloader(client))
                    .build();

        Picasso.with(this).load(url).placeholder(R.drawable.placeholder).error(R.drawable.placeholder_error).into(imgvImagen);
    }
}
