package com.example.ulangan_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NOTES_JUDUL = "judul";
    public static final String NOTES_DESKRIPSI = "deskripsi";
    public static final String NOTES_ID = "id";

    FloatingActionButton fab;

    DatabaseReference databaseNotes;

    RecyclerView RecyclerViewNotes;
    RecyclerView.LayoutManager mLayoutManager;

    List<Entity_notes> listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseNotes = FirebaseDatabase.getInstance().getReference("Notes");

        RecyclerViewNotes = (RecyclerView)findViewById(R.id.rv_container);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        listNotes = new ArrayList<>();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (RecyclerViewNotes != null){
            RecyclerViewNotes.setHasFixedSize(true);
        }

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerViewNotes.setLayoutManager(mLayoutManager);

        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNotes.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Entity_notes note = snapshot.getValue(Entity_notes.class);
                    listNotes.add(note);
                }

                AdapterNotes adapter = new AdapterNotes(listNotes, MainActivity.this);
                RecyclerViewNotes.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showDialogAdd(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add, null);
        dialogBuilder.setView(dialogView);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ' ' HH:mm:ss");
        final String date = sdf.format(new Date());

        final EditText judul = (EditText)dialogView.findViewById(R.id.juduls);
        final EditText deksc = (EditText)dialogView.findViewById(R.id.deks);



        Button tambahInfo = (Button) dialogView.findViewById(R.id.simpans);

        //set judul alert dialog agar tidak bingung

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();



        //membuat tombol addMurid bekerja dengan semestinya
        tambahInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judd = judul.getText().toString();
                String dess = deksc.getText().toString();

                String idNotes = databaseNotes.push().getKey();
                Entity_notes note = new Entity_notes(idNotes ,date, judd, dess);
                databaseNotes.child(idNotes).setValue(note);

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();

                alertDialog.dismiss();
            }
        });

    }


}
