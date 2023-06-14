package com.uberalles.symptomed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.local.entity.FaqEntity
import com.uberalles.symptomed.databinding.ItemFaqBinding

class FaqAdapter : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    private val listFaq = ArrayList<FaqEntity>()

    fun setFaq(faqs: List<FaqEntity>) {
        if (faqs == null) return
        this.listFaq.clear()
        this.listFaq.addAll(faqs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val itemFaqBinding =
            ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(itemFaqBinding)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faq = listFaq[position]
        holder.bind(faq)
    }

    override fun getItemCount(): Int = listFaq.size

    class FaqViewHolder(private val binding: ItemFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FaqEntity) {
            with(binding) {
                binding.tvAnswer.text = faq.answer
                binding.tvQuestion.text = faq.question
                binding.cvQuestion.setOnClickListener {
                    if (tvAnswer.visibility == View.VISIBLE) {
                        val slideUpAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_up)
                        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}
                            override fun onAnimationEnd(animation: Animation) {
                                tvAnswer.visibility = View.GONE
                            }
                            override fun onAnimationRepeat(animation: Animation) {}
                        })
                        tvAnswer.startAnimation(slideUpAnimation)
                        cvAnswer.startAnimation(slideUpAnimation)
                    } else {
                        tvAnswer.visibility = View.VISIBLE
                        val slideDownAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_down)
                        slideDownAnimation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}
                            override fun onAnimationEnd(animation: Animation) {}
                            override fun onAnimationRepeat(animation: Animation) {}
                        })
                        tvAnswer.startAnimation(slideDownAnimation)
                        cvAnswer.startAnimation(slideDownAnimation)
                    }
                }
            }

        }
    }
}