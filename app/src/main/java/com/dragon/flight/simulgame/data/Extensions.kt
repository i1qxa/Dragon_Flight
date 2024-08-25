package com.dragon.flight.simulgame.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DRAGON_FLY_DATA_STORE")
val isMusicOnKey = booleanPreferencesKey("IS_MUSIC_ON")
val isSoundOnKey = booleanPreferencesKey("IS_SOUND_ON")
val lastCompleteLvlKey = intPreferencesKey("LAST_COMPLETE_LVL")