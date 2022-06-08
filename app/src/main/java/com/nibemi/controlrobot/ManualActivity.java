package com.nibemi.controlrobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ManualActivity extends AppCompatActivity {

    User user = new User();
    BluetoothControl bluetoothControl;
    Button btn_arriba, btn_abajo, btn_derecha, btn_izquierda;
    Button btn_abrir_tolva, btn_cerrar_tolva, btn_regresar;
    TextView txt_carga, txt_numero_semillas, txt_porcentaje;
    TimerLectura timerLectura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        bluetoothControl = user.getBluetoothControl();

        btn_arriba = (Button) findViewById(R.id.btn_arriba2);
        btn_abajo = (Button) findViewById(R.id.btn_abajo2);
        btn_derecha = (Button) findViewById(R.id.btn_derecha2);
        btn_izquierda = (Button) findViewById(R.id.btn_izquierda2);

        btn_abrir_tolva = (Button) findViewById(R.id.btn_abrir_tolva);
        btn_cerrar_tolva = (Button) findViewById(R.id.btn_cerrar_tolva);
        btn_regresar = (Button) findViewById(R.id.btn_regresar);

        txt_carga = (TextView)findViewById(R.id.txt_carga);
        txt_numero_semillas = (TextView)findViewById(R.id.txt_numero_semillas);

        txt_porcentaje = (TextView)findViewById(R.id.txt_porcentaje);

        timerLectura = new TimerLectura(new Runnable() {
            @Override
            public void run() {
                String data = bluetoothControl.getRecibido();
                String[] campos = data.split("/");

                try{
                    String contador = campos[0];
                    String carga = campos[1];
                    String porcentaje = campos[2];

                    txt_carga.setText(carga + "%");
                    txt_numero_semillas.setText(contador);
                    txt_porcentaje.setText(porcentaje+ "%");
                }catch (Exception e){
                    Log.d("LOG", e.getMessage());
                }

            }
        }, 1000, true);

        btn_arriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(1);
            }
        });

        btn_abajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(2);
            }
        });

        btn_izquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(3);
            }
        });

        btn_derecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(4);
            }
        });

        btn_abrir_tolva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(5);
            }
        });

        btn_cerrar_tolva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(6);
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}