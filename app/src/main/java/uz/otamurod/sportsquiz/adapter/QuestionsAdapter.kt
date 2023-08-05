package uz.otamurod.sportsquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.otamurod.sportsquiz.database.entity.Questions
import uz.otamurod.sportsquiz.databinding.ItemQuestionBinding

class QuestionsAdapter(
    private var list: List<Questions>
) :
    RecyclerView.Adapter<QuestionsAdapter.VH>() {

    inner class VH(private val itemQuestionBinding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(itemQuestionBinding.root) {
        fun onBind(questions: Questions) {
            itemQuestionBinding.question.text = String.format("Q. %s", questions.question)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
