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
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.utils.mapToTime
import org.koin.core.component.getScopeId

class DialogDetailsResult() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //TODO add binding
        val currentResult = arguments?.getSerializable(RESULT_KEY) as ResultModel
        val fragment = arguments?.getSerializable(FRAGMENT_KEY)
        val listener = fragment as DialogDetailsResultListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_details_result, null, false)

            val eventTextView = view.findViewById<AppCompatTextView>(R.id.dialog_event_text_view)
            val timeTextView = view.findViewById<AppCompatTextView>(R.id.dialog_time_text_view)
            val scrambleTextView = view.findViewById<AppCompatTextView>(R.id.dialog_scramble_text_view)
            val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.dialog_description_edit_text)
            val saveButton = view.findViewById<AppCompatButton>(R.id.dialog_save_button)
            val copyButton = view.findViewById<AppCompatImageButton>(R.id.dialog_copy_image_button)
            val deleteButton = view.findViewById<AppCompatImageButton>(R.id.dialog_delete_image_button)

            eventTextView.text = when(currentResult.event) {
                EventEnum.Event2by2 -> context?.getString(R.string._2by2)
                EventEnum.Event3by3 -> context?.getString(R.string._3by3)
                EventEnum.EventPyra -> context?.getString(R.string.pyra)
            }
            var timeText = mapToTime(currentResult.time)

            when {
                currentResult.isDNF -> timeText += " ${getString(R.string.dnf)}"
                currentResult.isPlus -> timeText += " ${getString(R.string._2)}"
                else -> {}
            }

            timeTextView.text = timeText
            scrambleTextView.text = currentResult.scramble
            descriptionEditText.setText(currentResult.description)
            saveButton.setOnClickListener {
                currentResult.description = descriptionEditText.text.toString().trim()
                listener.updateResult(currentResult)
                dismiss()
            }

            copyButton.setOnClickListener {
                val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", "${mapToTime(currentResult.time)} ${currentResult.scramble}")
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
            }

            deleteButton.setOnClickListener {
                listener.deleteResult(currentResult)
                dismiss()
            }

            builder.setView(view)
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