package masli.prof.speedtimer.presentation.screens.resultsscreen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.screens.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.screens.resultsscreen.DialogDetailsResultListener
import masli.prof.speedtimer.presentation.screens.resultsscreen.ResultsFragment
import masli.prof.speedtimer.utils.mapToTime

class DialogDetailsResult(private val fragment: ResultsFragment) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val currentResult = arguments?.getSerializable(RESULT_KEY) as ResultModel
        val updateListener = fragment as DialogDetailsResultListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_details_result, null, false)

            val eventTextView = view.findViewById<AppCompatTextView>(R.id.dialog_event_text_view)
            val timeTextView = view.findViewById<AppCompatTextView>(R.id.dialog_time_text_view)
            val scrambleTextView = view.findViewById<AppCompatTextView>(R.id.dialog_scramble_text_view)
            val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.dialog_description_edit_text)
            val saveButton = view.findViewById<AppCompatButton>(R.id.dialog_save_button)

            eventTextView.text = when(currentResult.event) {
                EventEnum.Event2by2 -> context?.getString(R.string._2by2)
                EventEnum.Event3by3 -> context?.getString(R.string._3by3)
            }
            timeTextView.text = mapToTime(currentResult.time)
            scrambleTextView.text = currentResult.scramble
            descriptionEditText.setText(currentResult.description)
            saveButton.setOnClickListener {
                currentResult.description = descriptionEditText.text.toString()
                updateListener.updateResult(currentResult)
                dismiss()
            }

            builder.setView(view)
            builder.create()
        }
    }

    companion object {
        fun newInstance(fragment: ResultsFragment): DialogDetailsResult {
            return DialogDetailsResult(fragment)
        }
    }
}