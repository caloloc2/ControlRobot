package com.nibemi.controlrobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    User user = new User();
    BluetoothControl bluetoothControl;

    Button btn_automatico, btn_manual, btn_salir;
    ImageView btn_arriba, btn_abajo, btn_derecha, btn_izquierda;
    SeekBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothControl = user.getBluetoothControl();

        btn_automatico = (Button) findViewById(R.id.btn_automatico);
        btn_manual = (Button) findViewById(R.id.btn_manual);

        btn_arriba = (ImageView) findViewById(R.id.btn_arriba);
        btn_abajo = (ImageView) findViewById(R.id.btn_abajo);
        btn_izquierda = (ImageView) findViewById(R.id.btn_izquierda);
        btn_derecha = (ImageView) findViewById(R.id.btn_derecha);

        btn_salir = (Button)findViewById(R.id.btn_salir);

        progreso = (SeekBar)findViewById(R.id.progreso);

        progreso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bluetoothControl.send(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_arriba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("TEST", String.valueOf(motionEvent));
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("TEST", "1");
                    bluetoothControl.send(1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("TEST", "0");
                    bluetoothControl.send(0);
                }
                return true;
            }
        });

        btn_abajo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("TEST", String.valueOf(motionEvent));
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("TEST", "1");
                    bluetoothControl.send(1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("TEST", "0");
                    bluetoothControl.send(0);
                }
                return true;
            }
        });

        btn_derecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("TEST", String.valueOf(motionEvent));
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("TEST", "1");
                    bluetoothControl.send(1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("TEST", "0");
                    bluetoothControl.send(0);
                }
                return true;
            }
        });

        btn_izquierda.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("TEST", String.valueOf(motionEvent));
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("TEST", "1");
                    bluetoothControl.send(1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("TEST", "0");
                    bluetoothControl.send(0);
                }
                return true;
            }
        });

        btn_automatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(5);
                Intent intent = new Intent(getApplicationContext(), AutomaticoActivity.class);
                startActivity(intent);

            }
        });

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothControl.send(6);
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