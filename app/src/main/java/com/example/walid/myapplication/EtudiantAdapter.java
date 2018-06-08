package com.example.walid.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantHolder> {
    private Context context;
    private LayoutInflater inflater;
    private Cursor cursor;

    public EtudiantAdapter(Context context, Cursor cursor){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cursor = cursor;
    }
    // créer notre vue à partir du fichier layout
    @Override
    public EtudiantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.etudiantinfo2, parent,false);
        EtudiantHolder holder=new EtudiantHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(EtudiantHolder holder, int position) {

        cursor.moveToPosition(position);
        int index;
        String text;

        index = cursor.getColumnIndex("nom");
        text = cursor.getString(index);
        holder.nom.setText(text);

        index = cursor.getColumnIndex("email");
        text = cursor.getString(index);
        holder.email.setText(text);

        index = cursor.getColumnIndex("option");
        text = cursor.getString(index);
        holder.option.setText(text);

        index = cursor.getColumnIndex("abs");
        int abs = cursor.getInt(index);
        holder.abs.setText(""+abs);

    }
    //retourner la taille de la liste d'item
    @Override
    public int getItemCount() {
        //return data.size();
        return cursor.getCount();
    }

    class EtudiantHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView email;
        public TextView option;
        public TextView abs;
        public ImageView avatar;

        public EtudiantHolder(View itemView) {
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nom);
            email = (TextView) itemView.findViewById(R.id.email);
            option = (TextView) itemView.findViewById(R.id.option);
            abs = (TextView) itemView.findViewById(R.id.nbabs);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    public void updateCursor(Cursor cursor){
        this.cursor.close();
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}