package com.phc.cssd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phc.cssd.master_data.*;

public class MasterDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);
        Button bt1 = (Button) findViewById(R.id.seting01);
        Button bt2 = (Button) findViewById(R.id.seting02);
        Button bt3 = (Button) findViewById(R.id.seting03);
        Button bt4 = (Button) findViewById(R.id.seting04);
        Button bt5 = (Button) findViewById(R.id.seting05);
        Button bt6 = (Button) findViewById(R.id.seting06);
        Button bt7 = (Button) findViewById(R.id.seting07);
        Button bt8 = (Button) findViewById(R.id.seting08);
        Button bt9 = (Button) findViewById(R.id.seting09);
        Button bt10 = (Button) findViewById(R.id.seting10);
        Button bt11 = (Button) findViewById(R.id.seting11);
        Button bt12 = (Button) findViewById(R.id.seting12);
        Button bt13 = (Button) findViewById(R.id.seting13);

        final TextView tv1 = (TextView) findViewById(R.id.textView);


        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, EmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, ItemTypeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, LabelActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, OccurenceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, OrganizationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, PackingmapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, SpecialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, SterilemachineActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, SterileprocessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, SupplierActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, UnitsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MasterDataActivity.this, WashingProcessActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
