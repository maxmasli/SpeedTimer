package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import masli.prof.domain.enums.EventEnum
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.screens.DialogChangeEventListener
import masli.prof.speedtimer.presentation.screens.TimerFragment

class DialogChangeEvent(fragment: TimerFragment) : DialogFragment() {

    private val eventListener = fragment as DialogChangeEventListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_change_event, null, false)

            val icon2by2 = view.findViewById<AppCompatImageButton>(R.id.dialog_2by2_button)
            val icon3by3 = view.findViewById<AppCompatImageButton>(R.id.dialog_3by3_button)

            icon2by2.setOnClickListener {
                eventListener.onSetEvent(EventEnum.Event2by2)
                dismiss()
            }

            icon3by3.setOnClickListener {
                eventListener.onSetEvent(EventEnum.Event3by3)
                dismiss()
            }

            builder.setView(view)
            builder.create()
        }
    }

    companion object {
        fun newInstance(fragment: TimerFragment): DialogChangeEvent{
            return DialogChangeEvent(fragment)
        }
    }
}