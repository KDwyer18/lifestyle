package com.example.lifestyle

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap

private var firstName: EditText? = null
private var lastName: EditText? = null
private var middleName: EditText? = null
private var profileButton: Button? = null
private var submitButton: Button? = null

private var pfpView: ImageView? = null
//pri


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstName = findViewById(R.id.firstName)
        middleName = findViewById(R.id.middleName)
        lastName = findViewById(R.id.lastName)
        submitButton = findViewById(R.id.loginButton)
        profileButton = findViewById(R.id.pfpButton)

        pfpView = findViewById(R.id.pfpImage)

        submitButton!!.setOnClickListener(this)
        profileButton!!.setOnClickListener(this)

    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.loginButton -> {
                if(!firstName!!.text.toString().isNullOrEmpty() && !lastName!!.text.toString().isNullOrEmpty()) {

                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("EXTRA_FIRST", firstName!!.text.toString())
                    intent.putExtra("EXTRA_MIDDLE", middleName!!.text.toString())
                    intent.putExtra("EXTRA_LAST", lastName!!.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "First and Last name is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.pfpButton -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex: ActivityNotFoundException){
                    //Do error handling here
                }
            }


        }

    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            pfpView = findViewById<View>(R.id.pfpImage) as ImageView

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                pfpView!!.setImageBitmap(thumbnailImage)
            }
            else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                pfpView!!.setImageBitmap(thumbnailImage)
            }


        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_FIRST", firstName!!.text.toString())
        outState.putString("EXTRA_MIDDLE", middleName!!.text.toString())
        outState.putString("EXTRA_LAST", lastName!!.text.toString())
        outState.putParcelable("PFP_IMAGE", pfpView!!.drawable.toBitmap());
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        firstName!!.setText(savedInstanceState.getString("EXTRA_FIRST"))
        middleName!!.setText(savedInstanceState.getString("EXTRA_MIDDLE"))
        lastName!!.setText(savedInstanceState.getString("EXTRA_LAST"))
        pfpView!!.setImageBitmap(savedInstanceState.getParcelable("PFP_IMAGE"))
    }

}
