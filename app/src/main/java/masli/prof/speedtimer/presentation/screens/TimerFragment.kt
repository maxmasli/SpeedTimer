package masli.prof.speedtimer.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.databinding.FragmentTimerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private val viewModel: TimerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTextButton.setOnClickListener {
            viewModel.saveResult(
                ResultModel(
                    id = 0,
                    scramble = "asdasd",
                    time = 10,
                    description = "sssss",
                    isDNF = false,
                    isPlus = false
                )
            )
        }

        binding.getTextButton.setOnClickListener {
            viewModel.getAllResults()
        }

        viewModel.allResultsLiveData.observe(viewLifecycleOwner) { listResults ->
            listResults.forEach { result ->
                Log.e("AAA", "Result: ${result.id} ${result.scramble}")
            }
        }
    }
}