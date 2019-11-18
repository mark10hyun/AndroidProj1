package com.example.proj1.ui
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Typeface
import android.view.animation.AnimationUtils
import android.widget.Advanceable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.proj1.R
import com.example.proj1.viewmodel.CountViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var countViewModel: CountViewModel

    private var weatherData: TextView? = null

    private var counter: Long = 0


    lateinit var mAdView : AdView

    private fun getUsername()= intent.extras?.get("username").toString().toLowerCase(Locale.US)
    //fun getStore() = getPreferences(Context.MODE_PRIVATE)
   // private var userAccount: String = ""
    //private fun getUsername() = intent.extras?.get("username").toString().toLowerCase(Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {



        //this.userAccount = intent.extras?.get("username").toString()
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        weatherData = findViewById(R.id.textView)


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

    internal fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val stringBuilder = "Country: " +
                            weatherResponse.sys!!.country +
                            "\n" +
                            "Temperature: " +
                            weatherResponse.main!!.temp +
                            "\n" +
                            "Temperature(Min): " +
                            weatherResponse.main!!.temp_min +
                            "\n" +
                            "Temperature(Max): " +
                            weatherResponse.main!!.temp_max +
                            "\n" +
                            "Humidity: " +
                            weatherResponse.main!!.humidity +
                            "\n" +
                            "Pressure: " +
                            weatherResponse.main!!.pressure

                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherData!!.text = t.message
            }
        })
    }
        companion object {

            var BaseUrl = "http://api.openweathermap.org/"
            var AppId = "3a5e571b670bba24070468114b7fb8bd"
            var lat = "33.787914"
            var lon = "-117.853104"

        }
    }

