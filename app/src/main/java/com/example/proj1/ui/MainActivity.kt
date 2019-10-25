package com.example.proj1.ui
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.proj1.R
import com.example.proj1.viewmodel.CountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var countViewModel: CountViewModel

    private var counter: Long = 0


    private fun getUsername()= intent.extras?.get("username").toString().toLowerCase(Locale.US)
    //fun getStore() = getPreferences(Context.MODE_PRIVATE)
   // private var userAccount: String = ""
    //private fun getUsername() = intent.extras?.get("username").toString().toLowerCase(Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {

        //this.userAccount = intent.extras?.get("username").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countViewModel = ViewModelProviders.of(this).get(CountViewModel::class.java)
        countViewModel.getUserCount(getUsername())
            .observe(this, Observer{ updateCounter(it) })
            /*

        if (savedInstanceState != null) {
            updateCounter(savedInstanceState.getLong(userAccount, 0))
        } else if (getStore().contains(userAccount)) {
            updateCounter(getStore().getLong(userAccount, 0))
        }
*/

        counter++
        clickCounter.text = counter.toString()
        addButton.text = when (counter) {
            1L -> "YEE"
            in 2..9 -> addButton.text.toString().plus("!")
            else -> addButton.text
        }

        addButton.setOnClickListener {
            countViewModel.setUserCount(getUsername(), counter+1)

            var animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.mixed)
            addButton.startAnimation(animation)



        }


    }


        private fun updateCounter(count: Long) {
            counter = count
            clickCounter.text = counter.toString()
        }
        /*
    override fun onPause()
    {
        super.onPause()
        getStore().edit().putLong(userAccount, counter).apply()
    }

    override fun onSaveInstanceState(outState: Bundle){
        outState.run{
            putLong(userAccount, counter)
        }
        super.onSaveInstanceState(outState)
    }
    */

// companion object{
        //      private const val COUNTER_KEY = "counter"
        //}
    }

