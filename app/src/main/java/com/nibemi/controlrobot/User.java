package com.nibemi.controlrobot;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

public class User {
    private static BluetoothDevice bluetoothDevice;
    private static Context context;
    private static BluetoothControl bluetoothControl;

    public static BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public static void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        User.bluetoothDevice = bluetoothDevice;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        User.context = context;
    }

    public static BluetoothControl getBluetoothControl() {
        return bluetoothControl;
    }

    public static void setBluetoothControl(BluetoothControl bluetoothControl) {
        User.bluetoothControl = bluetoothControl;
    }
}
