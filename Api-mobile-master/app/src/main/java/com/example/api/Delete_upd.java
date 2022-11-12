package com.example.api;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Delete_upd extends AppCompatActivity {

    private EditText UserD, DeviceD, ZenaD;
    private Button Delete_button;
    private ImageView imageButtonDel;
    Mask mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_upd);

        mask=getIntent().getParcelableExtra("Zakazs");
        UserD = findViewById(R.id.User_del);
        UserD.setText(mask.getUsers());
        DeviceD = findViewById(R.id.Device_del);
        DeviceD.setText(mask.getNazvanie());
        ZenaD = findViewById(R.id.Price_del);
        ZenaD.setText(mask.getZena());
        Delete_button = findViewById(R.id.del_button);
        imageButtonDel = findViewById(R.id.Image_device);
        imageButtonDel.setImageBitmap(getImgBitmap(mask.getImage()));

    }
    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(Delete_upd.this.getResources(),
                    R.drawable.zaglushka);
        }

    }
    public  void delete()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/МанаковКА/api/Zakazs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIDelete delete = retrofit.create(RetrofitAPIDelete.class);
        Call<Model> call = delete.deleteData(mask.getId_zakaz());

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Toast.makeText(Delete_upd.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                Model responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });

    }
    public void Delet_Click(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Delete_upd.this);
        builder.setTitle("Удалить")
                .setMessage("Вы уверены что хотите Удалить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete();
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}