package com.example.scanprintdemo;

import android.app.IntentService;
import android.content.Intent;
import android.device.PrinterManager;
import android.util.Log;

/**
 * Created by Promlert on 2017-01-21.
 */

public class PrintBillService extends IntentService {

    private PrinterManager mPrinter;
    public static final String KEY_PRINT_DATA = "data";

    public PrintBillService() {
        super("bill");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPrinter = new PrinterManager();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getStringExtra(KEY_PRINT_DATA);
        if (data == null || data.equals("")) return;

        mPrinter.setupPage(384, -1);
        int ret = mPrinter.drawTextEx(data, 5, 0, 300, -1, "arial", 30, 0, 0, 0);
        android.util.Log.i("debug", "ret:" + ret);
        //ret += mPrinter.drawTextEx(data, 5, ret,300,-1, "arial", 25, 0, 0x0001, 1);
        //ret += mPrinter.drawTextEx(data, 5, ret,-1,-1, "arial", 25, 0, 0x0008, 0);
        ret += mPrinter.drawTextEx(data, 300, ret, -1, -1, "arial", 25, 1, 0, 0);
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