package com.example.mda_3_lab;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText etPavadinimas;
    private AutoCompleteTextView actvPadalinys;
    private RatingBar ratingBar;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Spinner spinnerMiestas;
    private SwitchCompat switchRegistruoti;
    private Button btnSaugoti;
    private View mainLayout;
    private boolean isActionClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main);
        etPavadinimas = findViewById(R.id.etPavadinimas);
        actvPadalinys = findViewById(R.id.actvPadalinys);
        ratingBar = findViewById(R.id.ratingBar);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);
        spinnerMiestas = findViewById(R.id.spinnerMiestas);
        switchRegistruoti = findViewById(R.id.switchRegistruoti);
        btnSaugoti = findViewById(R.id.btnSaugoti);

        timePicker.setIs24HourView(true);

        String[] padaliniai = getResources().getStringArray(R.array.padaliniai_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, padaliniai);
        actvPadalinys.setAdapter(adapter);

        actvPadalinys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pasirinktasPadalinys = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Pasirinktas padalinys: " + pasirinktasPadalinys, Toast.LENGTH_SHORT).show();
            }
        });

        String[] miestai = getResources().getStringArray(R.array.miestai_array);
        ArrayAdapter<String> miestuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, miestai);
        spinnerMiestas.setAdapter(miestuAdapter);

        btnSaugoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vykdytiDuomenuSaugojima();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void vykdytiDuomenuSaugojima(){
        String vardas = etPavadinimas.getText().toString().trim();
        String padalinys = actvPadalinys.getText().toString().trim();
        int sudetingumas = (int) ratingBar.getRating();

        int valanda = timePicker.getHour();
        int minute = timePicker.getMinute();
        String laikas = String.format(Locale.getDefault(), "%02d:%02d", valanda, minute);

        int metai = datePicker.getYear();
        int menuo = datePicker.getMonth() +1;
        int diena = datePicker.getDayOfMonth();
        String data = String.format(Locale.getDefault(), "%04d-%02d-%02d", metai, menuo, diena);

        String miestas = spinnerMiestas.getSelectedItem().toString();

        String suformatuotaEilute = "Pavadinimas: " + vardas + "; sudėtingumas: " + sudetingumas + ";\n" + "Laikas: " + laikas + ", data: " + data + "\n" + "Padalinys: " + padalinys + "; Miestas: " + miestas;

        isActionClicked = false;
        Snackbar snackbar = Snackbar.make(mainLayout, suformatuotaEilute, 6000);

        snackbar.setAction("Patvirtinu, informacija teisinga", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActionClicked = true;
                irasytiDuomenisIFaila(vardas, suformatuotaEilute);
            }
        });

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (!isActionClicked && (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE)) {
                    Toast.makeText(MainActivity.this, "Laikas baigėsi, bandykite dar kartą išsaugoti duomenis", Toast.LENGTH_LONG).show();
                }
            }
        });
        snackbar.show();
    }

    private void irasytiDuomenisIFaila(String vardas, String turinys) {
        if(vardas.isEmpty()){
            vardas = "VardenisPavardenis";
        }
        String failoVardas = vardas.replaceAll("\\s+", "") + ".txt";

        try {
            FileOutputStream fos = openFileOutput(failoVardas, MODE_PRIVATE);
            fos.write(turinys.getBytes());
            fos.close();

            File failoKelias = new File(getFilesDir(), failoVardas);
            String pranesimas = "Duomenys įrašyti teisingai: " + failoKelias.getAbsolutePath();
            Toast.makeText(this, pranesimas, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Klaida įrašant failą", Toast.LENGTH_SHORT).show();
        }
    }
}