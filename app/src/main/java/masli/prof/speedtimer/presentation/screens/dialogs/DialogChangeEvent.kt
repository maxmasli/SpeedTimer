package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import masli.prof.domain.enums.EventEnum
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogChangeEventListener

class DialogChangeEvent() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val fragment = arguments?.getSerializable(FRAGMENT_KEY)
        val listener = fragment as DialogChangeEventListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_change_event, null, false)

            val icon2by2 = view.findViewById<AppCompatImageButton>(R.id.dialog_2by2_button)
            val icon3by3 = view.findViewById<AppCompatImageButton>(R.id.dialog_3by3_button)
            val iconPyra = view.findViewById<AppCompatImageButton>(R.id.dialog_pyra_button)
            val iconSkewb = view.findViewById<AppCompatImageButton>(R.id.dialog_skewb_button)
            val iconClock = view.findViewById<AppCompatImageButton>(R.id.dialog_clock_button)

            icon2by2.setOnClickListener {
                listener.setEvent(EventEnum.Event2by2)
                dismiss()
            }

            icon3by3.setOnClickListener {
                listener.setEvent(EventEnum.Event3by3)
                dismiss()
            }

            iconPyra.setOnClickListener {
                listener.setEvent(EventEnum.EventPyra)
                dismiss()
            }

            iconSkewb.setOnClickListener {
                listener.setEvent(EventEnum.EventSkewb)
                dismiss()
            }

            iconClock.setOnClickListener {
                listener.setEvent(EventEnum.EventClock)
                dismiss()
            }

            builder.setView(view)
            builder.create()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DialogChangeEvent {
            val dialog = DialogChangeEvent()
            dialog.arguments = bundle
            return dialog
        }
    }
}