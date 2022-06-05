package com.nibemi.controlrobot;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothControl extends Thread {
    private static BluetoothSocket mmSocket = null;
    private final BluetoothDevice mmDevice;
    private static final UUID UUID_CODE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String btlog = "BLUETOOTH_LOG";
    private static Context appContext;
    TimerLectura timerLectura;

    private static String recibido = "";

    private Handler handler = new Handler();

    private static boolean isConnected = false;

    public BluetoothControl(BluetoothDevice device, Context context) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        appContext = context;
        BluetoothSocket tmp = null;

        mmDevice = device;

        try {
                tmp = device.createRfcommSocketToServiceRecord(UUID_CODE);

        } catch (IOException e) {
            Log.e(btlog, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
//        bluetoothAdapter.cancelDiscovery();

        try {


                mmSocket.connect();
                Log.d(btlog, "Recibiendo datos...");
                timerLectura = new TimerLectura(new Runnable() {
                    @Override
                    public void run() {
                        recibido = recibir();
                    }
                }, 2000, true);

                isConnected = true;


        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(btlog, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        manageMyConnectedSocket(mmSocket);
    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket) {
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(btlog, "Could not close the client socket", e);
        }
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static BluetoothSocket getSocket() {
        return mmSocket;
    }

    private String recibir(){
        if (mmSocket==null){
            return "0";
        }

        final byte delimiter=10;
        int position=0;
        String envio="";
        byte[] readBuffer=new byte[1024];

        try{
            int bytesAvailable= mmSocket.getInputStream().available();
            if (bytesAvailable>0){
                byte[] packetBytes = new byte[bytesAvailable];
                mmSocket.getInputStream().read(packetBytes);

                for (int i=0; i<bytesAvailable; i++){
                    byte b= packetBytes[i];
                    if(b == delimiter) {
                        byte[] encodedBytes = new byte[position];
                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                        final String data = new String(encodedBytes, "US-ASCII");
                        position = 0;
                        envio=data;
                    }
                    else {
                        readBuffer[position++] = b;
                    }
                }


            }

            return envio;

        }catch (IOException e){ return "0"; }

    }

    public static String getRecibido() {
        return recibido;
    }

    public void send(int dato) {
        //Si el socket está a null es que no hemos conectado
        if (mmSocket == null)
            return;
        try {
            mmSocket.getOutputStream().write(dato);

        } catch (IOException e) {}
    }
}
