package com.example.happyplaces.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.models.HappyPlaceModel
import com.example.happyplaces.databinding.ActivityHappyPlaceDetailBinding


class HappyPlaceDetail : AppCompatActivity() {
    private lateinit var binding:ActivityHappyPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHappyPlaceDetailBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

        var happyPlaceModel:HappyPlaceModel?=null

        if(intent.hasExtra(MainActivity.EXTRA_DETAILS_OF_HAPPY_PLACE))
        {
            happyPlaceModel=intent.getParcelableExtra(MainActivity.EXTRA_DETAILS_OF_HAPPY_PLACE)as HappyPlaceModel?
        }

        if(happyPlaceModel!=null){
            setSupportActionBar(binding.toolIdHappyPlaceDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title=happyPlaceModel.title

            binding.toolIdHappyPlaceDetail.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            binding.ivPlaceImage.setImageURI(Uri.parse(happyPlaceModel.Image))
            binding.tvDescription.text=happyPlaceModel.Description
            binding.tvLocation.text=happyPlaceModel.Location
        }

        binding.btnViewOnMap.setOnClickListener{
            val intent=Intent(this,MapView::class.java)
            intent.putExtra(MainActivity.EXTRA_DETAILS_OF_HAPPY_PLACE,happyPlaceModel)
            startActivity(intent)
        }
    }
}