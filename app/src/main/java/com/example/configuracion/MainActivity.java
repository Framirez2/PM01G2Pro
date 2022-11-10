package com.example.configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








        Button btns = (Button) findViewById(R.id.btnver);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantalla2 = new Intent(getApplicationContext(), perfil.class);
                startActivity(pantalla2);
            }
        });
    }
}