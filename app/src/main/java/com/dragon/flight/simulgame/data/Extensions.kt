package com.dragon.flight.simulgame.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dragon.flight.simulgame.R

fun FragmentManager.launchNewFragment(targetFragment: Fragment) {
    this.beginTransaction().apply {
        replace(R.id.conteinerDragon, targetFragment)
        addToBackStack(null)
        commit()
    }


}