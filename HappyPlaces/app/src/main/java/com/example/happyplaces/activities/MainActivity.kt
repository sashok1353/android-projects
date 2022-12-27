package com.example.happyplaces.activities

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.adapters.HappyPlaceAdapter
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.databinding.ActivityMainBinding
import com.example.happyplaces.models.HappyPlaceModel
import com.example.happyplaces.utils.SwipeToEditCallback
import com.example.happyplaces.utils.SwipeToRemoveCallback

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    companion object {
        var ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PLACE_DETAILS = "extra_place_details"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fabAddHappyPlace?.setOnClickListener {
            val intent = Intent(this, AddHappyPlaceActivity::class.java)
            startActivity(intent)
        }

        getHappyPlacesListFromLocalDB()
    }

    override fun onResume() {
        super.onResume()
        getHappyPlacesListFromLocalDB()
    }

    private fun setupHappyPlacesRecyclerView(
        happyPlaceList: ArrayList<HappyPlaceModel>) {

        binding?.rvHappyPlacesList?.layoutManager = LinearLayoutManager(this)
        binding?.rvHappyPlacesList?.setHasFixedSize(true)
        val placesAdapter = HappyPlaceAdapter(this@MainActivity,happyPlaceList)
        binding?.rvHappyPlacesList?.adapter = placesAdapter
        placesAdapter.setOnClickListener(object : HappyPlaceAdapter.OnClickListener{
            override fun onClick(position: Int, model: HappyPlaceModel) {
                val intent = Intent(this@MainActivity,HappyPlaceDetailActivity::class.java)

                intent.putExtra(EXTRA_PLACE_DETAILS,model)

                startActivity(intent)
            }
        })

        val editSwipeHandler = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding?.rvHappyPlacesList?.adapter as HappyPlaceAdapter
                adapter.notifyEditItem(this@MainActivity,
                    viewHolder.adapterPosition,
                    ADD_PLACE_ACTIVITY_REQUEST_CODE)
            }
        }

        val removeSwipeHandler = object : SwipeToRemoveCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding?.rvHappyPlacesList?.adapter as HappyPlaceAdapter
                adapter.removeAt(viewHolder.adapterPosition)

                getHappyPlacesListFromLocalDB()
            }
        }

        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        val removeItemTouchHelper = ItemTouchHelper(removeSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(binding?.rvHappyPlacesList)
        removeItemTouchHelper.attachToRecyclerView(binding?.rvHappyPlacesList)

    }
    private fun getHappyPlacesListFromLocalDB() {
        val dbHandler = DatabaseHandler(this)
        val getHappyPlaceList : ArrayList<HappyPlaceModel> = dbHandler.getHappyPlacesList()

        if(getHappyPlaceList.size > 0) {
            binding?.rvHappyPlacesList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
            setupHappyPlacesRecyclerView(getHappyPlaceList)
        } else {
            binding?.rvHappyPlacesList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }

    }

}