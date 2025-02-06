package com.example.happyplaces.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.happyplaces.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.activities.AddHappyPlaces
import com.example.happyplaces.activities.MainActivity
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.models.HappyPlaceModel
import de.hdodenhof.circleimageview.CircleImageView


open class HappyPlaceAdapter(
    private var context: Context,
    private var list: ArrayList<HappyPlaceModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var OnClickListener:onClickListener?=null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {

        return MyViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_happy_place, parent, false)
        )
    }

    fun setOnClickListener(ONClickListener:onClickListener){
        this.OnClickListener=ONClickListener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.findViewById<CircleImageView>(R.id.civ_happy_icon).setImageURI(Uri.parse(model.Image))
            holder.itemView.findViewById<TextView>(R.id.tvTitle).text=(model.title)
            holder.itemView.findViewById<TextView>(R.id.tvDescription).text=(model.Description)
        }

        holder.itemView.setOnClickListener{
            if(OnClickListener!=null){
                OnClickListener!!.onClick(position,model)
            }
        }

    }

    fun removeAt(position: Int){
        val dbHandler=DatabaseHandler(context)
        val isDeleted=dbHandler.deleteHappyPlace(list[position])
        if(isDeleted>0)
        {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun notifyEditedItem(activity: Activity,position:Int,requestCode:Int){
        val intent=Intent(context,AddHappyPlaces::class.java)
        intent.putExtra(MainActivity.EXTRA_DETAILS_OF_HAPPY_PLACE,list[position])
        activity.startActivityForResult(intent,requestCode)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface onClickListener{
        fun onClick(position: Int,model:HappyPlaceModel){}
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}