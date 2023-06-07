package com.uberalles.symptomed.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.Symptom

class SymptomAdapter(private val symptom: ArrayList<Symptom>, private val onItemClick: (Symptom) -> Unit) :
    RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>() {

    private var symptomArrayList: ArrayList<Symptom> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val itemView = View.inflate(parent.context, R.layout.symptom_item, null)
        return SymptomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return symptom.size
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        val currentItem = symptom[position]
        holder.symptomName.text = currentItem.name

        holder.bind(currentItem, onItemClick)
    }

    class SymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symptomName: TextView = itemView.findViewById(R.id.symptom_name)

        fun bind(symptom: Symptom, onItemClick: (Symptom) -> Unit) {
            symptomName.text = symptom.name
            symptomName.setOnClickListener {
                onItemClick(symptom)
            }
        }
    }

}