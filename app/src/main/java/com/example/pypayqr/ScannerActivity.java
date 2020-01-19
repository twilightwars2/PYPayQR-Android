package com.example.pypayqr;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.widget.Toast.*;


public class ScannerActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView remind,result;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    Button copybtn, URLbtn;
    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        getWindow().setWindowAnimations(0);

        getPermissionsCamera();

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        remind = (TextView) findViewById(R.id.scannerremind);
        result=(TextView)findViewById(R.id.result);
        copybtn = (Button) findViewById(R.id.copybtn);
        URLbtn = (Button) findViewById(R.id.openurlbtn);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);








        copybtn.setVisibility(View.INVISIBLE);
        URLbtn.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);


        final ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(300, 300).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceholder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceholder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {


                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    remind.post(new Runnable() {
                        @Override
                        public void run() {
                            result.setText(qrCodes.valueAt(0).displayValue);
                            cameraSource.stop();
                            remind.setVisibility(View.INVISIBLE);
                            copybtn.setVisibility(View.VISIBLE);
                            URLbtn.setVisibility(View.VISIBLE);
                            result.setVisibility(View.VISIBLE);

                            copybtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipData myClip;
                                    String text = String.valueOf(qrCodes.valueAt(0).displayValue);
                                    myClip = ClipData.newPlainText("text", text);
                                    myClipboard.setPrimaryClip(myClip);
                                    Toast.makeText(ScannerActivity.this, "Copy the text successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            URLbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String open;
                                    String url = String.valueOf(qrCodes.valueAt(0).displayValue);
                                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                        open = "http://" + url;
                                    } else open = qrCodes.valueAt(0).displayValue;
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(open));
                                    startActivity(intent);
                                }
                            });


                        }
                    });
                }

            }

        });

    }


    public void getPermissionsCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ScannerActivity.this, "Please allow PYpay QR to access your camera!", LENGTH_LONG).show();
        }
    }
}