package com.example.clicknbuy;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Activity2 extends AppCompatActivity {
    Button b;
    WebView w;
    String url;
    Vision vision;
    String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Vision.Builder visionBuilder=new Vision.Builder(new NetHttpTransport(),new AndroidJsonFactory(),null);
        visionBuilder.setVisionRequestInitializer(new VisionRequestInitializer("AIzaSyBazkm-MkGGM3CbbAP-xm0TICnktVUiYSY"));
         vision=visionBuilder.build();
        Log.d("TESTTHIS",vision.toString());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(Activity2.this, "Ehre", Toast.LENGTH_SHORT).show();
                Drawable d = getResources().getDrawable(R.drawable.download);
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] photodata = stream.toByteArray();
                Image inputImage = new Image();
                inputImage.encodeContent(photodata);
                Feature desiredFeature = new Feature();
                desiredFeature.setType("LABEL_DETECTION");
                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(inputImage);
                request.setFeatures(Arrays.asList(desiredFeature));
                BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
                batchRequest.setRequests(Arrays.asList(request));
                try {
                    BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();
                    List<AnnotateImageResponse> response=batchResponse.getResponses();
                   /* for(AnnotateImageResponse res: response){
                        for(EntityAnnotation annotation: res.getLabelAnnotations()){
                            annotation.
                        }*/
                   int n=response.size();
                   for(int i=0;i<n;i++){
                       message+=response.get(i).getLabelAnnotations().toString()+"\n";
                   }

                    Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
                    }
                catch (Exception e){}

            }
        });

        open_URL();
    }
    public void open_URL(){

       b=(Button)findViewById(R.id.open);
       w=(WebView)findViewById(R.id.Wbview);
       url="https://www.amazon.in/s?k=hp+laptop&ref=nb_sb_noss_2";
       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               w.getSettings().setLoadsImagesAutomatically(true);
               w.getSettings().setJavaScriptEnabled(true);
               w.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
               w.loadUrl(url);
           }
       });
    }
}
