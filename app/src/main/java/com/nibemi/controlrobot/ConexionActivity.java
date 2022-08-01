package com.nibemi.controlrobot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class ConexionActivity extends AppCompatActivity {

    ListView lista_dispositivos;

    private static final String btlog = "BLUETOOTH_LOG";
    private BluetoothAdapter btAdapter;
    private BluetoothDevice btDevice;
    private static final int REQUEST_ENABLE_BT = 9001;
    private static Context appContext;
    private ArrayAdapter dispositivosEmparejados;

    BluetoothControl bluetoothControl;
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion);

        appContext = getApplicationContext();

        dispositivosEmparejados = new ArrayAdapter(this, R.layout.dispositivos_encontrados);
        lista_dispositivos = (ListView) findViewById(R.id.list_dispositivos);
        lista_dispositivos.setAdapter(dispositivosEmparejados);

        lista_dispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String info = ((TextView) view).getText().toString();
                String macAddress = info.substring(info.length() - 17);

                Log.d(btlog, "Se seleccionó el dispositivo con MAC ADDRESS " + macAddress);
                btDevice = btAdapter.getRemoteDevice(macAddress);

                user.setBluetoothDevice(btDevice);
                user.setContext(appContext);

                bluetoothControl = new BluetoothControl(btDevice, appContext);
                bluetoothControl.run();

                user.setBluetoothControl(bluetoothControl);

                boolean estado = bluetoothControl.isConnected();

                if (estado){
                    Toast.makeText(getApplicationContext(), "El dispositivo se encuentra conectado.", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido conectar el dispositivo.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(getApplicationContext(), "No soporta el dispositivo", Toast.LENGTH_SHORT).show();
        } else {
            if (!btAdapter.isEnabled()) {
                Log.d(btlog, "Módulo Bluetooth no habilitado");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }else{
                // esta habilitado y lista los dispositivos vinculados
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
                Log.d(btlog, "Numero de dispositivos vinculados = "+String.valueOf(pairedDevices.size()));
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address

                        Log.d(btlog, "Dispositivo = "+deviceName + " - MAC ADDRESS = " + deviceHardwareAddress);
                        dispositivosEmparejados.add(deviceName + " \n" + deviceHardwareAddress);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode == 0){
                Toast.makeText(getApplicationContext(), "No se tiene permisos para utilizar el módulo Bluetooth.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}