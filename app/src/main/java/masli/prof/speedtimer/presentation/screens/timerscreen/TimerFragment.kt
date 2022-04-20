package masli.prof.speedtimer.presentation.screens.timerscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
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
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogChangeEventListener
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.presentation.listeners.DialogWriteScrambleListener
import masli.prof.speedtimer.presentation.screens.dialogs.DialogChangeEvent
import masli.prof.speedtimer.presentation.screens.dialogs.DialogDetailsResult
import masli.prof.speedtimer.presentation.screens.dialogs.DialogWriteScramble
import masli.prof.speedtimer.themes.AppTheme
import masli.prof.speedtimer.utils.mapToTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

private const val DIALOG_CHANGE_EVENT_TAG = "dialog_change_event"
private const val DIALOG_DETAILS_RESULT_TAG = "dialog_details_result"
private const val DIALOG_WRITE_SCRAMBLE_TAG = "dialog_write_scramble"

class TimerFragment : Fragment(), DialogChangeEventListener, DialogDetailsResultListener,
    DialogWriteScrambleListener, Serializable {

    private var binding: FragmentTimerBinding? = null
    private val viewModel: TimerViewModel by viewModel<TimerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTimerBinding.inflate(layoutInflater)
        AppTheme.theme = viewModel.getTheme()
        setTheme()

        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAvg()
        //bindings
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.timerIsStartLiveData.value == true) {
                        viewModel.stopTimer()
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            })

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
            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_KEY, this@TimerFragment)
            DialogChangeEvent.newInstance(bundle)
                .show(childFragmentManager, DIALOG_CHANGE_EVENT_TAG)
        }

        binding?.settingsImageButton?.setOnClickListener {
            (activity as MainActivity).findNavController(R.id.nav_host)
                .navigate(R.id.action_timerFragment_to_settingsFragment)
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
                bundle.putSerializable(FRAGMENT_KEY, this@TimerFragment)
                val dialog = DialogDetailsResult.newInstance(bundle)
                dialog.show(childFragmentManager, DIALOG_DETAILS_RESULT_TAG)
            }
        }

        binding?.scrambleUpdateImageButton?.setOnClickListener {
            viewModel.getScramble()
        }

        binding?.writeScrambleImageButton?.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_KEY, this@TimerFragment)
            DialogWriteScramble.newInstance(bundle)
                .show(childFragmentManager, DIALOG_WRITE_SCRAMBLE_TAG)
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
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
            }
        }

        viewModel.isReadyLiveData.observe(viewLifecycleOwner) { isReady ->
            if (isReady) binding?.timerTextView?.setTextColor(requireContext().getColor(R.color.green))
            else binding?.timerTextView?.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
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

                EventEnum.EventPyra -> {
                    binding?.setEventImageButton?.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_pyra
                        )
                    )
                }
                EventEnum.EventSkewb -> {
                    binding?.setEventImageButton?.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_skewb
                        )
                    )
                }
                EventEnum.EventClock -> {
                    binding?.setEventImageButton?.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_clock
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
                    ContextCompat.getDrawable(requireContext(),
                        AppTheme.theme.penaltyButtonBackground)
            }
        }

        viewModel.isPlusLiveData.observe(viewLifecycleOwner) { isPlus ->
            if (isPlus) {
                binding?.plusButton?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_penalty_button_red)
            } else binding?.plusButton?.background =
                ContextCompat.getDrawable(requireContext(), AppTheme.theme.penaltyButtonBackground)
        }

        viewModel.avgResultLiveData.observe(viewLifecycleOwner) { resultAvg ->
            val dnfText = context?.getString(R.string.dnf)
            val avg5text = resultAvg.avg5?.let { mapToTime(it) }
            val avg12text = resultAvg.avg12?.let { mapToTime(it) }
            val avg50text = resultAvg.avg50?.let { mapToTime(it) }
            val avg100text = resultAvg.avg100?.let { mapToTime(it) }
            val best = resultAvg.best?.let {mapToTime(it)}
            val count = resultAvg.count.toString()
            binding?.timerAvg5TextView?.text = avg5text ?: dnfText
            binding?.timerAvg12TextView?.text = avg12text ?: dnfText
            binding?.timerAvg50TextView?.text = avg50text ?: dnfText
            binding?.timerAvg100TextView?.text = avg100text ?: dnfText
            binding?.timerBestTextView?.text = best ?: dnfText
            binding?.timerCountTextView?.text = count
        }

    }

    private fun setTheme() {
        val context = requireContext()
        binding?.timerConstraintLayout?.background =
            ContextCompat.getDrawable(context, AppTheme.theme.background)
        binding?.deleteButton?.background =
            ContextCompat.getDrawable(context, AppTheme.theme.roundButtonBackground)
        binding?.descriptionButton?.background =
            ContextCompat.getDrawable(context, AppTheme.theme.roundButtonBackground)
        binding?.scrambleTextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerAvg5TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerAvg12TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerAvg50TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerAvg100TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerBestTextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerCountTextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelAvg5TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelAvg12TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelAvg50TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelAvg100TextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelBestTextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.timerLabelCountTextView?.setTextColor(context.getColor(AppTheme.theme.textColor))
        binding?.plusButton?.setTextColor(context.getColor(AppTheme.theme.textColorOnMainColor))
        binding?.dnfButton?.setTextColor(context.getColor(AppTheme.theme.textColorOnMainColor))
        binding?.setEventImageButton?.setColorFilter(context.getColor(AppTheme.theme.eventButtonTint))
        binding?.viewResultsImageButton?.setColorFilter(context.getColor(AppTheme.theme.resultsButtonTint))
        binding?.settingsImageButton?.setColorFilter(context.getColor(AppTheme.theme.resultsButtonTint))
        binding?.writeScrambleImageButton?.setColorFilter(context.getColor(AppTheme.theme.iconTint))
        binding?.scrambleUpdateImageButton?.setColorFilter(context.getColor(AppTheme.theme.iconTint))
        //binding?.deleteButton?.setColorFilter(context.getColor(AppTheme.theme.iconTint))
        //binding?.descriptionButton?.setColorFilter(context.getColor(AppTheme.theme.iconTint))
    }

    private fun changeVisibilityOfViews(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        binding?.scrambleTextView?.visibility = visibility
        binding?.penaltyButtonsLinearLayout?.visibility = visibility
        binding?.setEventImageButton?.visibility = visibility
        binding?.viewResultsImageButton?.visibility = visibility
        binding?.timerAvgResultsLinearLayout?.visibility = visibility
        binding?.timerCountResultsLinearLayout?.visibility = visibility
        binding?.writeScrambleImageButton?.visibility = visibility
        binding?.scrambleUpdateImageButton?.visibility = visibility
        binding?.settingsImageButton?.visibility = visibility

    }

    override fun deleteResult(result: ResultModel) {
        viewModel.deleteResultNoID()
    }

    override fun setEvent(event: EventEnum) { // on dialog click
        viewModel.setEvent(event)
    }

    override fun updateResult(result: ResultModel) { // on description click
        viewModel.updateResult(result)
    }

    override fun setScramble(scramble: String) { // on write scramble
        viewModel.setScramble(scramble)
    }
}