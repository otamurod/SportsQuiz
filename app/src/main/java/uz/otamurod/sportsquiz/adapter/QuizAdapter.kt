package uz.otamurod.sportsquiz.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.databinding.ItemQuizBinding

class QuizAdapter(
    private var context: Context,
    private var list: List<Sports>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<QuizAdapter.VH>() {

    inner class VH(private val itemQuizBinding: ItemQuizBinding) :
        RecyclerView.ViewHolder(itemQuizBinding.root) {
        fun onBind(sports: Sports, clickListener: OnItemClickListener) {
            itemQuizBinding.name.text = sports.name
            itemQuizBinding.info.text = "Questions - 10"
            setLogo(sports.id)

            itemQuizBinding.root.setOnClickListener {
                clickListener.onItemClicked(sports)
            }
        }

        private fun setLogo(id: Int) {
            val uri = "@drawable/logo_$id" // where myresource (without the extension) is the file
            val imageResource: Int = context.resources.getIdentifier(uri, null, context.packageName)
            val res: Drawable = context.resources.getDrawable(imageResource)
            itemQuizBinding.logo.setImageDrawable(res)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], itemClickListener)
    }

    override fun getItemCount(): Int = list.size

}
