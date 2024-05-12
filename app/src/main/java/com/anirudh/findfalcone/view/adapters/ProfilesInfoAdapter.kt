package com.anirudh.findfalcone.view.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//
//class ProfilesInfoAdapter(
//    private val context: Context,
//    private val onAccepted: (ProfileInfo) -> Unit,
//    private val onDeclined: (ProfileInfo) -> Unit
//) : RecyclerView.Adapter<ProfilesInfoAdapter.ResultsViewHolder>() {
//
//
//    private var profileInfoList: ArrayList<ProfileInfo> = arrayListOf()
//
//    fun updateList(info: List<ProfileInfo>) {
//        profileInfoList = info as ArrayList<ProfileInfo>
//        notifyDataSetChanged()
//    }
//
//
//    inner class ResultsViewHolder(val binding: UserProfileItemBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
//        return ResultsViewHolder(
//            UserProfileItemBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return profileInfoList.size
//    }
//
//    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
//        val user: ProfileInfo = profileInfoList[position]
//        holder.binding.apply {
//            Glide.with(holder.itemView.context).load(user.picture?.large)
//                .placeholder(R.drawable.baseline_account_box_24).into(iv)
//            tvName.text = String.format(
//                context.getString(R.string.userName), user.name?.first, user.name?.last
//            )
//            tvGender.text = user.gender
//            tvAddress.text = String.format(
//                context.getString(R.string.address), user.location?.state, user.location?.country
//            )
//            when (user.profileStatus) {
//                ProfileStatus.ACCEPTED, ProfileStatus.DECLINED -> {
//                    actionsContainer.visibility = View.GONE
//                    tvStatus.text = if (user.profileStatus == ProfileStatus.ACCEPTED) {
//                        context.getString(R.string.accepted)
//                    } else {
//                        context.getString(R.string.declined)
//                    }
//                    tvStatus.visibility = View.VISIBLE
//                }
//
//                else -> {
//                    actionsContainer.visibility = View.VISIBLE
//                    tvStatus.visibility = View.GONE
//                }
//            }
//            ivCheck.setOnClickListener {
//                onAccepted.invoke(user)
//                notifyItemChanged(position)
//            }
//            ivCross.setOnClickListener {
//                onDeclined.invoke(user)
//                notifyItemChanged(position)
//            }
//        }
//    }
//}