package com.example.sj.stylestockprojects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    Context mContext;
    public String userid;
    public String username;
    private LoginButton loginButton;
    private Button Custom_Facebook_Button,Custom_Kakao_Button,Custom_Naver_Button;
    private com.kakao.usermgmt.LoginButton kakao_login;
    private CallbackManager callbackManager;
    private SessionCallback callback;
    private String nickname;

    public static OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOAuthLoginButton;
    private static String OAUTH_CLIENT_ID = "nu0dym_i6V5_4b8bGsDt";
    private static String OAUTH_CLIENT_SECRET = "weJ7nhCv_Z";
    private static String OAUTH_CLIENT_NAME = "StyleStock";

    //자동 로그인
    String autoID;
    SharedPreferences auto;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        editor = auto.edit();
        getHashKey();

        autoID = auto.getString("autoID",null);

        if(auto.getBoolean("autoLogin",false)){
            userid = auto.getString("autoID",null);
        }
        if(autoID !=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("username",autoID);
            Log.e("넘어가는 name 값", autoID);
            startActivity(intent);
        }

        callbackManager = CallbackManager.Factory.create();  //로그인 응답을 처리할 콜백 관리자
        loginButton = (LoginButton)findViewById(R.id.buttonId); //페이스북 로그인 버튼

        loginButton.setReadPermissions("public_profile", "user_friends","email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("토큰",loginResult.getAccessToken().getToken());
                Log.e("유저아이디",loginResult.getAccessToken().getUserId());
                userid = loginResult.getAccessToken().getUserId();
                Log.e("퍼미션 리스트",loginResult.getAccessToken().getPermissions()+"");

                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("로그인회원정보",object.toString());
                                    Log.e("Login_name",object.getString("name"));
                                    username = object.getString("name");
                                    Log.e("Login_username",username);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("username",userid);
                editor.putString("autoID",userid);
                editor.putBoolean("autoLogin",true);
                editor.commit();
                Log.e("넘어가는 name 값", userid);
                startActivity(intent);
            }

            @Override
            public void onError(FacebookException error) { }

            @Override
            public void onCancel() { }
        });

        //페이스북 커스텀버튼
        Custom_Facebook_Button = (Button)findViewById(R.id.facebookBtn);
        Custom_Facebook_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        //카카오톡 로그인 부분
        kakao_login = (com.kakao.usermgmt.LoginButton)findViewById(R.id.com_kakao_login);
        callback = new SessionCallback();   //kakao login callback
        Session.getCurrentSession().addCallback(callback);
        //카카오톡 커스텀버튼
        Custom_Kakao_Button=(Button)findViewById(R.id.kakaoBtn);
        Custom_Kakao_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kakao_login.performClick();
            }
        });

        //네이버 로그인 부분
        mOAuthLoginModule  = OAuthLogin.getInstance();
        mOAuthLoginModule .init(this,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        //네이버 커스텀버튼
        Custom_Naver_Button = (Button) findViewById(R.id.naverBtn);
        Custom_Naver_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOAuthLoginButton.performClick();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    //카카오톡 로그인
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.e("ex", String.valueOf(exception));
            }
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }
    //카카오톡 사용자 정보요청
    public void requestMe() {

        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.requestMe(new MeResponseCallback() {
            // 세션 오픈 실패. 세션이 삭제된 경우,
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            // 회원이 아닌 경우,
            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            // 사용자정보 요청에 성공한 경우,
            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");

                nickname = userProfile.getNickname();
                String profileImagePath = userProfile.getProfileImagePath();
                String thumnailPath = userProfile.getThumbnailImagePath();
                String UUID = userProfile.getUUID();
                long id_long = userProfile.getId();
                userid=String.valueOf(id_long);

                Log.e("Profile : ", nickname + "");
                Log.e("Profile : ", profileImagePath  + "");    //얘넨 필요음슴
                Log.e("Profile : ", thumnailPath + "");         //얘넨 필요음슴
                Log.e("Profile : ?", UUID + "");                //얘는 혹시나
                Log.e("Profile : !", userid + "");
                redirectMainActivity(userid,nickname);
            }

            // 사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
            }

        });

    }
    //카카오톡 로그인 성공 후 값넘기기
    private void redirectMainActivity(String id, String nickname) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("username", id);
        editor.putString("autoID",id);
        editor.putBoolean("autoLogin",true);
        editor.commit();

        Log.e("kakao 넘어가는 name 값", id);
        Log.e("kakao 넘어가는 nickname 값", nickname);
        startActivity(intent);
    }
    //네이버 권한 요청
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                ProfileTask profileTask = new ProfileTask();
                profileTask.execute(accessToken);
                Log.d("accessToken",accessToken);

            }
        };
    };
    //네이버 로그인 사용자 정보요청
    class ProfileTask extends AsyncTask<String,Void,String> {
        String result;
        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0];
            String header = "Bearer "+ token;
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
                br.close();
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
            return result;
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals("00")) {
                    JSONObject jsonObject = new JSONObject(object.getString("response"));
                    Log.d("jsonObject", jsonObject.toString());

                    username = jsonObject.getString("name");
                    userid = jsonObject.getString("id");
                    Log.d("Naver_userName",username);
                    Log.d("Naver_userID",userid);

                    editor.putString("autoID",userid);
                    editor.putBoolean("autoLogin",true);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",userid);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //해시키
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
