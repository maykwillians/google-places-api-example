package com.maykmenezes.googleplacesapiexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maykmenezes.googleplacesapiexample.R

class PlacesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, ListPlacesFragment())
        fragmentTransaction.commit()
    }
}