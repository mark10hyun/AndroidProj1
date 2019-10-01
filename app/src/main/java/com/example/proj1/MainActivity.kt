package com.example.proj1
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counter: Long = 0
    fun getStore() = getPreferences(Context.MODE_PRIVATE)
    private var userAccount : String = ""
    override fun onCreate(savedInstanceState: Bundle?)
    {

        this.userAccount = intent.extras?.get("username").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            updateCounter(savedInstanceState.getLong(userAccount,0))
        }
        else if(getStore().contains(userAccount))
        {
            updateCounter(getStore().getLong(userAccount,0))
        }
        addButton.setOnClickListener {
            counter++
            clickCounter.text = counter.toString()
            addButton.text = when (counter) {
                1L -> "YEE"
                in 2..9 -> addButton.text.toString().plus("!")
                else -> addButton.text
            }
        }

    }
    private fun updateCounter(count: Long)
    {
        counter = count
        clickCounter.text = counter.toString()
    }
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
// companion object{
  //      private const val COUNTER_KEY = "counter"
    //}
}
