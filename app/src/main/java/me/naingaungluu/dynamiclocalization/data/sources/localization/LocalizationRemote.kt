package me.naingaungluu.dynamiclocalization.data.sources.localization

import me.naingaungluu.dynamiclocalization.data.models.Localization
import me.naingaungluu.dynamiclocalization.data.models.LocalizationBundle
import me.naingaungluu.dynamiclocalization.data.models.LocalizationData

interface LocalizationRemote {
    suspend fun getLocalizationBundle(): LocalizationBundle
    suspend fun getLocalizationList(language:String):List<LocalizationData>
}