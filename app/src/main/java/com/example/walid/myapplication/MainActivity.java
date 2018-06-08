package com.example.walid.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    RecyclerView myrcv;
    EtudiantAdapter myAdapter;
    String  url="http://students-training.1d35.starter-us-east-1.openshiftapps.com/students.json";
    final int ADD_STUDENT_ACTIVITY = 0;
    SQLHelper db;
    int counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "walid.saadd@gmail.com, ENSIT", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = new SQLHelper(this);

        counts = db.getEtudiantCount();

        if(counts == 0){
            new LoadInfo().execute();
            Toast.makeText(MainActivity.this, "Si la liste ne s'affiche, choisissez une classe dans la NavigationView. Merci!", Toast.LENGTH_LONG).show();
        }
        myrcv = (RecyclerView) findViewById(R.id.rcv);

        myAdapter = new EtudiantAdapter(MainActivity.this, db.getAllEtudiant());
        myrcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myrcv.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.search) {
            Toast.makeText(MainActivity.this, "search", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        Toast.makeText(this, ""+view.getId(), Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addE) {
            Intent intent = new Intent(MainActivity.this, AddStudent.class);
            counts = db.getEtudiantCount();
            intent.putExtra("nbEtudiants", counts);
            startActivityForResult(intent, ADD_STUDENT_ACTIVITY);

        }
        else if (id == R.id.c1) {
            myAdapter.updateCursor(db.getByOption("ING_INF2_A"));
        }
        else if (id == R.id.c2) {
            myAdapter.updateCursor(db.getByOption("ING_INF2_B"));
        }
        else if (id == R.id.class1) {
        }
        else if (id == R.id.class2) {
            myAdapter.updateCursor(db.getByOption("ING_INF2_B"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode != ADD_STUDENT_ACTIVITY)return;
        if(resultCode != RESULT_OK)return;
        Etudiant etudiant = (Etudiant) data.getParcelableExtra("new");
        db.addEtudiant(etudiant);
        myAdapter.updateCursor(db.getAllEtudiant());
    }

    private class LoadInfo extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("Loading students. Please wait...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String result = sh.makeServiceCall(url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();

            try {

                int j=1;
                JSONArray jArray = new JSONArray(result);

                for(int i=0;i<jArray.length();i++){

                    JSONObject json_data = jArray.getJSONObject(i);

                    Etudiant etudiant = new Etudiant(j, json_data.getString("nom"),
                            json_data.getString("email"),
                            json_data.getString("option"),
                            json_data.getInt("abs"));
                    if(etudiant.getOption().equals("Reseaux")){
                        etudiant.setOption("ING_INF2_A");
                    }else{
                        etudiant.setOption("ING_INF2_B");
                    }
                    j++;
                    db.addEtudiant(etudiant);
                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
