package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetDelayListener

class DialogSetDelay : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = arguments?.getSerializable(FRAGMENT_KEY) as DialogSetDelayListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_set_delay, null, false)
            val delayTextView = view.findViewById<AppCompatTextView>(R.id.delay_text_view)
            val delaySeekBar = view.findViewById<AppCompatSeekBar>(R.id.delay_seek_bar)
            val applyButton = view.findViewById<AppCompatButton>(R.id.apply_button)

            var delay: Long = 300

            delayTextView.text = "0.3"

            delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    delayTextView.text = (progress / 10F).toString()
                    delay = (progress * 100).toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })

            applyButton.setOnClickListener {
                listener.setDelay(delay)
                dismiss()
            }

            builder.setView(view)
            builder.create()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DialogSetDelay {
            val dialog = DialogSetDelay()
            dialog.arguments = bundle
            return dialog
        }
    }
}