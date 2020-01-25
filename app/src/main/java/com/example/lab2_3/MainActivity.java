// Chris Lee
// Lab 2 & 3
// CS 4322

package com.example.lab2_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView currentSize, totalText;
    SeekBar sizeBar;

    RadioGroup crustType, diningLocation;
    CheckBox anchov, pineapple, garlic, okra;

    float total, subCrust, subTopping, subDining, subSize;
    boolean[] topping = new boolean[4];

    private static DecimalFormat df = new DecimalFormat("0.00");

    final float costPerSQInch = 0.05f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSize = (TextView) findViewById(R.id.sizeOutput);
        sizeBar = (SeekBar) findViewById(R.id.sizeBar);
        totalText = (TextView) findViewById(R.id.total);

        crustType = (RadioGroup) findViewById(R.id.crust);
        diningLocation = (RadioGroup) findViewById(R.id.dining);

        anchov = (CheckBox) findViewById(R.id.topping_1);
        pineapple = (CheckBox) findViewById(R.id.topping_2);
        garlic = (CheckBox) findViewById(R.id.topping_3);
        okra = (CheckBox) findViewById(R.id.topping_4);

        currentSize.setText(sizeBar.getProgress() + "in");
        subSize = (float)(Math.PI * Math.pow((float)sizeBar.getProgress()/2,2)) * costPerSQInch;
        UpdateTotal();

        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSize.setText(sizeBar.getProgress() + "in");
                subSize = (float)(Math.PI * Math.pow((float)sizeBar.getProgress()/2,2)) * costPerSQInch;
                ProcessToppings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        crustType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Reset the value associated with this group
                if(subCrust != 0f){
                    total -= subCrust;
                    subCrust = 0f;
                }

                int currentId = crustType.getCheckedRadioButtonId();
                RadioButton currentRB = (RadioButton)findViewById(currentId);

                // Crispy
                if(currentRB.equals(crustType.getChildAt(0))){
                    subCrust = 0f;
                }
                // Thick
                if(currentRB.equals(crustType.getChildAt(1))){
                    subCrust = 2.50f;
                }
                // Soggy
                if(currentRB.equals(crustType.getChildAt(2))){
                    subCrust = 5.00f;
                }
                // Cheesy
                if(currentRB.equals(crustType.getChildAt(3))){
                    subCrust = 3.50f;
                }
                UpdateTotal();
            }
        });

        diningLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(subDining != 0f){
                    total -= subDining;
                    subDining = 0f;
                }

                int currentId = diningLocation.getCheckedRadioButtonId();
                RadioButton currentRB = (RadioButton)findViewById(currentId);

                if(currentRB.equals(diningLocation.getChildAt(2))){
                    subDining = 3.0f;
                }else{
                    subDining = 0.0f;
                }
                UpdateTotal();
            }
        });

        anchov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView == anchov && isChecked){
                    topping[0] = true;
                }else{
                    topping[0] = false;
                }
                ProcessToppings();
            }
        });

        pineapple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView == pineapple && isChecked){
                    topping[1] = true;
                }else{
                    topping[1] = false;
                }
                ProcessToppings();
            }
        });

        garlic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView == garlic && isChecked){
                    topping[2] = true;
                }else{
                    topping[2] = false;
                }
                ProcessToppings();
            }
        });

        okra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView == okra && isChecked){
                    topping[3] = true;
                }else{
                    topping[3] = false;
                }
                ProcessToppings();
            }
        });
    }

    private void ProcessToppings(){
        subTopping = 0;
        for(int i = 0; i < topping.length; i++){
            if(topping[i]){
                subTopping++;
            }
        }
        // The total number of topping * the area saved in toppings.
        subTopping = (float)(Math.PI * Math.pow((float)sizeBar.getProgress()/2,2)) * costPerSQInch * subTopping;
        UpdateTotal();
    }

    private void UpdateTotalText(){
        totalText.setText("$" + df.format(total));
    }

    private void UpdateTotal (){
        total = 0.0f;
        total += subSize + subCrust + subDining + subTopping;
        UpdateTotalText();
    }
}
