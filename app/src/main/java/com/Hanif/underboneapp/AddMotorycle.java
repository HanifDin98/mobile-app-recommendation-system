package com.Hanif.underboneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMotorycle extends AppCompatActivity {
    private EditText txtMotorNameA, txtBrandNameA,txtEngineTypeA,txtEngineCoolingA,txtDisplacementA,txtFuelTankA,txtStartingA,txtFrameA,txtMaxA,txtTransmissionA,ImageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motorycle);

        txtMotorNameA =findViewById(R.id.AddName);
        txtBrandNameA = findViewById(R.id.AddBrand);
        txtDisplacementA = findViewById(R.id.AddDisplacement);
        txtEngineCoolingA = findViewById(R.id.AddEngineCooling);
        txtEngineTypeA = findViewById(R.id.AddEngineType);
        txtFuelTankA = findViewById(R.id.AddFuelCapacity);
        txtStartingA = findViewById(R.id.AddStartingSystem);
        txtFrameA = findViewById(R.id.AddFrameType);
        txtMaxA = findViewById(R.id.AddMaxPower);
        txtTransmissionA = findViewById(R.id.AddTransmission);
        ImageURL = findViewById(R.id.AddImageURI);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_add_motor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_motor:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }



    }

    private void saveNote() {

        String name = txtMotorNameA.getText().toString();
        String search = name.toLowerCase();
        String brand = txtBrandNameA.getText().toString();
        String dis =  txtDisplacementA.getText().toString();
        //int displacement = Integer.parseInt(txtDisplacementA.getText().toString());
        String EngineType = txtEngineTypeA.getText().toString();
        String EngineCooling = txtEngineCoolingA.getText().toString();
       // double fuelTank = Double.parseDouble(txtFuelTankA.getText().toString());
        String fuel = txtFuelTankA.getText().toString();
       // double MaxPower = Double.parseDouble(txtMaxA.getText().toString());
        String max =  txtMaxA.getText().toString();
        String starting = txtStartingA.getText().toString();
        String frame = txtFrameA.getText().toString();
        String transmission = txtTransmissionA.getText().toString();
        String image = ImageURL.getText().toString();

        if(name.trim().isEmpty()|| brand.trim().isEmpty()|| EngineCooling.trim().isEmpty()|| EngineType.trim().isEmpty()
           ||starting.trim().isEmpty()||frame.trim().isEmpty()||transmission.trim().isEmpty()||image.trim().isEmpty()
              ||TextUtils.isEmpty(dis)||TextUtils.isEmpty(fuel)||TextUtils.isEmpty(max) ){

            Toast.makeText(this, "Please insert all field", Toast.LENGTH_SHORT).show();
            return;
        }
        int displacement = Integer.parseInt(dis);
        double fuelTank = Double.parseDouble(fuel);
        double MaxPower = Double.parseDouble(max);
//        if(txtMotorNameA.getText().toString().isEmpty()|| txtBrandNameA.getText().toString().isEmpty()|| txtEngineTypeA.getText().toString().isEmpty()
//                ||txtStartingA.getText().toString().isEmpty()||txtFrameA.getText().toString().isEmpty()||txtTransmissionA.getText().toString().isEmpty()
//                ||ImageURL.getText().toString().isEmpty()|| TextUtils.isEmpty(txtDisplacementA.getText().toString())
//                ||TextUtils.isEmpty(txtFuelTankA.getText().toString())||TextUtils.isEmpty(txtMaxA.getText().toString())){
//            Toast.makeText(AddMotorycle.this, "One of the field is empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
        CollectionReference MotorRef = FirebaseFirestore.getInstance().collection("Underbone");
        MotorRef.add(new Motorcycle(name,brand,EngineType,EngineCooling,displacement,fuelTank,starting
                ,frame,MaxPower,transmission,image));
        Intent intent = new Intent(AddMotorycle.this,AdminAllMotorcyle.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),AdminAllMotorcyle.class));
    }
}