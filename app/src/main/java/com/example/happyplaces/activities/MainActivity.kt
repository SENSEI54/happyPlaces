package com.example.happyplaces.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.adapters.HappyPlaceAdapter
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.models.HappyPlaceModel
import com.example.happyplaces.databinding.ActivityMainBinding
import com.example.happyplaces.utils.SwipeToEditCallback
import com.example.happyplaces.utils.SwipedToDeleteCallBack


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabButton.setOnClickListener {
            intent = Intent(this, AddHappyPlaces::class.java)
            startActivityForResult(intent, ADD_HAPPY_PLACES_REQUEST_CODE)
        }
        getHappyPlacesListFromLocalDB()
    }


    private fun setUpHappyPlacesRecycleView(
        happyPlaceList: ArrayList<HappyPlaceModel>
    ) {
        binding.rvAddHappyPlacesList.layoutManager = LinearLayoutManager(this)
        binding.rvAddHappyPlacesList.setHasFixedSize(true)

        val placesAdapter = HappyPlaceAdapter(this, happyPlaceList)
        binding.rvAddHappyPlacesList.adapter = placesAdapter

        placesAdapter.setOnClickListener(object : HappyPlaceAdapter.onClickListener {
            override fun onClick(position: Int, model: HappyPlaceModel) {
                val intent = Intent(this@MainActivity, HappyPlaceDetail::class.java)
                intent.putExtra(EXTRA_DETAILS_OF_HAPPY_PLACE, model)
                startActivity(intent)
            }
        }
        )

        val editSwipeHandler = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvAddHappyPlacesList.adapter as HappyPlaceAdapter
                adapter.notifyEditedItem(
                    this@MainActivity, viewHolder.adapterPosition,
                    ADD_HAPPY_PLACES_REQUEST_CODE
                )
            }
        }
        val editItemTouchHandler = ItemTouchHelper(editSwipeHandler)
        editItemTouchHandler.attachToRecyclerView(binding.rvAddHappyPlacesList)


        val deleteSwipeHandler = object : SwipedToDeleteCallBack(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvAddHappyPlacesList.adapter as HappyPlaceAdapter
                adapter.removeAt(viewHolder.absoluteAdapterPosition)
                getHappyPlacesListFromLocalDB()
            }
        }
        val deleteItemTouchHandler = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHandler.attachToRecyclerView(binding.rvAddHappyPlacesList)
    }


    private fun getHappyPlacesListFromLocalDB() {
        val dbHandler = DatabaseHandler(this)
        val getHappyPlacesList = dbHandler.getHappyPlacesList()

        if (getHappyPlacesList.size > 0) {
            binding.rvAddHappyPlacesList.visibility = View.VISIBLE
            binding.tvNoRecordFound.visibility = View.GONE
            setUpHappyPlacesRecycleView(getHappyPlacesList)
        } else {
            binding.rvAddHappyPlacesList.visibility = View.GONE
            binding.tvNoRecordFound.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_HAPPY_PLACES_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getHappyPlacesListFromLocalDB()
            } else {
                Log.e("Activity", "Call Back or Back Pressed")
            }
        }
    }

    companion object {
        var ADD_HAPPY_PLACES_REQUEST_CODE = 1
        var EXTRA_DETAILS_OF_HAPPY_PLACE = "extra detailed happy place"
    }
}
