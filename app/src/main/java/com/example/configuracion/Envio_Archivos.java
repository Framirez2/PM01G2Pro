package com.example.configuracion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class Envio_Archivos extends AppCompatActivity {

    static final int peticion_Storage = 100;
    static final int pickFile_Result_Code = 101;

    Button btnSendFile, btnSelectFile;
    EditText txtFileDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_archivos);
        chargeObj();

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void permiso(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, peticion_Storage);
        }else openFileExplorer();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_Storage){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openFileExplorer();
            }
        }
    }

    private void openFileExplorer(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "Escoger un archivo");
        startActivityForResult(intent, pickFile_Result_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        String src = uri.getPath();
        File source = new File(src);
        String fileName = uri.getLastPathSegment();
    }

    private void chargeObj(){
        btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
        btnSendFile = (Button) findViewById(R.id.btnSendFile);
        txtFileDesc = (EditText) findViewById(R.id.txtFileDesc);
    }
}