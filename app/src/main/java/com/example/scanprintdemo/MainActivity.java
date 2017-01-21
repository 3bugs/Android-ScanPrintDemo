package com.example.scanprintdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SCAN_ACTION = "urovo.rcv.message";

    private ScanManager mScanManager;
    //private SoundPool mSoundPool;
    //private int mSoundId;

    private TextView mResultTextView;

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //isScaning = false;
            //mSoundPool.play(mSoundId, 1, 1, 0, 0, 1);
            mResultTextView.setText("");
            //mVibrator.vibrate(100);

            byte[] barcode = intent.getByteArrayExtra("barocode");
            //byte[] barcode = intent.getByteArrayExtra("barcode");
            int barcodeLength = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            Log.i(TAG, "--- barcode type ---" + temp);
            String barcodeText = new String(barcode, 0, barcodeLength);
            mResultTextView.setText(barcodeText);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTextView = (TextView) findViewById(R.id.result_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScan();
        mResultTextView.setText("");
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mScanManager != null) {
            mScanManager.stopDecode();
            //isScaning = false;
        }
        unregisterReceiver(mScanReceiver);
    }

    private void initScan() {
        // TODO Auto-generated method stub
        mScanManager = new ScanManager();
        mScanManager.openScanner();
        mScanManager.switchOutputMode(0);

        //mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        //mSoundId = mSoundPool.load("/etc/Scan_new.ogg", 1);
    }
}
