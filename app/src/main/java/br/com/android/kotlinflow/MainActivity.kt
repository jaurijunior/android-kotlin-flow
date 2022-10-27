package br.com.android.kotlinflow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.android.kotlinflow.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            btnLiveData.setOnClickListener {
                viewModel.triggerLiveData()
            }

            btnStateFlow.setOnClickListener {
                viewModel.triggerStateFlow()
            }

            btnFlow.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.triggerFlow().collectLatest {
                        binding.textFlow.text = it
                    }
                }

            }

            btnSharedFlow.setOnClickListener {
                viewModel.triggerSharedFlow()
            }

        }

        subscribeToObservables()

    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.textLiveData.text = it
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.textStateFlow.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                binding.textSharedFlow.text = it
            }
        }
    }
}