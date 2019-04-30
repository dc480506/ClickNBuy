package com.example.clicknbuy;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b;
    Button p;
    ImageView img;
    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.capture);
        p=(Button)findViewById(R.id.proceed);
        p.setVisibility(View.GONE);
        img=(ImageView)findViewById(R.id.ImgView);
        onClickCapture();
        onCLickProceed();
    }

    private void onCLickProceed() {
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(".Activity2");
               startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
        a.setMessage("Are you sure you want to exit?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setCancelable(true);
        final AlertDialog p=a.create();
        p.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                p.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ff0000"));
                p.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008000"));
            }
        });

        p.setTitle("Exit");
        p.show();
    }

    public void onClickCapture(){
       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                startCamera();
                try {
                    Thread.sleep(1000);
                }catch(Exception e){

                }
               b.setText("Retake");
                p.setVisibility(View.VISIBLE);
           }
       });
   }

    /*public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);


        //  Toast.makeText(this, "Photo not capture", Toast.LENGTH_SHORT).show();


    }
    public void startCamera(){
        try {
            startActivityForResult(intent, 0);
        }catch(Exception e){
            AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
            a.setMessage("Camera is not responding!! Retry?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(true);
        }
    }
}
