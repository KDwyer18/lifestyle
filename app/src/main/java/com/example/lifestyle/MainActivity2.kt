package com.example.lifestyle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.RelativeLayout
import android.widget.TextView

private var first: String? = ""
private var middle: String? = ""
private var last: String? = ""

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        first = intent.getStringExtra("EXTRA_FIRST")
        middle = intent.getStringExtra("EXTRA_MIDDLE")
        last = intent.getStringExtra("EXTRA_LAST")

        val textView: TextView = findViewById<TextView>(R.id.textView)
        textView.text = first.toString() + " " + last.toString() + " has logged in!"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_FIRST", first)
        outState.putString("EXTRA_MIDDLE", middle)
        outState.putString("EXTRA_LAST", last)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        first = savedInstanceState.getString("EXTRA_FIRST")
        middle = savedInstanceState.getString("EXTRA_MIDDLE")
        last = savedInstanceState.getString("EXTRA_LAST")
    }
}