package com.nibemi.controlrobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    User user = new User();
    BluetoothControl bluetoothControl;

    Button btn_automatico, btn_manual, btn_arriba, btn_abajo, btn_derecha, btn_izquierda, btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothControl = user.getBluetoothControl();

        btn_automatico = (Button) findViewById(R.id.btn_automatico);
        btn_manual = (Button) findViewById(R.id.btn_manual);

        btn_arriba = (Button) findViewById(R.id.btn_arriba);
        btn_abajo = (Button) findViewById(R.id.btn_abajo);
        btn_izquierda = (Button) findViewById(R.id.btn_izquierda);
        btn_derecha = (Button) findViewById(R.id.btn_derecha);

        btn_salir = (Button)findViewById(R.id.btn_salir);

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

        btn_automatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AutomaticoActivity.class);
                startActivity(intent);

            }
        });

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManualActivity.class);
                startActivity(intent);
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.cancel();
                finish();
            }
        });
    }
}