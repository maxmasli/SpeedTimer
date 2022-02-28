package masli.prof.speedtimer.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import masli.prof.domain.enums.EventEnum
import masli.prof.speedtimer.R
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bindings
        binding.timerTextView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> { // pressed down
                    viewModel.timerActionDown()
                }

                MotionEvent.ACTION_UP -> { // pressed up
                    viewModel.timerActionUp()
                }
            }
            true
        }

//        binding.setEventImageButton.setOnClickListener {
//            viewModel.setEvent(EventEnum.Event2by2)
//        }

        binding.dnfButton.setOnClickListener {
            viewModel.setDNFResult()
        }

        binding.plusButton.setOnClickListener {
            viewModel.setPlusResult()
        }

        //observes
        viewModel.scrambleLiveData.observe(viewLifecycleOwner) { scramble ->
            binding.scrambleTextView.text = scramble
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner) { time ->
            binding.timerTextView.text = time.toString()
        }

        viewModel.timerIsStartLiveData.observe(viewLifecycleOwner) { timerIsStart ->
            if (timerIsStart) binding.timerTextView.text = "..."
        }

        viewModel.isReadyLiveData.observe(viewLifecycleOwner) { isReady ->
            if (isReady) binding.timerTextView.setTextColor(requireContext().getColor(R.color.green))
            else binding.timerTextView.setTextColor(requireContext().getColor(R.color.black))
        }

        viewModel.currentEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                EventEnum.Event3by3 -> {
                    binding.setEventImageButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_3by3
                        )
                    )
                }
                EventEnum.Event2by2 -> {
                    binding.setEventImageButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_2by2
                        )
                    )
                }
            }
        }

        viewModel.isDNFLiveData.observe(viewLifecycleOwner) { isDNF ->
            if (isDNF) {
                binding.dnfButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button_red)
            } else {
                binding.dnfButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button)
            }
        }

        viewModel.isPlusLiveData.observe(viewLifecycleOwner) { isPlus ->
            if (isPlus) {
                binding.plusButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button_red)
            } else binding.plusButton.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button)
        }
    }
}