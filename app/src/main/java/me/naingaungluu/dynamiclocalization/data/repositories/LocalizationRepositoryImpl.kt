package me.naingaungluu.dynamiclocalization.data.repositories

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.naingaungluu.dynamiclocalization.R
import me.naingaungluu.dynamiclocalization.data.models.Localization
import me.naingaungluu.dynamiclocalization.data.models.LocalizationBundle
import me.naingaungluu.dynamiclocalization.data.models.LocalizationData
import me.naingaungluu.dynamiclocalization.data.sources.localization.LocalizationLocal
import me.naingaungluu.dynamiclocalization.data.sources.localization.LocalizationRemote
import me.naingaungluu.dynamiclocalization.preferences.AppLanguage
import me.naingaungluu.dynamiclocalization.preferences.PreferenceStorage
import java.util.*
import javax.inject.Inject

class LocalizationRepositoryImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    private val local: LocalizationLocal,
    private val remote: LocalizationRemote,
) : LocalizationRepository {

    //TODO modify
    private val _localizationDataFlow = Channel<List<LocalizationData>>()
    override val localizationDataFlow: Flow<List<LocalizationData>> =
        _localizationDataFlow.receiveAsFlow()

    override val currentAppLanguage: AppLanguage
        get() = preferenceStorage.getAppLanguage()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getLocalizationFromRemote()
        }
    }

    override suspend fun updateLanguage(language: AppLanguage) {
        // _localizationFlow.value = getLocalization(language)
        // preferenceStorage.saveAppLanguage(language)

        val name = when (language) {
            AppLanguage.BURMESE -> "mm.json"
            else -> "en.json"
        }
        remote.getLocalizationList(name).let {
            _localizationDataFlow.send(it)
        }
    }

    private suspend fun getLocalizationFromRemote() {
        // Fetch localization data from remote here
        /* remote.getLocalizationBundle().let {
             cachedLocalizationBundle = it
             _localizationFlow.value = it.getLocalization(currentAppLanguage)
         }*/
        remote.getLocalizationList("en.json").let {
            // cachedLocalizationBundle = it
            _localizationDataFlow.send(it)
        }
    }


}