package com.example.scanprintdemo;

import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PrintActivity extends AppCompatActivity {

    private PrinterManager mPrinter = new PrinterManager();

    private EditText mEditText;
    private Button mPrintTextButton, mPrintImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mPrintTextButton = (Button) findViewById(R.id.print_text_button);
        mPrintImageButton = (Button) findViewById(R.id.print_image_button);

        mPrintTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mEditText.getText().toString();
                doPrintWork(data);
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
}
