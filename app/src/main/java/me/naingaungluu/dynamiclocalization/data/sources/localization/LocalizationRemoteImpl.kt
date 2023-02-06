package me.naingaungluu.dynamiclocalization.data.sources.localization

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import me.naingaungluu.dynamiclocalization.data.models.Localization
import me.naingaungluu.dynamiclocalization.data.models.LocalizationBundle
import me.naingaungluu.dynamiclocalization.data.models.LocalizationData
import okio.IOException
import javax.inject.Inject

class LocalizationRemoteImpl @Inject constructor(@ApplicationContext val context: Context) :
    LocalizationRemote {

    override suspend fun getLocalizationBundle(): LocalizationBundle {

        // A Dummy Implementation of fetching Localization from Remote
        delay(5000)
        return LocalizationBundle(
            en = Localization().apply {
                lblGreeting = "Hello from Remote"
                lblSelectedLanguage = "Selected Language From Remote : %@"
                lblEnglish = "English"
                lblChinese = "Chinese"
                lblBurmese = "Burmese"
            },
            mm = Localization().apply {
                lblGreeting = "မင်္ဂလာပါ။ from Remote"
                lblSelectedLanguage = "ရွေးချယ်ထားသောဘာသာစကား From Remote : %@"
                lblEnglish = "အင်္ဂလိပ်"
                lblChinese = "တရုတ်"
                lblBurmese = "ဗမာ"
            },
            cn = Localization().apply {
                lblGreeting = "你好 from Remote"
                lblSelectedLanguage = "选择的语言 From Remote : %@"
                lblEnglish = "英语"
                lblChinese = "缅甸语"
                lblBurmese = "中文"
            }
        )
    }

    override suspend fun getLocalizationList(language: String): List<LocalizationData> {
        val jsonFileString = loadJsonFromAsset(language)
        val gson = Gson()
        val localization = object : TypeToken<List<LocalizationData>>() {}.type
        return gson.fromJson(jsonFileString, localization)
    }

    private fun loadJsonFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}