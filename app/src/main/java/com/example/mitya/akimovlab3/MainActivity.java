package com.example.mitya.akimovlab3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "Text", EXTRA_SIZE="Size";
    RadioGroup group1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group1 = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton rButton;

        rButton = new RadioButton(this);
        rButton.setText("Так");
        group1.addView(rButton);
        group1.check(rButton.getId());
        rButton = new RadioButton(this);
        rButton.setText("Ні");
        group1.addView(rButton);
        Button button = findViewById(R.id.button);
        Button open = findViewById(R.id.button2);
        Button save = findViewById(R.id.button3);

        View.OnClickListener v = new View.OnClickListener(){
            public void onClick(View v) {
                int selectedId1 = group1.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(selectedId1);
                EditText mEdit = (EditText)findViewById(R.id.editText);
                Context context = getApplicationContext();
                CharSequence text = mEdit.getText() + " - " + radioButton1.getText() + " - ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };

        View.OnClickListener openView = new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            openFileInput("question info")));
                    String str = "", str2 = "";
                    str = br.readLine();
                    str2 = br.readLine();
                    if(str2 == "")
                    {
                        Toast.makeText(getApplicationContext(), "File is empty!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        try {
                            Intent intent = new Intent(getApplicationContext(), opened_file.class);
                            intent.putExtra(EXTRA_TEXT, str);
                            intent.putExtra(EXTRA_SIZE, str2);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        View.OnClickListener saveView = new View.OnClickListener(){
            public void onClick(View v) {
                int selectedId1 = group1.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(selectedId1);
                EditText mEdit = (EditText)findViewById(R.id.editText);
                CharSequence text = mEdit.getText() + " - " + radioButton1.getText() + " - ";
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("question info",MODE_PRIVATE)));
                    writer.write(text.toString());
                    writer.close();
                    Toast.makeText(getApplicationContext(), "File Saved.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        button.setOnClickListener(v);
        open.setOnClickListener(openView);
        save.setOnClickListener(saveView);
    }
}
