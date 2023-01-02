package com.stivoo.wagba.ui.previousorders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stivoo.wagba.ui.ordertracking.OrderTrackingRecyclerViewInterface;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.OrderModel;

import java.util.ArrayList;

public class PreviousOrdersAdapter extends RecyclerView.Adapter<PreviousOrdersAdapter.PreviousOrdersViewHolder> {

    private final OrderTrackingRecyclerViewInterface recyclerViewInterface;
    private static ArrayList<OrderModel> ordersList = new ArrayList<>();

    public PreviousOrdersAdapter(OrderTrackingRecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public PreviousOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreviousOrdersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_order_row_card, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousOrdersViewHolder holder, int position) {
        holder.order_id.setText(ordersList.get(position).getId());
        holder.order_date.setText(ordersList.get(position).getOrderDate());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public void setList(ArrayList<OrderModel> ordersList) {
        PreviousOrdersAdapter.ordersList = ordersList;
        notifyDataSetChanged();
    }

    public static class PreviousOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView order_date;
        TextView order_id;
        Button btn;
        public PreviousOrdersViewHolder(@NonNull View itemView, OrderTrackingRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            order_date = itemView.findViewById(R.id.po_date_txt);
            order_id = itemView.findViewById(R.id.po_id_txt);
            btn = itemView.findViewById(R.id.po_show_btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onViewBtnClick(ordersList.get(pos).getTimeStamp());
                        }
                    }
                }
            });


        }
    }
}
