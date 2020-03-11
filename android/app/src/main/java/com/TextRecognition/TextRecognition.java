package com.TextRecognition;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextRecognition extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactApplicationContext;
    private static final String LENGTH_LONG = "LENGTH_LONG";
    private static final String LENGTH_SHORT = "LEGTH_SHORT";
    public TextRecognition(@NonNull ReactApplicationContext reactContext, ReactApplicationContext reactApplicationContext) {

        super(reactContext);
        this.reactApplicationContext = reactApplicationContext;
    }


    @Nullable
    @Override
    public Map<String, Object> getConstants() {
       final Map<String,Object> constants  = new HashMap<>();
       constants.put("PI","3.1416");
       constants.put(LENGTH_LONG,Toast.LENGTH_LONG);
       constants.put(LENGTH_SHORT,Toast.LENGTH_SHORT);
       return constants;
    }

    @NonNull
    @Override
    public String getName() {
        return "TextRecognition";
    }
    @ReactMethod
    public void showText(String message,int duration){
     Toast.makeText(getReactApplicationContext(),message,duration).show();
    }
    @ReactMethod
    public void imageFromPath(String uri, Callback callback) {

        FirebaseVisionImage image;
        try {
            image = FirebaseVisionImage.fromFilePath(this.reactApplicationContext, Uri.parse(uri));
       //detector image
            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();
            //processing part
            Task<FirebaseVisionText> result =
                    detector.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                    // Task completed successfully
                                    // ...

                                    callback.invoke(firebaseVisionText.getText());
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                            // ...
                                         showText("error",Toast.LENGTH_LONG);
                                        }
                                    });


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
