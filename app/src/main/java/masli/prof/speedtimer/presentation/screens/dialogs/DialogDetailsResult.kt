package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.DialogDetailsResultBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.utils.mapToTime
import org.koin.core.component.getScopeId

class DialogDetailsResult() : DialogFragment() {

    private var binding: DialogDetailsResultBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogDetailsResultBinding.inflate(layoutInflater)

        val currentResult = arguments?.getSerializable(RESULT_KEY) as ResultModel
        val fragment = arguments?.getSerializable(FRAGMENT_KEY)
        val listener = fragment as DialogDetailsResultListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            binding?.dialogEventTextView?.text = when(currentResult.event) {
                EventEnum.Event2by2 -> context?.getString(R.string._2by2)
                EventEnum.Event3by3 -> context?.getString(R.string._3by3)
                EventEnum.EventPyra -> context?.getString(R.string.pyra)
                EventEnum.EventSkewb -> context?.getString(R.string.skewb)
                EventEnum.EventClock -> context?.getString(R.string.clock)
            }
            var timeText = mapToTime(currentResult.time)

            when {
                currentResult.isDNF -> timeText += " ${getString(R.string.dnf)}"
                currentResult.isPlus -> timeText += " ${getString(R.string._2)}"
                else -> {}
            }

            binding?.dialogTimeTextView?.text = timeText
            binding?.dialogScrambleTextView?.text = currentResult.scramble
            binding?.dialogDescriptionEditText?.setText(currentResult.description)
            binding?.dialogSaveButton?.setOnClickListener {
                currentResult.description = binding?.dialogDescriptionEditText?.text.toString().trim()
                listener.updateResult(currentResult)
                dismiss()
            }

            binding?.dialogCopyImageButton?.setOnClickListener {
                val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", "${mapToTime(currentResult.time)} ${currentResult.scramble}")
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), context?.getString(R.string.copied), Toast.LENGTH_SHORT).show()
            }

            binding?.dialogDeleteImageButton?.setOnClickListener {
                listener.deleteResult(currentResult)
                dismiss()
            }

            builder.setView(binding?.root)
            builder.create()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DialogDetailsResult {
            val dialog = DialogDetailsResult()
            dialog.arguments = bundle
            return dialog
        }
    }
}