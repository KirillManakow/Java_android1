package com.example.api;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add extends AppCompatActivity {

    private EditText User, Device, Price;
    private Button AddButton;
    private ImageView imageButton;
    String Img="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        User = findViewById(R.id.User_add);
        Device = findViewById(R.id.Device_add);
        Price = findViewById(R.id.Price_add);
        AddButton = findViewById(R.id.add_button);
        imageButton = findViewById(R.id.Image_device_add);
    }
    public  void Add(View v)
    {
        if (User.getText().length()==0 || Device.getText().length()==0 ||  Price.getText().length()==0 )
        {
            Toast.makeText(Add.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if (Img=="")
            {
                Img=null;
                postData(User.getText().toString(),Device.getText().toString(),Price.getText().toString(),Img);
            }
            else
            {
                postData(User.getText().toString(),Device.getText().toString(),Price.getText().toString(),Img);
            }
            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    Next();
                }

                public void onTick(long millisUntilFinished) {

                }
            }.start();

        }

    }
    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickChooseImage(View view)
    {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img= Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }
    private void configureBackButton() {
        Button back = (Button) findViewById(R.id.button_otmena);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void postData(String Users, String Nazvanie, String Zena ,String Image) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/МанаковКА/api/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Model model = new Model(Users,Nazvanie,Zena,Image);

        Call<Model> call = retrofitAPI.createPost(model);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Toast.makeText(Add.this, "Данные добавлены", Toast.LENGTH_SHORT).show();
                User.setText("");
                Device.setText("");
                Price.setText("");
                Model responseFromAPI = response.body();

            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
            }
        });
    }
}

