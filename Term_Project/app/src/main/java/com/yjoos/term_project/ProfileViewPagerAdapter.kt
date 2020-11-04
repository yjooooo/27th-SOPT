package com.yjoos.term_project


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ProfileViewPagerAdapter(fm: FragmentManager):
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    override fun getItem(position: Int): Fragment = when(position){
        0 -> ChildInfoFragment()
        1 -> ChildOtherFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }


    override fun getCount(): Int = 2
}