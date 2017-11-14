package jrl.acdat.red;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionAsincronaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUrl;
    RadioButton rdbJava, rdbApache;
    Button btnConectar;
    WebView wbvWeb;
    TextView txvTiempo;
    Resultado resultado;
    long inicio, fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_asincrona);

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
        resultado = new Resultado();
        //StrictMode.setThreadPolicy(new  StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View v) {
        String texto = edtUrl.getText().toString();
        long inicio, fin;

        String[] datos = new String[2];
        datos[0] = texto;

        if (v == btnConectar) {
            inicio = System.currentTimeMillis();
            if (rdbJava.isChecked())
                datos[1] = "java";
            else
                datos[1] = "apache";
            new ConexionAsincronaActivity.TareaAsincrona(ConexionAsincronaActivity.this).execute(datos);
        }
    }

    public class TareaAsincrona extends AsyncTask<String, Integer, Resultado> {
        private ProgressDialog progreso;
        private Context context;

        public TareaAsincrona(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Conectando . . .");
            progreso.setCancelable(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    ConexionAsincronaActivity.TareaAsincrona.this.cancel(true);
                }
            });
            progreso.show();
            inicio = System.currentTimeMillis();
        }

        protected Resultado doInBackground(String... cadena) {
            Resultado resultado = new Resultado();
            int i = 1;
            try {
                // operaciones en el hilo secundario
                publishProgress(i++);

                if (cadena[1] == "java")
                    resultado = Conexion.conectarJava(cadena[0]);
                if (cadena[1] == "apache")
                    resultado = Conexion.conectarApache(cadena[0]);
            } catch (Exception e) {

            }
            return resultado;
        }

        protected void onProgressUpdate(Integer... progress) {
            progreso.setMessage("Conectando " + Integer.toString(progress[0]));
        }

        protected void onPostExecute(Resultado result) {
            progreso.dismiss();
            // mostrar el resultado
            fin = System.currentTimeMillis();
            if (result.getCodigo())
                wbvWeb.loadDataWithBaseURL(null, result.getContenido(), "text/html", "UTF-8", null);
            else
                wbvWeb.loadDataWithBaseURL(null, result.getMensaje(), "text/html", "UTF-8", null);
            txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }

        protected void onCancelled() {
            progreso.dismiss();
            // mostrar cancelación
            fin = System.currentTimeMillis();
            if (resultado.getCodigo())
                wbvWeb.loadDataWithBaseURL(null, resultado.getContenido(), "text/html", "UTF-8", null);
            else
                wbvWeb.loadDataWithBaseURL(null, resultado.getMensaje(), "text/html", "UTF-8", null);
            txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }
    }
}
