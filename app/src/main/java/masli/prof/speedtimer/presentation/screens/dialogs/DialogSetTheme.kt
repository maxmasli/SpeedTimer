package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.DialogSetThemeBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetThemeListener
import masli.prof.speedtimer.themes.AppTheme

class DialogSetTheme : DialogFragment() {

    private var binding: DialogSetThemeBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogSetThemeBinding.inflate(layoutInflater)

        val listener = arguments?.getSerializable(FRAGMENT_KEY) as DialogSetThemeListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)

            for (theme in AppTheme.themes) {
                val itemTheme = layoutInflater.inflate(android.R.layout.simple_list_item_1, null, false)
                val itemTextView = itemTheme.findViewById<TextView>(android.R.id.text1)
                itemTextView.text = theme.name
                itemTheme.setOnClickListener {
                    listener.setTheme(theme)
                    dismiss()
                }
                binding?.dialogThemesLinearLayout?.addView(itemTheme)
            }

            builder.setView(binding?.root)
            builder.create()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DialogSetTheme {
            val dialog = DialogSetTheme()
            dialog.arguments = bundle
            return dialog
        }
    }
}