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

    Button btn_automatico, btn_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothControl = user.getBluetoothControl();

        btn_automatico = (Button) findViewById(R.id.btn_automatico);
        btn_manual = (Button) findViewById(R.id.btn_manual);

        btn_automatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = bluetoothControl.getRecibido();
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
//                finish();
//                Intent intent = new Intent(getApplicationContext(), AutomaticoActivity.class);
//                startActivity(intent);

            }
        });

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valor =  1;
                bluetoothControl.send(valor);
//                finish();
//                Intent intent = new Intent(getApplicationContext(), ManualActivity.class);
//                startActivity(intent);
            }
        });
    }
}