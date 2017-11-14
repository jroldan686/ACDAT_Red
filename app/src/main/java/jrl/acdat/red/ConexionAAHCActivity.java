package jrl.acdat.red;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ConexionAAHCActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUrl;
    RadioButton rdbJava, rdbApache;
    Button btnConectar;
    WebView wbvWeb;
    TextView txvTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_aahc);

        iniciar();
    }

    public void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        rdbJava = (RadioButton) findViewById(R.id.rdbJava);
        rdbApache = (RadioButton) findViewById(R.id.rdbApache);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        wbvWeb = (WebView) findViewById(R.id.wbvWeb);
        txvTiempo = (TextView) findViewById(R.id.txvTiempo);
    }

    @Override
    public void onClick(View view) {
        if(view == btnConectar)
            AAHC();
    }

    private void AAHC() {
        final String texto = edtUrl.getText().toString();
        final long inicio;
        //long fin;
        final ProgressDialog progreso = new ProgressDialog(ConexionAAHCActivity.this);
        inicio = System.currentTimeMillis();
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                    public void onCancel(DialogInterface dialog){
                        RestClient.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // called when response HTTP status is "200 OK"
                long fin = System.currentTimeMillis();
                progreso.dismiss();
                wbvWeb.loadDataWithBaseURL(null, response, "text/html", "UTF-8", null);
                txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " millisegundos");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                long fin = System.currentTimeMillis();
                progreso.dismiss();
                wbvWeb.loadDataWithBaseURL(null, "Error: " + statusCode + " " + response + " " + t, "text/html", "UTF-8", null);
                txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " millisegundos");
            }
        });
    }
}
