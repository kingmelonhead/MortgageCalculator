package com.example.mortgagecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.widget.doAfterTextChanged


class MainActivity : AppCompatActivity() {

    private var year : Int = 15; // variable used for keeping track of the year in the text box
    private var apr : Double = 0.0; // variable used to contain the apr rate
    private var escrow : Double = 0.0; // variable used to contain the escrow
    private var loan : Double = 0.0; // variable used to contain the loan amount
    private var validInput : Boolean = false;
    private var temp : String = ""
    private var validApr : Boolean = false
    private var validEscrow :  Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        aprError.visibility = INVISIBLE
        escrowError.visibility = INVISIBLE

        //on click listeners for buttons
        plusButton.setOnClickListener(clickPlus);
        minButton.setOnClickListener(clickMin);
        resetButton.setOnClickListener(resetBoxes)
        calcButton.setOnClickListener(bundleVars)

        aprBox.doAfterTextChanged {
            validateApr();
        }
        escrowBox.doAfterTextChanged {
            validateEscrow();
        }

    }


    private fun validateApr(){
        //makes sure the apr is valid, if not it displays an error message and
        //sets the validApr flag to false
        aprError.visibility = INVISIBLE
        validApr = true // assume its true
        var dec : Boolean = false
        var afterDot : Int = 0
        for (ch in aprBox.text.toString()){
            if (ch == '.'){
                dec = true;
            }
            else {
                if (dec){
                    afterDot++;
                    if (afterDot > 3){
                        validApr = false
                        aprError.visibility = VISIBLE
                        break
                    }
                }
            }
        }
    }

    private fun validateEscrow(){
        //makes sure the escrow is only to 2 decimal places, displays an error message if the user goes over 2 places
        //sets the escrow flag to false if its not valid
        escrowError.visibility = INVISIBLE
        validEscrow = true // assume its true
        var dec : Boolean = false
        var afterDot : Int = 0
        for (ch in escrowBox.text.toString()){
            if (ch == '.'){
                dec = true;
            }
            else {
                if (dec){
                    afterDot++;
                    if (afterDot > 2){
                        validEscrow = false
                        escrowError.visibility = VISIBLE
                        break
                    }
                }
            }
        }
    }
    private val clickPlus = View.OnClickListener {
        //click listener for the plus button
        increaseYear()
    }

    private val bundleVars = View.OnClickListener {

        if (validEscrow && validApr){
            if (loanBox.text.toString().toInt() > 0){
                validInput = true
            }
        }
        if (validInput){
            temp = aprBox.text.toString()
            apr = temp.toDouble()

            temp = escrowBox.text.toString()
            escrow = temp.toDouble()

            temp = loanBox.text.toString()
            loan = temp.toDouble()
            val data = Bundle()
            data.putDouble("apr", apr)
            data.putDouble("loan", loan)
            data.putDouble("escrow", escrow)
            data.putInt("year", year)
            validInput = false
            intent = Intent(this, DisplayResults::class.java)
            intent.putExtras(data)
            startActivity(intent)
        }
    }
    private val clickMin = View.OnClickListener {
        //click listener for the minus button
        decreaseYear()
    }
    private val resetBoxes = View.OnClickListener{
        //click listener to deal with the reset button being hit
        escrowBox.text.clear()
        aprBox.text.clear()
        loanBox.text.clear()
        resetYear();
    }

    private fun resetYear() {
        //used to reset the values used with the year box
        yearBox.text = getString(R.string.low_year)
        year = 15
    }


    private fun increaseYear(){
        //used by plus button to raise the year
        if (year == 15){
            yearBox.text = getString(R.string.med_year)
            year = 30
        }
        else if (year == 30){
            yearBox.text = getString(R.string.high_year)
            year = 40
        }

    }

    private fun decreaseYear(){
        //used by the minus button to lower year
        if (year == 30){
            yearBox.text = getString(R.string.low_year)
            year = 15
        }
        else if (year == 40){
            yearBox.text = getString(R.string.med_year)
            year = 30
        }

    }



}