package masli.prof.speedtimer.presentation.screens.timerscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentTimerBinding
import masli.prof.speedtimer.presentation.MainActivity
import masli.prof.speedtimer.presentation.bundlekeys.EVENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogChangeEventListener
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.presentation.screens.dialogs.DialogChangeEvent
import masli.prof.speedtimer.presentation.screens.dialogs.DialogDetailsResult
import masli.prof.speedtimer.utils.mapToTime
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DIALOG_CHANGE_EVENT_TAG = "dialog_change_event"
private const val DIALOG_DETAILS_RESULT_TAG = "dialog_details_result"

class TimerFragment : Fragment(), DialogChangeEventListener, DialogDetailsResultListener {

    private var binding: FragmentTimerBinding? = null
    private val viewModel: TimerViewModel by viewModel<TimerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bindings
        binding?.timerTextView?.setOnTouchListener { _, motionEvent ->
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

        binding?.setEventImageButton?.setOnClickListener {

            DialogChangeEvent.newInstance(this@TimerFragment).show(childFragmentManager, DIALOG_CHANGE_EVENT_TAG)
        }

        binding?.dnfButton?.setOnClickListener {
            viewModel.setDNFResult()
        }

        binding?.plusButton?.setOnClickListener {
            viewModel.setPlusResult()
        }

        binding?.deleteButton?.setOnClickListener {
            viewModel.deleteResultNoID()
        }

        binding?.viewResultsImageButton?.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(EVENT_KEY, viewModel.currentEventLiveData.value)
            (activity as MainActivity).findNavController(R.id.nav_host)
                .navigate(R.id.action_timerFragment_to_resultsFragment, bundle)
        }

        binding?.descriptionButton?.setOnClickListener {
            if (viewModel.currentResult != null) {
                val bundle = Bundle()
                bundle.putSerializable(RESULT_KEY, viewModel.currentResult)
                val dialog = DialogDetailsResult.newInstance(this@TimerFragment, bundle)
                dialog.show(childFragmentManager, DIALOG_DETAILS_RESULT_TAG)
            }
        }

        //observes
        viewModel.scrambleLiveData.observe(viewLifecycleOwner) { scramble ->
            binding?.scrambleTextView?.text = scramble
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner) { time ->
            binding?.timerTextView?.text = time
            changeVisibilityOfViews(true)
        }

        viewModel.timerIsStartLiveData.observe(viewLifecycleOwner) { timerIsStart ->
            if (timerIsStart) {
                binding?.timerTextView?.text = "..."
                changeVisibilityOfViews(false)
            }

        }

        viewModel.isReadyLiveData.observe(viewLifecycleOwner) { isReady ->
            if (isReady) binding?.timerTextView?.setTextColor(requireContext().getColor(R.color.green))
            else binding?.timerTextView?.setTextColor(requireContext().getColor(R.color.black))
        }

        viewModel.currentEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                EventEnum.Event3by3 -> {
                    binding?.setEventImageButton?.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_3by3
                        )
                    )
                }
                EventEnum.Event2by2 -> {
                    binding?.setEventImageButton?.setImageDrawable(
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
                binding?.dnfButton?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button_red)
            } else {
                binding?.dnfButton?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button)
            }
        }

        viewModel.isPlusLiveData.observe(viewLifecycleOwner) { isPlus ->
            if (isPlus) {
                binding?.plusButton?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button_red)
            } else binding?.plusButton?.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button)
        }

        viewModel.avgResultLiveData.observe(viewLifecycleOwner) { resultAvg ->
            val dnfText = context?.getString(R.string.dnf)
            val avg5text = resultAvg.avg5?.let { mapToTime(it) }
            val avg12text = resultAvg.avg12?.let { mapToTime(it) }
            val avg50text = resultAvg.avg50?.let { mapToTime(it) }
            val avg100text = resultAvg.avg100?.let { mapToTime(it) }
            binding?.timerAvg5TextView?.text = avg5text ?: dnfText
            binding?.timerAvg12TextView?.text = avg12text ?: dnfText
            binding?.timerAvg50TextView?.text = avg50text ?: dnfText
            binding?.timerAvg100TextView?.text = avg100text ?: dnfText
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAvg()
        viewModel.clearTimer()
    }

    private fun changeVisibilityOfViews(isVisible: Boolean) {
        val visibility = if(isVisible) View.VISIBLE else View.GONE
        binding?.scrambleTextView?.visibility = visibility
        binding?.penaltyButtonsLinearLayout?.visibility = visibility
        binding?.setEventImageButton?.visibility = visibility
        binding?.viewResultsImageButton?.visibility = visibility
        binding?.timerAvgResultsLinearLayout?.visibility = visibility

    }

    override fun setEvent(event: EventEnum) { // on dialog click
        viewModel.setEvent(event)
    }

    override fun updateResult(result: ResultModel) { // on description click
        viewModel.updateResult(result)
    }

    override fun deleteResult(result: ResultModel) {
        viewModel.deleteResultNoID()
    }
}