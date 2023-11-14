package com.univalle.picobotellamvvm.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.univalle.picobotellamvvm.databinding.ChallengeItemBinding
import com.univalle.picobotellamvvm.model.Challenge

class ChallengeViewHolder (binding: ChallengeItemBinding):RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemChallenge(challenge: Challenge){
        bindingItem.tvReto.text = challenge.descriptionChallenge
    }
}