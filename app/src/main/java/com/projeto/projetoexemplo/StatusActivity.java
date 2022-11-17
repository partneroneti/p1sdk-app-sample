package com.projeto.projetoexemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projeto.photoface.CallBackStatus;
import com.projeto.photoface.CallLib;

import java.util.Timer;
import java.util.TimerTask;

public class StatusActivity extends AppCompatActivity {
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getSupportActionBar().hide();

        Button btn = findViewById(R.id.button3);
        TextView tvtransactionm = findViewById(R.id.textView4);
        tvtransactionm.setText(CallLib.transactionId);
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
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (CallLib.status == 1 || CallLib.status == 2){
                        handler.removeCallbacksAndMessages(null);
                        CallLib.callTransactionStatus(new CallBackStatus() {
                            @Override
                            public void onFinish() {
                                changeMgsStatus();
                            }
                        });
                    }else{
                        CallLib.callTransaction(new CallBackStatus() {
                            @Override
                            public void onFinish() {
                                changeMgsStatus();
                            }
                        });
                    }

//                    if (CallLib.status != 1 || CallLib.status != 2) {
//                        CallLib.callTransaction(new CallBackStatus() {
//                            @Override
//                            public void onFinish() {
//                                changeMgsStatus();
//                            }
//                        });
//                    } else {
//                        handler.removeCallbacksAndMessages(null);
//                        CallLib.callTransactionStatus(new CallBackStatus() {
//                            @Override
//                            public void onFinish() {
//                                changeMgsStatus();
//                            }
//                        });
//                    }
                }
            }, delay);
    }

    public void changeMgsStatus() {
        TextView tv = findViewById(R.id.textView6);
        switch (CallLib.status) {
            case 0: {
                tv.setText("Transação em processamento");
                break;
            }
            case 1:
                tv.setText("Transação Aprovada");
                tv.setTextColor(Color.parseColor("#FF69B332"));
                break;
            case 2:
                tv.setText("Transação Reprovada");
                tv.setTextColor(Color.parseColor("#D32500"));
                break;
            default:{
                tv.setText("Transação em processamento");
                break;
            }

        }
    }
}