package com.shirucodes.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    EditText title,price,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.openFirebaseReference("traveldeals");

        firebaseDatabase =FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;

        title = findViewById(R.id.txtTittle);
        price = findViewById(R.id.txtPrice);
        description = findViewById(R.id.txtDescription);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                clean();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clean() {
        title.setText("");
        description.setText("");
        price.setText("");
        title.requestFocus();






    }

    private void saveDeal() {
        String txt_tittle = title.getText().toString();
        String txt_price = price.getText().toString();
        String txt_description = description.getText().toString();
        TravelDeal deal = new TravelDeal(txt_tittle,txt_description,txt_price,"");
        databaseReference.push().setValue(deal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }
}
