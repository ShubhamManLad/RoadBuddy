package com.eample.roadbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.MyViewHolder>{

    private Context context;
    private List<ticketModel> ticketList;

    public historyAdapter() {
    }

    public historyAdapter(Context context) {
        this.context = context;
        ticketList = new ArrayList<>();
    }

    public void add(ticketModel ticketModel){
        ticketList.add(ticketModel);
        notifyDataSetChanged();
    }

    public void clear(){
        ticketList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ticketModel ticket = ticketList.get(position);
        holder.ticketId_textview.setText(ticket.getTicketID());
        holder.numberPlate_textView.setText(ticket.getPlate_number());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ticketViewIntent = new Intent(context, TicketView.class);
                ticketViewIntent.putExtra("Ticket_ID",ticket.getTicketID());
                ticketViewIntent.putExtra("Number_Plate",ticket.getPlate_number());
                ticketViewIntent.putExtra("Latitude",ticket.getCar_latitude());
                ticketViewIntent.putExtra("Longitude",ticket.getCar_longitude());
                ticketViewIntent.putExtra("Mode",69);
                context.startActivity(ticketViewIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView ticketId_textview;
        private TextView numberPlate_textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketId_textview = itemView.findViewById(R.id.ticketId_textView);
            numberPlate_textView = itemView.findViewById(R.id.numberPlate_textView);
        }
    }


}
