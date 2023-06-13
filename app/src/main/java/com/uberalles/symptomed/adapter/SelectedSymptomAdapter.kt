package com.uberalles.symptomed.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.local.symptom.SelectedSymptom

class SelectedSymptomAdapter(
    private val selectedSymptom: ArrayList<SelectedSymptom>,
    private val onItemDelete: (SelectedSymptom) -> Unit
) :
    RecyclerView.Adapter<SelectedSymptomAdapter.SelectedSymptomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedSymptomViewHolder {
        val itemView = View.inflate(parent.context, R.layout.symptom_checked, null)
        return SelectedSymptomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return selectedSymptom.size
    }

    override fun onBindViewHolder(
        holder: SelectedSymptomViewHolder,
        position: Int
    ) {
        val currentItem = selectedSymptom[position]
        holder.selectedSymptomName.text = currentItem.name

        holder.bind(currentItem, onItemDelete)
    }

    class SelectedSymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selectedSymptomName: TextView = itemView.findViewById(R.id.selected_symptom_name)

        fun bind(selectedSymptom: SelectedSymptom, onItemDelete: (SelectedSymptom) -> Unit) {
            selectedSymptomName.text = selectedSymptom.name
            selectedSymptomName.setOnClickListener {
                onItemDelete(selectedSymptom)
            }
        }
    }

}