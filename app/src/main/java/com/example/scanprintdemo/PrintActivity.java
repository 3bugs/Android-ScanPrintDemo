package com.example.scanprintdemo;

import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PrintActivity extends AppCompatActivity {

    private static final int GRAY_LEVEL = 4;
    private static final int SPEED_LEVEL = 9;

    private PrinterManager mPrinter = new PrinterManager();

    private EditText mEditText;
    private Button mPrintTextButton, mPrintImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        // เข้าถึงตัวแปร global ที่อยู่ในคลาส App
        App.getInstance().data = "xxx";

        mPrinter.setGrayLevel(GRAY_LEVEL);
        mPrinter.setSpeedLevel(SPEED_LEVEL);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mPrintTextButton = (Button) findViewById(R.id.print_text_button);
        mPrintImageButton = (Button) findViewById(R.id.print_image_button);

        mPrintTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mEditText.getText().toString();
                //doPrintWork(data);
                doPrintWork2(data);
            }
        });

        mPrintImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrinter.setupPage(384, -1);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
                opts.inDensity = getResources().getDisplayMetrics().densityDpi;
                opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, opts);
                mPrinter.drawBitmap(img, 30, 0);

                int ret = mPrinter.printPage(0);

                Intent i = new Intent("android.prnt.message");
                i.putExtra("ret", ret);
                sendBroadcast(i);
            }
        });
    }

    void doPrintWork(String msg) {
        Intent intentService = new Intent(this, PrintBillService.class);
        intentService.putExtra(PrintBillService.KEY_PRINT_DATA, msg);
        startService(intentService);
    }

    void doPrintWork2(String msg) {
        Toast.makeText(this, "doPrintWork2", Toast.LENGTH_LONG).show();

        mPrinter.setupPage(384, -1);
        int ret = mPrinter.drawTextEx(msg, 5, 0, 300, -1, "arial", 30, 0, 0, 0);
        android.util.Log.i("debug", "ret:" + ret);
        //ret += mPrinter.drawTextEx(data, 5, ret,300,-1, "arial", 25, 0, 0x0001, 1);
        //ret += mPrinter.drawTextEx(data, 5, ret,-1,-1, "arial", 25, 0, 0x0008, 0);
        ret += mPrinter.drawTextEx(msg, 300, ret, -1, -1, "arial", 25, 1, 0, 0);
        //ret +=mPrinter.drawTextEx(data, 0, ret,-1,-1, "/system/fonts/DroidSans-Bold.ttf", 25, 0, 0, 0);
        //ret +=mPrinter.drawTextEx(data, 0, ret,-1,-1, "/system/fonts/kaishu.ttf", 25, 0, 0x0001, 0);
        Log.i("debug", "ret:" + ret);
        //mPrinter.drawTextEx(data, 5, 60,160,-1, "arial", 25, 0, 0x0001 |0x0008, 0);
        //mPrinter.drawTextEx(data, 180, 0,160,-1, "arial", 25, 1, 0x0008, 0);
        //mPrinter.drawTextEx(data, 300, 30,160,-1, "arial", 25, 2, 0x0008, 0);
        //mPrinter.drawTextEx(data, 300, 160,160,-1, "arial", 25, 3, 0x0008, 0);
        //mPrinter.drawTextEx(data, 0, 0,160,-1, "arial", 25, 1, 0x0008, 0);
        //mPrinter.drawTextEx(data, 160, 30,200,-1, "arial", 28, 0, 2,1);
        //mPrinter.drawTextEx(data, 0, 180,-1,-1, "arial", 28, 0, 2,1);
        ret = mPrinter.printPage(0);

        Intent i = new Intent("android.prnt.message");
        i.putExtra("ret", ret);
        this.sendBroadcast(i);
    }
}
