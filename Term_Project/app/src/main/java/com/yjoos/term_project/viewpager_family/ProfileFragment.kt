package com.yjoos.term_project.viewpager_family

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yjoos.term_project.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var profileViewPagerAdapter: ProfileViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileViewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager)

        profile_viewpager.adapter = profileViewPagerAdapter

        sample_tab.setupWithViewPager(profile_viewpager)
        sample_tab.apply{
            getTabAt(0)?.text = "INFO"
            getTabAt(1)?.text = "OTHER"
        }

        super.onViewCreated(view, savedInstanceState)
    }
}