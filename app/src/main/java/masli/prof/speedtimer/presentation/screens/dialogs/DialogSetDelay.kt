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
import masli.prof.speedtimer.databinding.DialogSetDelayBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetDelayListener

class DialogSetDelay : DialogFragment() {

    private var binding: DialogSetDelayBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogSetDelayBinding.inflate(layoutInflater)

        val listener = arguments?.getSerializable(FRAGMENT_KEY) as DialogSetDelayListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            var delay: Long = 300

            binding?.delayTextView?.text = "0.3"

            binding?.delaySeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    binding?.delayTextView?.text = (progress / 10F).toString()
                    delay = (progress * 100).toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })

            binding?.applyButton?.setOnClickListener {
                listener.setDelay(delay)
                dismiss()
            }

            builder.setView(binding?.root)
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