package com.example.walid.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddStudent extends AppCompatActivity {

    EditText nom;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        nom=(EditText)findViewById(R.id.nom);
        email=(EditText)findViewById(R.id.email);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouter(v);
            }
        });
    }


    public void ajouter(View v) {

        Intent intent = getIntent();
        int nbEtudiants = intent.getIntExtra("nbEtudiants", 0);

        Etudiant etudiant = new Etudiant(nbEtudiants+1,nom.getText().toString(), email.getText().toString(), "", 0);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        int btnChecked = rg.getCheckedRadioButtonId();

        if(btnChecked == R.id.infoA){
            etudiant.setOption("ING_INF2_A");
        }
        if(btnChecked == R.id.infoB){
            etudiant.setOption("ING_INF2_B");
        }

        intent.putExtra("new", etudiant);
        intent.removeExtra("nbEtudiants");

        setResult(RESULT_OK, intent);
        finish();
    }
}
