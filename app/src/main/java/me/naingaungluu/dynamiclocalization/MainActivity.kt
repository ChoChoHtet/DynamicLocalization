package me.naingaungluu.dynamiclocalization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.naingaungluu.dynamiclocalization.data.models.LocalizationData
import me.naingaungluu.dynamiclocalization.databinding.ActivityMainBinding
import me.naingaungluu.dynamiclocalization.ui.screens.home.HomeScreen
import me.naingaungluu.dynamiclocalization.ui.screens.home.HomeViewModel
import me.naingaungluu.dynamiclocalization.ui.theme.DynamicLocalizationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContent {
            DynamicLocalizationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }*/

        //View-Based
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.localizationDataFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                //TODO way 1
                binding.localization = it
                // TODO Alternate way-2 , please don't forget to remove data variable in xml
                /* binding.apply {
                     tvGreeting.text = it[0].value
                     tvLanguage.text = it[1].value
                     btnChinese.text = it[2].value
                     btnEnglish.text = it[3].value
                     btnBurmese.text = it[4].value
                 }*/
            }
            .launchIn(lifecycleScope)

        binding.btnBurmese.setOnClickListener {
            viewModel.switchToBurmese()
        }
        binding.btnEnglish.setOnClickListener {
            viewModel.switchToEnglish()
        }
    }
}