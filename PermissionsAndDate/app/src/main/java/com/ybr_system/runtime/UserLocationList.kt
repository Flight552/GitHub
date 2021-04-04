package com.ybr_system.runtime


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class UserLocationList(
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<UserLocationList.Holder>() {

    private val differ = AsyncListDiffer<UserLocation>(this, DiffUtilCallBack())
    private var isChanged = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflateExtension(R.layout.list_location), onItemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val userPosition = differ.currentList[position]
        holder.bind(userPosition)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateUserLocation(newList: List<UserLocation>) {
        differ.submitList(newList)
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<UserLocation>() {
        override fun areItemsTheSame(oldItem: UserLocation, newItem: UserLocation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserLocation, newItem: UserLocation): Boolean {
            return oldItem == newItem
        }

    }

    @SuppressLint("ResourceAsColor")
    class Holder(view: View, onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val userAvatar = "https://cdna.artstation.com/p/assets/images/images/018/890/440/large/blanche-art-3f5dc439-f612-40ce-9cf5-7d887454e447.jpg?1561121477"
        private val imageColor: ImageView = view.findViewById(R.id.iv_avatar_user_location)
        private var userId: TextView = view.findViewById(R.id.tv_id)
        private var userLongitude: TextView = view.findViewById(R.id.tv_location)
        private var userSpeed: TextView = view.findViewById(R.id.tv_speed)
        private var userDateTime: TextView = view.findViewById(R.id.tv_location_date_time)
        private val dateFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
                .withZone(ZoneId.systemDefault())

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
                userDateTime.setTextColor(R.color.purple_500)
            }
        }

        fun bind(user: UserLocation) {
         //   imageColor.setBackgroundColor(user.avatarColor)
            val fullLocation =
                "Long: ${user.longitude}, Lat: ${user.latitude}, Alt: ${user.altitude}"
            val speed = "Speed: ${user.speed}"
            val id = "ID: ${user.id}"
            val date = "Received: ${dateFormatter.format(user.dateTime)}"
            userLongitude.text = fullLocation
            userSpeed.text = speed
            userId.text = id
            userDateTime.text = date

            Glide.with(itemView)
                .load(userAvatar)
                .error(R.drawable.ic_launcher_background)
                .into(imageColor)


        }
    }
}