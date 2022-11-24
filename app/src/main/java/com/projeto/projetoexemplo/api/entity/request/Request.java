package com.projeto.projetoexemplo.api.entity.request;


import com.projeto.projetoexemplo.api.entity.body.AuthenticationBody;
import com.projeto.projetoexemplo.api.entity.body.Cpf;
import com.projeto.projetoexemplo.api.entity.body.DocumentBody;
import com.projeto.projetoexemplo.api.entity.body.LivenessTBody;
import com.projeto.projetoexemplo.api.entity.response.AuthObj;
import com.projeto.projetoexemplo.api.entity.response.CpfObj;
import com.projeto.projetoexemplo.api.entity.response.LivenessResponse;
import com.projeto.projetoexemplo.api.entity.response.SessionLiveResponse;
import com.projeto.projetoexemplo.api.entity.response.StatusObj;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Request {

    @POST("authentication")
    Call<AuthObj> authentication(@Body AuthenticationBody user);

    @POST("transaction")
    Call<CpfObj> cpfStatus(@Body Cpf cpf, @Header("Authorization") String auth);

    @GET("transaction/{transactionId}")
    Call<StatusObj> checkStatus(@Path("transactionId") String transactionId, @Header("Authorization") String auth);

    @POST("document")
    Call<DocumentBody> sendDocument(@Body DocumentBody documentBody,@Header("Authorization") String auth);

    @GET("session")
    Call<SessionLiveResponse> sessionLive(@Header("Authorization") String auth);

    @POST("liveness")
    Call<LivenessResponse> liveness(@Body LivenessTBody documentBody, @Header("Authorization") String auth);

    @GET("session")
    Call<SessionLiveResponse> createSession(@Header("X-Device-Key") String deviceKey,@Header("User-Agent") String userAgent,
                               @Header("Authorization") String auth);
}
