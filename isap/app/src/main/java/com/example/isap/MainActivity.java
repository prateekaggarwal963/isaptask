package com.example.isap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name,address,mobile;
    Button btnshow,btninsert;
    ImageView image1;
    database g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g = new database(this);
        g.getReadableDatabase();


        name=(EditText) findViewById(R.id.name);
        address=(EditText) findViewById(R.id.address);
        mobile=(EditText) findViewById(R.id.mobile);
        btninsert= findViewById(R.id.btninsert);
        btnshow=findViewById(R.id.btnshow);
        image1 = findViewById(R.id.image1);

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                String uname=name.getText().toString();
                String uaddress=address.getText().toString();
                String umobile=mobile.getText().toString();

                Boolean b = g.insert_data(uname,uaddress,umobile);
                if(b==true)
                {
                    Toast.makeText(MainActivity.this, "Data Inserted in sqlite", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this, "not successful in sqlite", Toast.LENGTH_SHORT).show();
                }


                i.putExtra("uname",uname);
                i.putExtra("uaddress",uaddress);
                i.putExtra("umobile",umobile);
//                //set the image
                startActivity(i);
            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor t=g.getInfo();
                if(t.getCount()==0)
                {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer=new StringBuffer();
                while (t.moveToNext())
                {
                    buffer.append("name: "+t.getString(1)+"\n");
                    buffer.append("address: "+t.getString(2)+"\n");
                    buffer.append("mobile: "+t.getString(3)+"\n\n\n\n");

                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User info");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

    }
}