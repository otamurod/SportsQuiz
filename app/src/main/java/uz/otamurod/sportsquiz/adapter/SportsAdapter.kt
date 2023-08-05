package uz.otamurod.sportsquiz.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.databinding.ItemSportBinding

class SportsAdapter(
    private var context: Context,
    private var list: List<Sports>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<SportsAdapter.VH>() {

    inner class VH(private val itemSportBinding: ItemSportBinding) :
        RecyclerView.ViewHolder(itemSportBinding.root) {
        fun onBind(sports: Sports, clickListener: OnItemClickListener) {
            itemSportBinding.name.text = sports.name
            itemSportBinding.info.text = sports.fact
            setLogo(sports.id)

            itemSportBinding.root.setOnClickListener {
                clickListener.onItemClicked(sports)
            }
        }

        private fun setLogo(id: Int) {
            val uri = "@drawable/sport_$id" // where myresource (without the extension) is the file
            val imageResource: Int = context.resources.getIdentifier(uri, null, context.packageName)
            val res: Drawable = context.resources.getDrawable(imageResource)
            itemSportBinding.logo.setImageDrawable(res)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemSportBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], itemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

interface OnItemClickListener {
    fun onItemClicked(sports: Sports)
}
