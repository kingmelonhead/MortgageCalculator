package com.example.mortgagecalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_results.*
import kotlin.math.pow


class DisplayResults : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        //gets data from intent
        val data = intent.extras

        var escrow = 0.0
        var loan = 0.0
        var year = 0
        var apr = 0.0

        //retrieves values from the bundle passed by intent
        if (data != null) {
            escrow  = data.getDouble("escrow")
        }
        if (data != null) {
            apr = data.getDouble("apr")
        }
        if (data != null) {
            loan = data.getDouble("loan")
        }
        if (data != null) {
            year = data.getInt("year")
        }
        val list = populateList(escrow, loan, year, apr)

        recycler_view.adapter = Adapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }

    @SuppressLint("SetTextI18n")
    private fun populateList(escrow: Double, loan: Double, year: Int, apr: Double) : List<ExampleOutput>{
        //        M = P[r(1+r)^n/((1+r)^n)-1)]

        //The assumption here is that the escrow is given in amount per year
        val size : Int = year * 12
        val monthlyRate : Double = apr/1200
        val onePlusMonthly : Double = monthlyRate + 1
        val escrowTotal : Double = escrow * year
        val monthlyMort : Double = loan*((monthlyRate* onePlusMonthly.pow(size))/(onePlusMonthly.pow(size) -1))
        val totalMonthly : Double = monthlyMort + escrow/12
        monthlyBox.text = "Monthly payment:   $" + "%.2f".format(totalMonthly)
        val list = ArrayList<ExampleOutput>()
        var accLoan : Double = loan
        var accEscrow : Double = escrowTotal
        var index : Int = 1
        while (index < (size + 1)){
            accEscrow -= escrow/12
            val interest : Double = accLoan * monthlyRate
            val principal : Double = monthlyMort - interest
            accLoan -= principal
            if (index == size){
                accEscrow = 0.0
                accLoan = 0.0
            }
            val item = ExampleOutput(index, "%.2f".format(accEscrow + accLoan).toDouble(), "%.2f".format(principal).toDouble(),
                "%.2f".format(escrow/12).toDouble(), "%.2f".format(interest).toDouble())
            list += item
            index++
        }
    return list
    }
}