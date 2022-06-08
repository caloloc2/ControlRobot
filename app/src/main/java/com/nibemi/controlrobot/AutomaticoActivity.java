package com.nibemi.controlrobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AutomaticoActivity extends AppCompatActivity {

    User user = new User();
    BluetoothControl bluetoothControl;
    TimerLectura timerLectura;
    Button btn_arriba, btn_abajo, btn_derecha, btn_izquierda;
    Button btn_iniciar, btn_detener, btn_regresar;
    TextView txt_carga, txt_numero_semillas, txt_porcentaje;
    TextView txt_cantidad_surcos, txt_distancia_semilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatico);

        bluetoothControl = user.getBluetoothControl();

        btn_arriba = (Button) findViewById(R.id.btn_arriba3);
        btn_abajo = (Button) findViewById(R.id.btn_abajo3);
        btn_derecha = (Button) findViewById(R.id.btn_derecha3);
        btn_izquierda = (Button) findViewById(R.id.btn_izquierda3);

        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        btn_detener = (Button) findViewById(R.id.btn_detener);
        btn_regresar = (Button) findViewById(R.id.btn_regresar2);

        txt_carga = (TextView)findViewById(R.id.txt_carga2);
        txt_numero_semillas = (TextView)findViewById(R.id.txt_numero_semillas2);
        txt_porcentaje = (TextView)findViewById(R.id.txt_porcentaje2);

        txt_cantidad_surcos = (TextView)findViewById(R.id.txt_cantidad_surcos);
        txt_distancia_semilla = (TextView)findViewById(R.id.txt_distancia_semilla);

        timerLectura = new TimerLectura(new Runnable() {
            @Override
            public void run() {
                String data = bluetoothControl.getRecibido();
                String[] campos = data.split("/");
                try {
                    String contador = campos[0];
                    String carga = campos[1];
                    String porcentaje = campos[2];

                    txt_carga.setText(carga + "%");
                    txt_numero_semillas.setText(contador);
                    txt_porcentaje.setText(porcentaje+ "%");

                    String cantidad = txt_cantidad_surcos.getText().toString();
                    String distancia = txt_distancia_semilla.getText().toString();

                    if (!cantidad.isEmpty()){
                        bluetoothControl.send(Integer.valueOf(cantidad));
                    }

                    if (!distancia.isEmpty()){
                        bluetoothControl.send((Integer.valueOf(distancia)));
                    }
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

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(7);
            }
        });

        btn_detener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(8);
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