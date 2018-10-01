package com.example.sj.stylestockprojects;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddItem extends AppCompatActivity {

    ImageButton itemImage;
    Button registerbutton;
    final int REQ_CODE_SELECT_IMAGE=1;
    final int REQ_CODE_CAMERA = 2;
    Uri uri;
    String additemname,imgPath=null,filename,username;
    String add_itemcatagory,add_itemname,add_itemprice,add_itemseller,add_itemsize;
    Spinner itemcatagory;
    ArrayAdapter spinnerAdapter;
    EditText itemname,itemprice,itemseller,itemsize;

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        setContentView(R.layout.activity_add_item);
        requestPermissionDenial();
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        Intent intent = getIntent();
        username= intent.getStringExtra("username");
        Log.e("추가하기_사용자이름",username);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final String[] data = getResources().getStringArray(R.array.catagory);
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);

        itemcatagory = (Spinner)findViewById(R.id.catagoryspinner);
        itemImage = (ImageButton) findViewById(R.id.itemImage);
        registerbutton = (Button)findViewById(R.id.registerButton);
        itemname = (EditText)findViewById(R.id.itemname);
        itemprice = (EditText)findViewById(R.id.itemprice);
        itemseller = (EditText)findViewById(R.id.itemseller);
        itemsize = (EditText)findViewById(R.id.itemsize);




        itemcatagory.setAdapter(spinnerAdapter);

        itemImage.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");




                startActivityForResult(Intent.createChooser(intent, "선택해주세요"), REQ_CODE_SELECT_IMAGE);


            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카테고리,이름,가격,구매처,사이즈
                //데이터베이스 storage 에 동시 저장
                add_itemcatagory = itemcatagory.getSelectedItem().toString();
                add_itemname = itemname.getText().toString();
                add_itemprice = itemprice.getText().toString();
                add_itemseller = itemseller.getText().toString();
                add_itemsize = itemsize.getText().toString();

                uploadFile();

            }
        });
    }
    @Override
    public void onStart(){
        // Log.e("Add_closet_start",username);
        //Log.e("start add","start add");
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            requestPermissionDenial();
            uri = data.getData();            //uri 이미지 경로
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //ImageView imageView = (ImageView) findViewById(R.id.itemImage);

                additemname =getImageNameToUri(uri);

                itemImage.setImageBitmap(bitmap);

                System.out.println("imgpath"+imgPath+"\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        //Cursor cursor = managedQuery(data, proj, null, null, null);
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        Log.d("index0", String.valueOf(cursor));
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        Log.d("index1", String.valueOf(column_index));
        cursor.moveToFirst();
        Log.d("index2", String.valueOf(column_index));
        imgPath = cursor.getString(column_index);

        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        System.out.println("imgname = "+imgName);
        return imgName;
    }


    private void uploadFile() {

        if (uri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();


            FirebaseStorage storage = FirebaseStorage.getInstance();


            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://stylestock-ccf60.appspot.com").child(username).child(add_itemcatagory).child(add_itemname);


            storageRef.putFile(uri)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Product product = new Product(add_itemname,add_itemprice,add_itemseller,add_itemsize,downloadUrl.toString());

                            databaseReference.child(username+"/"+add_itemcatagory+"/"+add_itemname).setValue(product);

                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();

                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }



    protected void requestPermissionDenial(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            Log.d("check1","check1");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("check2","check2");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                Log.d("check3","check3");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1 : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }


        }


    }



}





