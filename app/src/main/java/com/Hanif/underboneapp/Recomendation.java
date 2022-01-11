package com.Hanif.underboneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Recomendation extends AppCompatActivity {
    private TextView NameMotor,Rec1,Rec2,Rec3,Rec4,Rec5;
    private Button bttntoSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);
        NameMotor = findViewById(R.id.in1);
        Rec1 = findViewById(R.id.OneAnswer);
        Rec2 = findViewById(R.id.TwoAnswer);
        Rec3 = findViewById(R.id.ThreeAnswer);
        Rec4 = findViewById(R.id.FourAnswer);
        Rec5 = findViewById(R.id.FiveAnswer);
        bttntoSearch = findViewById(R.id.bttntoSearch);
        //NameRec
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("NameRec");
        NameMotor.setText(name);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py= Python.getInstance();
        final Python pyobj = Python.getInstance();

        PyObject pyo = pyobj.getModule("main");
        PyObject obj = pyo.callAttr("get_recomendations",name,0);
        Rec1.setText(obj.toString());

        PyObject pyo1 = pyobj.getModule("main");
        PyObject obj1 = pyo1.callAttr("get_recomendations",name,1);
        Rec2.setText(obj1.toString());

        PyObject pyo2 = pyobj.getModule("main");
        PyObject obj2 = pyo2.callAttr("get_recomendations",name,2);
        Rec3.setText(obj2.toString());

        PyObject pyo3 = pyobj.getModule("main");
        PyObject obj3 = pyo3.callAttr("get_recomendations",name,3);
        Rec4.setText(obj3.toString());

        PyObject pyo4 = pyobj.getModule("main");
        PyObject obj4 = pyo4.callAttr("get_recomendations",name,4);
        Rec5.setText(obj4.toString());

        bttntoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Recomendation.this,AllMotorcycleActivity.class);
                startActivity(intent);
            }
        });

    }
}