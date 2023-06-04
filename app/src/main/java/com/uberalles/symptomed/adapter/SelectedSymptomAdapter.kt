package com.uberalles.symptomed.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.SelectedSymptom
import com.uberalles.symptomed.data.Symptom

class SelectedSymptomAdapter(private val selectedSymptom: ArrayList<SelectedSymptom>, private val onItemClick: (SelectedSymptom) -> Unit) :
    RecyclerView.Adapter<SelectedSymptomAdapter.SelectedSymptomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedSymptomViewHolder {
        val itemView = View.inflate(parent.context, R.layout.symptom_checked, null)
        return SelectedSymptomViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: SelectedSymptomViewHolder,
        position: Int
    ) {
        val currentItem = selectedSymptom[position]
        holder.selectedSymptomName.text = currentItem.name

        holder.bind(currentItem, onItemClick)
    }

    override fun getItemCount(): Int {
        return selectedSymptom.size
    }

    class SelectedSymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selectedSymptomName: TextView = itemView.findViewById(R.id.selected_symptom_name)

        fun bind(selectedSymptom: SelectedSymptom, onItemClick: (SelectedSymptom) -> Unit) {
            selectedSymptomName.text = selectedSymptom.name
            selectedSymptomName.setOnClickListener {
                onItemClick(selectedSymptom)
            }
        }
    }

}