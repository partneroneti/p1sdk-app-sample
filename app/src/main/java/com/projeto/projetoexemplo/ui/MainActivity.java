package com.projeto.projetoexemplo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.projeto.photoface.CallLib;
import com.projeto.photoface.MaskUtil;
import com.projeto.photoface.callback.CallbackStatus;
import com.projeto.photoface.entity.body.Document;
import com.projeto.projetoexemplo.api.ApiService;
import com.projeto.projetoexemplo.R;
import com.projeto.projetoexemplo.api.entity.callback.OnDocumentListener;
import com.projeto.projetoexemplo.api.entity.callback.OnFaceListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    Button btn;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btn = findViewById(R.id.button2);
        edt = findViewById(R.id.editTextNumber2);

        mContext = this;

        edt.addTextChangedListener(MaskUtil.insert(edt));

        btn.setOnClickListener(view -> {
            btn.setEnabled(false);

            initApi();
        });
    }

    private void initApi() {
        ApiService.call(edt.getText().toString());
        ApiService.onFinish(new CallbackStatus() {
            @Override
            public void onFinish() {
                Intent intent = new Intent(mContext, StatusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
        ApiService.onFaceStartListener(new OnFaceListener() {
            @Override
            public void init() {
                initStepFace();
            }
        });
        ApiService.onDocumentStartListener(new OnDocumentListener() {
            @Override
            public void init() {
                initStepDocument();
            }
        });
    }

    private void initStepFace() {
        startPhotoFace();
        setFaceListener();
    }

    private void startPhotoFace() { // Inicializa a lib
        CallLib.startFaceCapture(
                mContext,
                ApiService.facetecCredentialsObj.getCertificate(),
                ApiService.facetecCredentialsObj.getDeviceKeyIdentifier(),
                ApiService.facetecCredentialsObj.getProductionKeyText(),
                ApiService.session
        );
    }

    private void setFaceListener() { // cria o listener para a captura de selfie
        CallLib.faceListener(
                (faceScan, auditTrailImage, lowQualityAuditTrailImage) -> { // faça algo com as imagens da selfie

                    ApiService.callLiveSession(faceScan, auditTrailImage, lowQualityAuditTrailImage);

                    logFace(faceScan, auditTrailImage, lowQualityAuditTrailImage);
                });
    }

    private void initStepDocument() {
        startDocumentCapture();
        setDocumentListener();
    }

    private void startDocumentCapture() { // inicializa a captura do documento
        CallLib.startDocumentCapture(mContext);
    }

    private void setDocumentListener() {
        CallLib.docListener(
                (documentList) -> {
                    ApiService.sendDocument(documentList);
                    logDoc(documentList); // faça algo com as imagens do documento
                });
    }

    private void logFace(
            String faceScan,
            String auditTrailImage,
            String lowQualityAuditTrailImage
    ) {
        Log.d("MainActivity", "" +
                "onCapturedFace: " +
                " faceScan ->" + faceScan +
                " auditTrailImage ->" + auditTrailImage +
                " lowQualityAuditTrailImage ->" + lowQualityAuditTrailImage
        );
    }

    private void logDoc(List<Document> documentList) {
        List<Document> list = documentList;
        list.get(0).getType();

        Log.d("MainActivity",
                "onCapturedDocument: " +
                        "document0 -> " + list.get(0).getType() + ":" + list.get(0).getByte() +
                        "document1 -> " + list.get(1).getType() + ":" + list.get(0).getByte());
    }
}