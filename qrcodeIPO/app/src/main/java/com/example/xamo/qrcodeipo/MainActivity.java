package com.example.xamo.qrcodeipo;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.camerakit.CameraKitView;

import java.io.IOException;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class MainActivity extends AppCompatActivity {
    private int menuIndex=0;
    private TextView tv1, tv2, tv3, timer;
    private ImageView left_arrow, rigth_arrow, home, record, recordSmall,close;
    private LinearLayout menu, camera;
    private CardView cv1,cv2,cv3;
    private CameraKitView cameraKitView;
    private LinearLayout gallery;
    private ImageView stop;
    private Chrono chrono;
    private Animation pulse;
    private Activity activity;
    private HC05 hc05;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIds();
        hc05=new HC05("98:D3:31:F9:40:C8",getApplicationContext(),this);
        if(hc05.init()){
            Toast.makeText(getApplicationContext(),"CONNECTED!", Toast.LENGTH_LONG).show();
            getData();
        }else{

            Toast.makeText(getApplicationContext(),"ERROR CONNECTING!",Toast.LENGTH_LONG).show();

        }
    }

    private void setIds() {
        timer=findViewById(R.id.timer);
        chrono= new Chrono(timer, true);
        activity=this;
        pulse= AnimationUtils.loadAnimation(this, R.anim.pulse);

        stop=findViewById(R.id.stop_image);
        stop.startAnimation(pulse);
        cameraKitView = findViewById(R.id.cameraView);
        tv1=findViewById(R.id.TextView1_main);
        tv2=findViewById(R.id.TextView2_main);
        tv3=findViewById(R.id.TextView3_main);
        cv1=findViewById(R.id.cardView1);
        close=findViewById(R.id.close_button);
        cv2=findViewById(R.id.cardView2);
        cv3=findViewById(R.id.cardView3);
        home=findViewById(R.id.buttonHome);
        gallery=findViewById(R.id.grid_layout);
        menu=findViewById(R.id.menu_layout);
        camera=findViewById(R.id.layout_camara);
        record=findViewById(R.id.recordButton);
        recordSmall=findViewById(R.id.recordButtonSmall);
        left_arrow=findViewById(R.id.left_arrow);
        rigth_arrow=findViewById(R.id.rigth_arrow);
        listeners();
    }

    private void listeners() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menu.getVisibility()==View.INVISIBLE){
                    menu.setVisibility(View.VISIBLE);
                }else{
                    menu.setVisibility(View.INVISIBLE);
                }
            }
        });
        rigth_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left_arrow.setVisibility(View.VISIBLE);
                menuIndex++;
                switch (menuIndex){
                    case 1:
                        tv1.setText("Cámara");
                        tv2.setText("Mapas");
                        tv3.setText("Traductor");
                        break;
                    case 2:
                        tv1.setText("Mapas");
                        tv2.setText("Traductor");
                        tv3.setText("Comprar objetos");
                        rigth_arrow.setVisibility(View.INVISIBLE);
                        break;
                }

            }
        });
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigth_arrow.setVisibility(View.VISIBLE);
                menuIndex--;
                switch (menuIndex){
                    case 0:
                        tv1.setText("Galería");
                        tv2.setText("Cámara");
                        tv3.setText("Mapas");
                        left_arrow.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tv1.setText("Cámara");
                        tv2.setText("Mapas");
                        tv3.setText("Traductor");
                        break;
                }
            }
        });

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(tv1.getText().toString()){
                    case "Galería":
                        galeria();
                        break;
                    case "Cámara":
                        camara();
                        break;
                }
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(tv2.getText().toString()){
                    case "Cámara":
                        camara();
                        break;
                }
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(tv3.getText().toString()){
                    case "Comprar objetos":
                        startActivity(new Intent(activity, InfoProducts.class));
                        break;

                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setVisibility(View.VISIBLE);
                record.setVisibility(View.INVISIBLE);
                home.setVisibility(View.INVISIBLE);
                chrono.startChrono();
            }
        });
        recordSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setVisibility(View.INVISIBLE);
                chrono.stopChrono();
                home.setVisibility(View.VISIBLE);
                chrono.restore();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void camara(){
        menu.setVisibility(View.INVISIBLE);
        record.setVisibility(View.VISIBLE);
    }

    private void galeria(){
        menu.setVisibility(View.INVISIBLE);
        gallery.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setVisibility(boolean value){
        if(value) {
            stop.setVisibility(View.VISIBLE);
            stop.startAnimation(pulse);
            //stop.requestLayout();


        }else {

            stop.clearAnimation();
            stop.setVisibility(View.INVISIBLE);
        }
        //stop.postInvalidate();
    }

    private void getData(){
        hc05.sendData("r");
        final Handler handler = new Handler();
        final boolean[] stopThread = {false};
        byte buffer[] = new byte[1024];
        final String[] data = {""};

        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread[0])
                {
                    try
                    {
                        int byteCount = hc05.getInputStream().available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            hc05.getInputStream().read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    data[0] = data[0].concat(string);
                                    char c=data[0].charAt(data[0].length()-1);
                                    if(c=='s'){
                                        setVisibility(true);
                                    }else{
                                        setVisibility(false);
                                    }
                                }
                            });
                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread[0] = true;
                    }
                }
            }
        });
        thread.start();
    }


}