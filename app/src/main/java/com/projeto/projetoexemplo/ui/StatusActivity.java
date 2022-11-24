package com.projeto.projetoexemplo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projeto.photoface.CallLib;
import com.projeto.photoface.callback.CallbackStatus;
import com.projeto.projetoexemplo.R;
import com.projeto.projetoexemplo.api.ApiService;

public class StatusActivity extends AppCompatActivity {
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getSupportActionBar().hide();

        Button btn = findViewById(R.id.button3);
        TextView tvtransactionm = findViewById(R.id.textView4);
        tvtransactionm.setText(ApiService.transaction.getTransactionId());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        changeMgsStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAsynchronousTask();
    }

    public void callAsynchronousTask() {
        final int delay = 10000; // 1000 milliseconds == 1 second
        handler.postDelayed(() -> {
            if (ApiService.status == 1 || ApiService.status == 2) {
                handler.removeCallbacksAndMessages(null);
                    ApiService.callTransactionStatus(this::changeMgsStatus);
            } else {
                ApiService.callTransaction(new CallbackStatus() {
                        @Override
                        public void onFinish() {
                            changeMgsStatus();
                        }
                    });
            }
        }, delay);
    }

    public void changeMgsStatus() {
        TextView tv = findViewById(R.id.textView6);
        switch (ApiService.status) {
            case 1:
                tv.setText("Transação Aprovada");
                tv.setTextColor(Color.parseColor("#FF69B332"));
                break;
            case 2:
                tv.setText("Transação Reprovada");
                tv.setTextColor(Color.parseColor("#D32500"));
                break;
            default: {
                tv.setText("Transação em processamento");
                break;
            }

        }
    }
}