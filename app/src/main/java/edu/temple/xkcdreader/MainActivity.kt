package edu.temple.xkcdreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException


class MainActivity : AppCompatActivity() {

    val comicNumberEditText : TextView by lazy {
        findViewById(R.id.comicNumberEditText)
    }

    val showComicButton : Button by lazy {
        findViewById(R.id.showComicButton)
    }

    val volleyQueue : RequestQueue by lazy {
        Volley.newRequestQueue(this)
    }

    val titleTextView : TextView by lazy {
        findViewById(R.id.titleTextView)
    }

    val altTextView : TextView by lazy {
        findViewById(R.id.altTextView)
    }

    val comicImageView : ImageView by lazy {
        findViewById(R.id.imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showComicButton.setOnClickListener {
            fetchComic(comicNumberEditText.text.toString())
        }
    }

    private fun fetchComic (comicNumber : String) {
        val url = "https://xkcd.com/" + comicNumber + "/info.0.json"


        volleyQueue.add (
            JsonObjectRequest(Request.Method.GET
            , url
            , null
            , {
              Log.d("Response", it.toString())
                    try {
                        titleTextView.text = it.getString("safe_title")
                        altTextView.text = it.getString("alt")
                        Picasso.get().load(it.getString("img")).into(comicImageView)
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            , {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                })
        )

    }
}