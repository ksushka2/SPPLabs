package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

//1 Б

public class MainActivity extends AppCompatActivity {

    private Button butBuild, butAdd, butDel;
    private EditText editTextPredl;
    private Spinner spinnerPreposition;
    private RadioGroup radioGroup;
    private ListView listView;
    private TextView textViewResult;

    //String[] predl = {"В", "На", "Под"};

    private ArrayList<String> predl;
    private String selectedPreposition = "";
    private String selectedNoun = "";
    private String selectedAdverb = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPreposition = findViewById(R.id.spinnerPreposition);
        radioGroup = findViewById(R.id.radioGroup);
        listView = findViewById(R.id.listView);
        textViewResult = findViewById(R.id.textViewResult);
        butBuild = findViewById(R.id.butBuild);
        butAdd = findViewById(R.id.butAdd);
        butDel = findViewById(R.id.butDel);
        editTextPredl = findViewById(R.id.editTextPredl);

        predl = new ArrayList<>(Arrays.asList("В","На","Под"));

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, predl);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPreposition.setAdapter(adapter);

        String [] narech = getResources().getStringArray(R.array.array_narechia);

        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, narech);
        listView.setAdapter(adapter2);

        spinnerPreposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPreposition = predl.get(position);

                editTextPredl.setText(selectedPreposition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPreposition = "";
            }
        });

        butAdd.setOnClickListener(v ->{
            String newPredl = editTextPredl.getText().toString().trim();
            if (!newPredl.isEmpty())
            {
                adapter.add(newPredl);
                adapter.notifyDataSetChanged();
                spinnerPreposition.setSelection(predl.size() - 1);
                editTextPredl.setText("");
            }
        });

        butDel.setOnClickListener(v ->{
            int pos = spinnerPreposition.getSelectedItemPosition();
            String todelPredl = predl.get(pos);
            adapter.remove(todelPredl);
            adapter.notifyDataSetChanged();

            if (predl.isEmpty())
            {
                selectedPreposition = "";
                editTextPredl.setText("");
            }
        });

        editTextPredl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    int position = spinnerPreposition.getSelectedItemPosition();
                    String updatedPredl= editTextPredl.getText().toString().trim();

                    if (!updatedPredl.isEmpty()) {
                        predl.set(position, updatedPredl);
                        adapter.notifyDataSetChanged();
                        selectedPreposition = updatedPredl;
                    }
                    return true;
                }
                return false;
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null) {
                    selectedNoun = checkedRadioButton.getText().toString();

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAdverb = (String) parent.getItemAtPosition(position);

            }
        });

        butBuild.setOnClickListener(v->{
            updateResultText();
        });

    }

    private void updateResultText() {
        String finalText = selectedPreposition + " " + selectedNoun + " " + selectedAdverb;
        textViewResult.setText(finalText.trim());
    }
}