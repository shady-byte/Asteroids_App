package com.example.asteroidapp.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asteroidapp.R
import com.example.asteroidapp.dataBase.AsteroidData
import com.example.asteroidapp.dataBase.NasaTodayImage
import com.example.asteroidapp.viewModels.AsteroidsListViewModel
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("AsteroidTypeSetter")
fun ImageView.bindAsteroidTypeImage(itemType: Boolean?) {
    itemType?.let {
        if(itemType) {
            this.setImageResource(R.drawable.asteroid_hazardous_icon)
            this.setColorFilter(ContextCompat.getColor(context, R.color.hazardous_red))
            this.contentDescription = "image describe that asteroid is hazardous"
        } else {
            this.setImageResource(R.drawable.asteroid_safe_icon)
            this.setColorFilter(ContextCompat.getColor(context, R.color.image_hazardous_icon_color))
            this.contentDescription = "image describes that asteroid is not hazardous"
        }
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("AsteroidListAdapter")
//asteroidList: MutableList<AsteroidData>?
fun RecyclerView.bindRecyclerView(asteroidList: MutableList<AsteroidData>?) {
    asteroidList?.let {
        val adapter = this.adapter as AsteroidsListAdapter
        adapter.submitList(asteroidList)
    }
}

@BindingAdapter("AsteroidHazardousSetter")
fun ImageView.bindAsteroidImage(itemType: Boolean?) {
    itemType?.let {
        if(itemType) {
            this.setImageResource(R.drawable.asteroid_hazardous)
            this.contentDescription = "image describes that asteroid is hazardous"
        } else {
            this.setImageResource(R.drawable.asteroid_safe)
            this.contentDescription = "image describes that asteroid is not hazardous"
        }
    }
}

@BindingAdapter("TodayImageBind")
fun ImageView.bindTodayImage(image: NasaTodayImage?) {
    image?.let {
        var imageUri: Uri?= null
        if(image.checkIsImage) {
            imageUri = it.url.toUri().buildUpon().scheme("https").build()
        }
        Glide.with(this.context)
            .load(imageUri)
            .placeholder(R.drawable.ic_image_icon)
            .error(R.drawable.ic_no_image_icon)
            .into(this)
    }
}
/*
@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["FilterStatus","AsteroidList","FilteredAsteroidList"])
fun RecyclerView.bindFilterStatus(filter: Boolean?, asteroidList: MutableList<AsteroidData>?,
                                  filteredAsteroidList: MutableList<AsteroidData>?) {
    val adapter = this.adapter as AsteroidsListAdapter
    filter?.let {
        if(filter) {
            /*
            val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(todayString)

            asteroidList?.removeAll { data ->
                val firstDate= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(data.closeApproachDate)
                //remove all data of dates less than today
                firstDate?.compareTo(todayDate) == -1
            }

             */
            adapter.submitList(filteredAsteroidList)
            adapter.notifyDataSetChanged()
        } else {
            adapter.submitList(asteroidList)
            adapter.notifyDataSetChanged()
        }
        Log.d("MainTest","filter $filter")
    }
}
 */


