package com.razer.guessnumber

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.razer.guessnumber.databinding.ActivityMainBinding
import java.lang.NumberFormatException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private var secret: Int = Random.nextInt(10) +1
    private var guessTimes :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, " onCreate secret number = $secret")
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView(binding.root)

        initViews()
        initListener()

    }

    private fun initListener() {
        binding.guessButton.setOnClickListener {
            guess()
        }

        binding.reset.setOnClickListener {
            reset()
        }

        binding.editTextNumberDecimal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged: ")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged: ")
                if (s?.length!! == 0) {
                    binding.guessButton.setEnabled(false)
                } else {
                    binding.guessButton.setEnabled(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged: ")
            }
        })
    }

    private fun initViews() {
        binding.guessButton.setEnabled(false)
        binding.guessTimes.text = "0 Times"
        binding.editTextNumberDecimal.setText("")
        binding.editTextNumberDecimal.hint = "Input answer:"
    }

    private fun guess() {

        binding.guessTimes.text = (++guessTimes).toString() + " Times"

        val s = binding.editTextNumberDecimal.text.toString()
        if (s.length == 0) {
            AlertDialog.Builder(this)
                .setTitle("Alert Message")
                .setMessage("Please inset a decimal number")
                .setPositiveButton("OK", null).show()
        } else {
            try {
                val num = s.toInt()
                Log.d(TAG, "Guess: $num")
                val message =
                    if ( num > secret ) {
                        "Try smaller"
                    } else if (num < secret) {
                        "Try bigger"
                    } else {
                        "You're Right"
                    }
                AlertDialog.Builder(this)
                    .setTitle("Guess Result")
                    .setMessage(message)
                    .setPositiveButton( "OK" ) { _,_ ->
                        if ( message == "You're Right" ) {
                            reset()
                        }
                    }
                    .show()
            } catch (e: NumberFormatException) {
                Log.d(TAG, "guess: exception")
            }
        }
    }

    private fun reset() {
        initViews()
        guessTimes = 0
    }

}