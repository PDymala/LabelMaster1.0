package com.example.labelmaster10;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Application for verification of genuine of a specially designed label
 *
 * @author Piotr Dymala p.dymala@gmail.com
 * @version 1.0
 * @since 2020-06-03
 */


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LabelMaster";
    Button buttonScanQR;
    Button buttonScanDM;
    Button buttonReset;
    TextView textQRStatus;
    TextView textDMStatus;
    TextView textCodesVerif;
    View viewColoredBar;
    View viewColoredBar2;
    private String qrValue = "";
    private String dmValue = "";
    private Hash hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        permission();


    }

    private void permission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);

        }




    }




    public void init() {

        buttonScanQR = (Button) findViewById(R.id.buttonScanQR);
        buttonScanDM = (Button) findViewById(R.id.buttonScanDM);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        textQRStatus = (TextView) findViewById(R.id.textQRStatus);
        textDMStatus = (TextView) findViewById(R.id.textDMStatus);
        textCodesVerif = (TextView) findViewById(R.id.textCodesVerif);
        viewColoredBar = (View) findViewById(R.id.colored_bar);
        viewColoredBar2 = (View) findViewById(R.id.colored_bar2);
        hash = new Hash();
        viewColoredBar.setBackgroundColor(Color.WHITE);
        viewColoredBar2.setBackgroundColor(Color.WHITE);


    }

    public void scanQRCode(View view) {


        int LAUNCH_SECOND_ACTIVITY = 100;
        Intent i = new Intent(this, CameraActivity.class);
        i.putExtra("CodeType", "QR");
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);


    }

    public void scanDMCode(View view) {
        int LAUNCH_SECOND_ACTIVITY = 200;
        Intent i = new Intent(this, CameraActivity.class);
        i.putExtra("CodeType", "DM");
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);


    }

    public void checkCodes() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (qrValue.isEmpty() || dmValue.isEmpty()) {
            textCodesVerif.setText("Scan codes");
        } else {

            String tempHash = hash.getHash(qrValue);

            if (dmValue.equals(tempHash)) {
                textCodesVerif.setText("Codes match");
                viewColoredBar.setBackgroundColor(Color.GREEN);
                viewColoredBar2.setBackgroundColor(Color.GREEN);
            } else {
                textCodesVerif.setText("Codes do not match");
                viewColoredBar.setBackgroundColor(Color.RED);

                viewColoredBar2.setBackgroundColor(Color.RED);

            }


        }

    }

    public void reset(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        textQRStatus.setText("X");
        textDMStatus.setText("X");
        qrValue = "";
        dmValue = "";
        viewColoredBar.setBackgroundColor(Color.WHITE);
        viewColoredBar2.setBackgroundColor(Color.WHITE);

        checkCodes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {

                qrValue = data.getStringExtra("result");
                try {
                    checkCodes();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                textQRStatus.setText("OK");



            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

        } else if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
               dmValue = data.getStringExtra("result");
                try {
                    checkCodes();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                textDMStatus.setText("OK");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }

        }

    }
}