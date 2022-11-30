package com.projeto.projetoexemplo.api;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.gson.Gson;
import com.projeto.photoface.CallLib;
import com.projeto.photoface.callback.CallbackStatus;
import com.projeto.photoface.entity.body.Document;
import com.projeto.projetoexemplo.api.entity.body.AuthenticationBody;
import com.projeto.projetoexemplo.api.entity.body.Cpf;
import com.projeto.projetoexemplo.api.entity.body.DocumentBody;
import com.projeto.projetoexemplo.api.entity.body.LivenessTBody;
import com.projeto.projetoexemplo.api.entity.callback.OnDocumentListener;
import com.projeto.projetoexemplo.api.entity.callback.OnFaceListener;
import com.projeto.projetoexemplo.api.entity.request.Request;
import com.projeto.projetoexemplo.api.entity.response.FacetecCredentialsResponse;
import com.projeto.projetoexemplo.api.entity.response.obj.FacetecCredentialsObj;
import com.projeto.projetoexemplo.api.entity.response.AuthenticationResponse;
import com.projeto.projetoexemplo.api.entity.response.obj.CpfObj;
import com.projeto.projetoexemplo.api.entity.response.CpfResponse;
import com.projeto.projetoexemplo.api.entity.response.LivenessResponse;
import com.projeto.projetoexemplo.api.entity.response.SessionLiveResponse;
import com.projeto.projetoexemplo.api.entity.response.StatusResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String baseUrl =  "";
    private static final String user = "";
    private static final String password = "";

    private static final Request service = getRetrofit(baseUrl).create(Request.class);

    public static Integer status;

    public static AuthenticationResponse authResponse;
    private static Cpf cpf = new Cpf();
    public static CpfResponse transaction;
    public static FacetecCredentialsObj facetecCredentialsObj;
    public static String session;
    private static CallbackStatus callback;

    private static OnDocumentListener onDocumentListener;
    private static OnFaceListener onFaceListener;

    private static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void onFaceStartListener(OnFaceListener listener) {
        onFaceListener = listener;
    }

    public static void onDocumentStartListener(OnDocumentListener listener) {
        onDocumentListener = listener;
    }

    public static void onFinish(CallbackStatus callbackStatus) {
        callback = callbackStatus;
    }

    public static void call(String cpfInput) {

        cpf = new Cpf();
        cpf.setCpf(cpfInput);

        AuthenticationBody ab = new AuthenticationBody();
        ab.setGrantType("password");
        ab.setPassword(password);
        ab.setUsername(user);
        Call<AuthenticationResponse> callAuth = service.authentication(ab);

        callAuth.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                authResponse = response.body();
                callCpf();
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.w(TAG, "onFailure");
            }
        });
    }

    private static void callCpf() {
        Call<CpfObj> callCpf = service.cpfStatus(cpf, "Bearer " + authResponse.getObjectReturn().getAccessToken());
        callCpf.enqueue(new Callback<CpfObj>() {
            @Override
            public void onResponse(Call<CpfObj> call, Response<CpfObj> response) {

                transaction = response.body().getObjectReturn();

                getFacetecCredentials();

            }

            @Override
            public void onFailure(Call<CpfObj> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void createSession(String deviceKey, Runnable onFinish){
        Call<SessionLiveResponse> callSession = service.createSession(deviceKey,"Bearer " + authResponse.getObjectReturn().getAccessToken());
        callSession.enqueue(new Callback<SessionLiveResponse>() {
            @Override
            public void onResponse(Call<SessionLiveResponse> call, Response<SessionLiveResponse> response) {
                session = response.body().getObjectReturn().getSession();
                onFinish.run();
            }

            @Override
            public void onFailure(Call<SessionLiveResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void getFacetecCredentials(){
        Call<FacetecCredentialsResponse> callFacetecCredentialsResponse = service.facetecCredentials("Bearer " + authResponse.getObjectReturn().getAccessToken());
        callFacetecCredentialsResponse.enqueue(new Callback<FacetecCredentialsResponse>() {
            @Override
            public void onResponse(Call<FacetecCredentialsResponse> call, Response<FacetecCredentialsResponse> response) {

                facetecCredentialsObj = response.body().getObjectReturn();

                //cria a sessao
                createSession(ApiService.facetecCredentialsObj.getDeviceKeyIdentifier(),
                        new Runnable(){

                            @Override
                            public void run() {

                                callTransaction(() -> {
                                });
                            }
                        });
            }

            @Override
            public void onFailure(Call<FacetecCredentialsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void callTransaction(CallbackStatus callbackStatus) {
        Call<StatusResponse> callStatus = service.checkStatus(
                transaction.getTransactionId(),
                "Bearer " + authResponse.getObjectReturn().getAccessToken()
        );
        callStatus.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                status = response.body().getObjectReturn().getResult().getStatus();
                switch (status) {
                    case 0:// transaçao em processamento
                    case 1:// transaçao aprovada
                    case 2:// transaçao reprovada
                        callback.onFinish();
                        callbackStatus.onFinish();
                        break;
                    case 3:
                        //FaceTecSDK.getStatus(contextApp)
                        onFaceListener.init();
                        break;
                    case 4:
                        onDocumentListener.init();
                        break;
                    default:
                        callback.onFinish();
                        break;// Status indefinido
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }

    public static void callTransactionStatus(CallbackStatus callbackStatus) {
        Call<StatusResponse> callStatus = service.checkStatus(transaction.getTransactionId(), "Bearer " + authResponse.getObjectReturn().getAccessToken());
        callStatus.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                status = response.body().getObjectReturn().getResult().getStatus();
                callbackStatus.onFinish();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }

    public static void callLiveSession(
            String faceScan,
            String auditTrailImage,
            String lowQualityAuditTrailImage
    ) {
        Call<SessionLiveResponse> callSession = service.sessionLive("Bearer " + authResponse.getObjectReturn().getAccessToken());
        callSession.enqueue(new Callback<SessionLiveResponse>() {
            @Override
            public void onResponse(Call<SessionLiveResponse> call, Response<SessionLiveResponse> response) {
                liveNess(faceScan, auditTrailImage, lowQualityAuditTrailImage);
            }

            @Override
            public void onFailure(Call<SessionLiveResponse> call, Throwable t) {

            }
        });
    }

    public static void liveNess(
            String faceScan,
            String auditTrailImage,
            String lowQualityAuditTrailImage
    ) {

        LivenessTBody live = new LivenessTBody();
        live.setTransactionId(transaction.getTransactionId());
        live.setFaceScan(faceScan);
        live.setAuditTrailImage(auditTrailImage);
        live.setLowQualityAuditTrailImage(lowQualityAuditTrailImage);
        live.setSessionId(CallLib.createUserAgentForSession(session));
        live.setDeviceKey(facetecCredentialsObj.getDeviceKeyIdentifier());
        new Gson().toJson(live);
        Call<LivenessResponse> callLive = service.liveness(live, "Bearer " + authResponse.getObjectReturn().getAccessToken());
        callLive.enqueue(new Callback<LivenessResponse>() {
            @Override
            public void onResponse(Call<LivenessResponse> call, Response<LivenessResponse> response) {

                if (response.body() != null && response.body().getObjectReturn().getCode() != null) {

                    switch (response.body().getObjectReturn().getCode()) {
                        case 3: {
                            status = 2;
                            break;
                        }
                        case 1: {
                            status = 0;
                        }
                    }
                }
                callback.onFinish();
            }

            @Override
            public void onFailure(Call<LivenessResponse> call, Throwable t) {
                Log.i("err", "err");
                callback.onFinish();
            }
        });
    }

    public static void sendDocument(List<Document> listDoc) {

        DocumentBody db = new DocumentBody();
        db.setTransactionId(transaction.getTransactionId());
        db.setDocumentos(listDoc);
        status = 0;

        Call<DocumentBody> sendDocument = service.sendDocument(
                db,
                "Bearer " + authResponse.getObjectReturn().getAccessToken());
        sendDocument.enqueue(new Callback<DocumentBody>() {
            @Override
            public void onResponse(Call<DocumentBody> call, Response<DocumentBody> response) {
                callback.onFinish();
            }

            @Override
            public void onFailure(Call<DocumentBody> call, Throwable t) {
                Log.i(TAG, "sendDocument: onFailure");
                callback.onFinish();
            }
        });
    }

}
