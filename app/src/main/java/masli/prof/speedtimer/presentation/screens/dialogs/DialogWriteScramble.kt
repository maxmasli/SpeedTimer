package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.DialogWriteScrambleBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.presentation.listeners.DialogWriteScrambleListener

class DialogWriteScramble() : DialogFragment() {

    private var binding: DialogWriteScrambleBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogWriteScrambleBinding.inflate(layoutInflater)

        val fragment = arguments?.getSerializable(FRAGMENT_KEY)
        val listener = fragment as DialogWriteScrambleListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            binding?.dialogWriteScrambleApply?.setOnClickListener {
                val scramble = binding?.dialogWriteScrambleEditText?.text.toString()
                if (scramble.isNotEmpty()) {
                    listener.setScramble(scramble)
                    dismiss()
                } else {
                    Toast.makeText(context, "Field must be not empty", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setView(binding?.root)
            builder.create()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DialogWriteScramble{
            val dialog =  DialogWriteScramble()
            dialog.arguments = bundle
            return dialog
        }
    }
}