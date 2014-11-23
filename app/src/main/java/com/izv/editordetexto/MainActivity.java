package com.izv.editordetexto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;


public class MainActivity extends Activity {

    EditText et;
    TextView tv;
    Button bt;
    String ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.etTexto);
        tv=(TextView)findViewById(R.id.tvTitulo);
        bt=(Button)findViewById(R.id.button);
        Intent intent = getIntent();
        Uri data = intent.getData();
        List<String> segments = data.getPathSegments();
        String nombre=segments.get(segments.size()-1);
        ruta = data.getPath();
        tv.setText(nombre);
        File f = new File(ruta);
        try {
            FileInputStream fin = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
            String c = "";
            while (linea != null) {
                c += linea + "\n";
                linea = br.readLine();
            }
            br.close();
            isr.close();
            et.setText(c);

        } catch (IOException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardar(View v){
        String texto = et.getText().toString();
        try {
            File f = new File(ruta);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write(texto);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException ioe) {
        }
    }
}
