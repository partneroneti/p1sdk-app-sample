package com.projeto.projetoexemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.projeto.photoface.CallBackCapture;
import com.projeto.photoface.CallLib;
import com.projeto.photoface.MaskUtil;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    String cert =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "-----END PUBLIC KEY-----";

    String key = "";
    String urlPadrao =  "";
    String urlDocLive = "";
    String user = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean networkLocationEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Activity act = this;
        Button btn = findViewById(R.id.button2);
        EditText edt = findViewById(R.id.editTextNumber2);

        edt.addTextChangedListener(MaskUtil.insert(edt));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setEnabled(false);

                CallLib.start(act, cert, key, urlPadrao, user, password, urlDocLive);
                CallLib.call(edt.getText().toString(), new CallBackCapture() {
                    @Override
                    public void finish() {
                        btn.setEnabled(true);
                        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}