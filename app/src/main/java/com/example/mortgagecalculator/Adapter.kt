package com.example.mortgagecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.output_template.view.*
import org.w3c.dom.Text

class Adapter(private val exampleList: List<ExampleOutput>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.output_template, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = exampleList[position]

        holder.monthView.text = item.month.toString()
        holder.loanView.text = item.loan.toString()
        holder.principalView.text = item.principal.toString()
        holder.interestView.text = item.interest.toString()
        holder.escrowView.text = item.escrow.toString()
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val monthView : TextView = itemView.month_num
        val loanView : TextView = itemView.loan_amount
        val principalView : TextView = itemView.principal_amount
        val escrowView : TextView = itemView.escrow_amount
        val interestView : TextView = itemView.interest_amount
    }
}