package com.example.configuracion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.configuracion.config.API;
import com.google.android.gms.common.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Envio_Archivos extends AppCompatActivity {

    static final int peticion_Storage = 100;
    static final int pickFile_Result_Code = 101;

    Button btnSendFile, btnSelectFile;
    EditText txtFileDesc;

    private Bitmap bitmap;
    private String file;

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

    private void uploadFile(){
        StringRequest sr = new StringRequest(Request.Method.POST, API.apiGetList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(Envio_Archivos.this, "sent", Toast.LENGTH_SHORT).show();
                Toast.makeText(Envio_Archivos.this, response, Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Envio_Archivos.this, "Errrorrr", Toast.LENGTH_SHORT).show();
                Toast.makeText(Envio_Archivos.this, "Error!! " + error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //String imgData = imageToBase64(bitmap);
                params.put("image", file);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(sr);
    }

    private void openFileExplorer(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        //intent = Intent.createChooser(intent, "Escoger un archivo");
        startActivityForResult(intent, pickFile_Result_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == pickFile_Result_Code && resultCode == Activity.RESULT_OK){

            try{
                Uri uri = data.getData();
                InputStream is = getContentResolver().openInputStream(uri);
                byte[] fileBa = IOUtils.toByteArray(is);
                //file = fileToBase64(is);
                //byte[] fileBa = IOUtils.toByteArray(is);
                //System.out.println(fileBa.length);
                is=null;
                //file = Base64.encodeToString(fileBa, Base64.DEFAULT);
                //is = null;
                //file = Base64.encodeToString(IOUtils.toByteArray(is), Base64.DEFAULT);
                String _fname = getContentResolver().getType(uri);
                String _type = MimeTypeMap.getSingleton().getExtensionFromMimeType(_fname);
                System.out.println(_fname);
                System.out.println(_type);
                Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
            }catch (Exception ex){
                Toast.makeText(this, "error img", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        }else Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
    }

    private String fileToBase64(InputStream _is){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            int bSize = 3 * 512;
            byte[] bfr = new byte[6000];
            baos = new ByteArrayOutputStream();
            //String tst = Base64.encodeToString(IOUtils.toByteArray(_is), Base64.DEFAULT);
            int bRead;
            while ((bRead = _is.read(bfr)) != -1){
                baos.write(bfr, 0, bRead);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            Toast.makeText(this, "Byte Array Error", Toast.LENGTH_SHORT).show();
        } finally {
            if(_is != null){
                try {
                    _is.close();
                }catch (IOException ie){
                    ie.printStackTrace();
                    Toast.makeText(this, "Mas error", Toast.LENGTH_SHORT).show();
                }
            }
        }

        //byte[] bArray = baos.toByteArray();

        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        //return Base64.encodeToString(bArray, Base64.DEFAULT);
    }

    private void chargeObj(){
        btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
        btnSendFile = (Button) findViewById(R.id.btnSendFile);
        txtFileDesc = (EditText) findViewById(R.id.txtFileDesc);
    }
}