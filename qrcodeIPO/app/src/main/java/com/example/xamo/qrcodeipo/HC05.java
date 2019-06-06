package com.example.xamo.qrcodeipo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class HC05 {
    private String DEVICE_ADDRESS="98:D3:31:F9:40:C8";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Context context;
    private Activity activity;
    private boolean deviceConnected=false;
    private boolean stopThread;
    private byte buffer[];

    public HC05(String DEVICE_ADDRESS, Context context, Activity activity) {
        this.DEVICE_ADDRESS = DEVICE_ADDRESS;
        this.context=context;
        this.activity=activity;
    }
    public boolean isConnected(){
        return deviceConnected;
    }
    public boolean init(){
        boolean correct=false;
        if(BTinit())
        {
            if(BTconnect())
            {
                correct=true;
                deviceConnected=true;

            }

        }
        return correct;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context,"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(context,"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                Log.e("ITERATOR:", iterator.getAddress());
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    Toast.makeText(context, "Found!", Toast.LENGTH_SHORT).show();
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    private boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }
    public void stopConnection(){
        stopThread = true;
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        deviceConnected=false;
    }
    public void sendData(String data){
        data.concat("\n");
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void beginListenForData(final String dataRecieved[], final Semaphore semaphore)
    {

    }
}