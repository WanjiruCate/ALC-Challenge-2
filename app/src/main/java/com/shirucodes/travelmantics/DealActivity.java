package com.shirucodes.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    EditText title,price,description;
    TravelDeal deal;

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

        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");
        if (deal == null){
            deal = new TravelDeal();

        }
        this.deal = deal;
        title.setText(deal.getTitle());
        description.setText(deal.getDescription());
        price.setText(deal.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this,"Deal deleted",Toast.LENGTH_LONG).show();
                backToList();
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
        deal.setTitle(title.getText().toString());
        deal.setPrice(price.getText().toString());
        deal.setDescription(description.getText().toString());

        if(deal.getId() == null) {
            databaseReference.push().setValue(deal);
        }
        else {
            databaseReference.child(deal.getId()).setValue(deal);
        }
    }
    private void deleteDeal(){
        if (deal == null){
            Toast.makeText(this,"Please save the deal before deleting",Toast.LENGTH_LONG).show();
            return;
        }
        databaseReference.child(deal.getId()).removeValue();

    }
    private void backToList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }
}
