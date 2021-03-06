package com.example.myapplication

import android.os.Bundle
import android.view.Choreographer
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // works fine
        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                println("Recyclview doFrame frameTimeNanos:$frameTimeNanos")
                Choreographer.getInstance().postFrameCallback(this)
            }
        })
        // bugs here
//        Choreographer.getInstance().postFrameCallback{object : Choreographer.FrameCallback {
//            override fun doFrame(frameTimeNanos: Long) {
//                println("Recyclview doFrame frameTimeNanos:$frameTimeNanos")
//                Choreographer.getInstance().postFrameCallback(this)
//            }
//        }}

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.duration = 20000
        // ????????????
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(alphaAnimation)
        binding.fab.startAnimation(animationSet)
        binding.fab.postDelayed({
            Toast.makeText(this@MainActivity,"??????",Toast.LENGTH_LONG).show()
            Thread.sleep(8000)
        },1000)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}