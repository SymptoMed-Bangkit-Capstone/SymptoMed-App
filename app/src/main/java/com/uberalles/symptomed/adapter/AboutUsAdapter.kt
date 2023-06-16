package com.uberalles.symptomed.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uberalles.symptomed.data.local.entity.TeamEntity
import com.uberalles.symptomed.databinding.ItemTeamBinding

class AboutUsAdapter : RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder>() {
    private val listMember = ArrayList<TeamEntity>()

    fun setMember(members: List<TeamEntity>){
        if (members == null) return
        this.listMember.clear()
        this.listMember.addAll(members)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AboutUsViewHolder {
        val itemTeamBinding =
            ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AboutUsViewHolder(itemTeamBinding)
    }

    override fun onBindViewHolder(holder: AboutUsViewHolder, position: Int) {
        val member = listMember[position]
        holder.bind(member)
    }

    override fun getItemCount(): Int = listMember.size

    class AboutUsViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: TeamEntity) {
            with(binding) {
                tvFullName.text = member.memberName
                tvPath.text = member.memberPath
                ivGithub.setOnClickListener {
                    val githubUrl = member.memberGithub
                    githubUrl.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
                        itemView.context.startActivity(intent)
                    }
                }
                ivLinkedin.setOnClickListener {
                    val linkedInUrl = member.memberLinkedIn
                    linkedInUrl.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
                        itemView.context.startActivity(intent)
                    }
                }
                Glide.with(itemView.context)
                    .load(member.memberPhoto)
                    .circleCrop()
                    .into(ivPhoto)
            }
        }
    }
}