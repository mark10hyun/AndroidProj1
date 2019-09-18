package com.example.proj1
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counter: Long = 0
    fun getStore() = getPreferences(Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            counter = savedInstanceState.getLong(COUNTER_KEY,0)
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

    override fun onPause()
    {
        super.onPause()
        getStore().edit().putLong(COUNTER_KEY, counter).apply()
    }

    override fun onSaveInstanceState(outState: Bundle?){
        outState?.run{
            putLong(COUNTER_KEY, counter)
        }
        super.onSaveInstanceState(outState)
    }

    companion object{
        private const val COUNTER_KEY = "counter"
    }
}
