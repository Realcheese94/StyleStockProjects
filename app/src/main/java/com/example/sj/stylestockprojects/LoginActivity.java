package com.example.sj.stylestockprojects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.network.KakaoRequest;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class LoginActivity extends AppCompatActivity {
    private com.kakao.usermgmt.LoginButton btnKakao;
    private SessionCallback callback; //위의 콜백함수를 만들어주셔야 사용가능합니다. 위의 함수를 만들어주세요~~

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callback = new SessionCallback(); //세션콜백을 부르고
        Session.getCurrentSession().addCallback(callback); // 추가시키면 끝입니다!!



        btnKakao = (com.kakao.usermgmt.LoginButton) findViewById(R.id.btn_kakao);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();

    }



    private class SessionCallback implements ISessionCallback{

        @Override
        public void onSessionOpened() {
            Kakaorequestme();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception !=null){
                Log.d("ErrorSession",exception.getMessage());
            }

        }
    }

    public void Kakaorequestme(){
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.d("Error", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("Error","오류로 로그인 실패입니다.")
;            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile",result.toString());
            }
        });
    }
}
