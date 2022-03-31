package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import masli.prof.domain.enums.EventEnum
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.DialogChangeEventBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogChangeEventListener

class DialogChangeEvent() : DialogFragment() {

    private var binding: DialogChangeEventBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogChangeEventBinding.inflate(layoutInflater)

        val fragment = arguments?.getSerializable(FRAGMENT_KEY)
        val listener = fragment as DialogChangeEventListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            binding?.dialog2by2Button?.setOnClickListener {
                listener.setEvent(EventEnum.Event2by2)
                dismiss()
            }

            binding?.dialog3by3Button?.setOnClickListener {
                listener.setEvent(EventEnum.Event3by3)
                dismiss()
            }

            binding?.dialogPyraButton?.setOnClickListener {
                listener.setEvent(EventEnum.EventPyra)
                dismiss()
            }

            binding?.dialogSkewbButton?.setOnClickListener {
                listener.setEvent(EventEnum.EventSkewb)
                dismiss()
            }

            binding?.dialogClockButton?.setOnClickListener {
                listener.setEvent(EventEnum.EventClock)
                dismiss()
            }

            builder.setView(binding?.root)
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