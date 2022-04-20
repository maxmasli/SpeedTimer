package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import masli.prof.speedtimer.databinding.DialogAskToDeleteBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogAskToDeleteListener

class DialogAskToDelete : DialogFragment() {

    private var binding: DialogAskToDeleteBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAskToDeleteBinding.inflate(layoutInflater)

        val listener = arguments?.getSerializable(FRAGMENT_KEY) as DialogAskToDeleteListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            binding?.deleteYesButton?.setOnClickListener {
                listener.deleteAllResults()
                dismiss()
            }

            binding?.deleteNoButton?.setOnClickListener {
                dismiss()
            }

            builder.setView(binding?.root)
            builder.create()
        }

    }

    companion object {
        fun newInstance(bundle: Bundle): DialogAskToDelete{
            val dialog = DialogAskToDelete()
            dialog.arguments = bundle
            return dialog
        }
    }
}