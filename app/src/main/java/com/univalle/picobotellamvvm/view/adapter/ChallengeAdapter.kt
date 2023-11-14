package com.univalle.picobotellamvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.univalle.picobotellamvvm.databinding.ChallengeItemBinding
import com.univalle.picobotellamvvm.model.Challenge
import com.univalle.picobotellamvvm.view.viewholder.ChallengeViewHolder

class ChallengeAdapter (private val listChallenge:MutableList<Challenge>): RecyclerView.Adapter<ChallengeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ChallengeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = listChallenge[position]
        holder.setItemChallenge(challenge)
    }

    override fun getItemCount(): Int {
        return listChallenge.size
    }
}