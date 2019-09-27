package com.example.ulangan_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {

    private List<Entity_notes> listNote;
    Context mContext;

    String juduls,tanggal,deskripsi;

    public AdapterNotes(List<Entity_notes> listNote, Context mContext) {
        this.listNote = listNote;
        this.mContext = mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tJudul, tTanggal, tDeskripsi;

        LinearLayout lr;

        public ViewHolder(View v){
            super(v);

            tJudul = (TextView)v.findViewById(R.id.tv_judul);
            tTanggal = (TextView)v.findViewById(R.id.tv_waktu);
            tDeskripsi = (TextView)v.findViewById(R.id.tv_desk);

            lr = (LinearLayout) v.findViewById(R.id.linearlayout);

        }

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item,parent,false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Entity_notes note =listNote.get(position);

        holder.tTanggal.setText(String.valueOf(note.getDate()));
        holder.tJudul.setText(String.valueOf(note.getJudul()));
        holder.tDeskripsi.setText(String.valueOf(note.getDeskripsi()));


        holder.lr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Entity_notes note = listNote.get(position);
                String id = note.getId();
                String date = note.getDate();
                juduls = note.getJudul();
                deskripsi = note.getDeskripsi();
                tanggal = note.getDate();
                showDialog(id,date);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    private void showDialog(final String idNotes, final String date) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.form,null);
        dialogBuilder.setView(dialogView);

        Button btnDoUpdate = (Button) dialogView.findViewById(R.id.btn_edit);
        Button btnDoDelete = (Button) dialogView.findViewById(R.id.btn_del);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnDoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateInfo(idNotes, date);
                alertDialog.dismiss();
            }
        });

        btnDoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteInfo(idNotes);
                alertDialog.dismiss();
            }
        });

    }

    private void deleteInfo(String idNotes) {
        DatabaseReference dNotes = FirebaseDatabase.getInstance().getReference("Entity_notes").child(idNotes);
        dNotes.removeValue();
        Toast.makeText(mContext, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();

    }

    private void showDialogUpdateInfo(final String idNotes,final String date) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.activity_update,null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btn_simpan);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final EditText etjudul = (EditText)dialogView.findViewById(R.id.ed_judul);
        etjudul.setText(juduls);
        final EditText etdes = (EditText)dialogView.findViewById(R.id.ed_deks);
        etdes.setText(deskripsi);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String penjudul = etjudul.getText().toString();
                String dess = etdes.getText().toString();


                updateInfo(idNotes , penjudul , dess,date);
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateInfo(String idNotes, String penjudul, String dess,String date){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Entity_notes").child(idNotes);
        Entity_notes note = new Entity_notes(idNotes ,date, penjudul, dess);
        databaseReference.setValue(note);

        Toast.makeText(mContext, "Behasil Update Data", Toast.LENGTH_SHORT).show();

        return true;
    }



}