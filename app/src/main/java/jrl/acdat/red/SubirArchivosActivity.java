package jrl.acdat.red;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubirArchivosActivity extends AppCompatActivity {

    public static final String WEB = "http://192.168.3.57/acceso/upload.php";

    TextView txvInformacion;
    EditText edtUrl;
    Button btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_archivos);

        txvInformacion = (TextView)findViewById(R.id.txvInformacion);
        edtUrl = (EditText)findViewById(R.id.edtUrl);
        btnSubir = (Button)findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}