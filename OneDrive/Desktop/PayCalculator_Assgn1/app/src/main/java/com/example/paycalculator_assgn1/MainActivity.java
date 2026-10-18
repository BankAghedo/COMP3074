package com.example.paycalculator_assgn1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etHours, etRate, etTax;
    private TextView tvPay, tvOvertime, tvTotal, tvTaxResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHours = findViewById(R.id.etHours);
        etRate = findViewById(R.id.etRate);
        etTax = findViewById(R.id.etTax);
        tvPay = findViewById(R.id.tvPay);
        tvOvertime = findViewById(R.id.tvOvertime);
        tvTotal = findViewById(R.id.tvTotal);
        tvTaxResult = findViewById(R.id.tvTaxResult);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePay();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculatePay() {
        String hoursText = etHours.getText().toString().trim();
        String rateText = etRate.getText().toString().trim();
        String taxText = etTax.getText().toString().trim();

        if (hoursText.isEmpty() || rateText.isEmpty() || taxText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double hours;
        double rate;
        double taxRate;
        try {
            hours = Double.parseDouble(hoursText);
            rate = Double.parseDouble(rateText);
            taxRate = Double.parseDouble(taxText) / 100.0; // user enters e.g. 15 for 15%
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double pay;
        double overtimePay;

        if (hours <= 40) {
            pay = hours * rate;
            overtimePay = 0;
        } else {
            pay = 40 * rate;
            overtimePay = (hours - 40) * rate * 1.5;
        }

        double totalPay = pay + overtimePay;
        double tax = pay * taxRate;

        tvPay.setText(String.format("Pay: $%.2f", pay));
        tvOvertime.setText(String.format("Overtime Pay: $%.2f", overtimePay));
        tvTotal.setText(String.format("Total Pay: $%.2f", totalPay));
        tvTaxResult.setText(String.format("Tax: $%.2f", tax));
    }
}