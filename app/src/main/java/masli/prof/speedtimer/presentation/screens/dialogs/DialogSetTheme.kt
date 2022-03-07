package masli.prof.speedtimer.presentation.screens.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginTop
import androidx.fragment.app.DialogFragment
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetThemeListener
import masli.prof.speedtimer.presentation.themes.AppTheme

class DialogSetTheme : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = arguments?.getSerializable(FRAGMENT_KEY) as DialogSetThemeListener

        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_set_theme, null, false)
            val dialogThemesLayoutInflater = view.findViewById<LinearLayoutCompat>(R.id.dialog_themes_linear_layout)

            for (theme in AppTheme.themes) {
                val textView = TextView(requireContext())
                val textViewLayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                textViewLayoutParams.setMargins(5, 5, 0, 0)
                textView.text = theme.name
                textView.textSize = 20F
                textView.layoutParams = textViewLayoutParams
                textView.setOnClickListener {
                    listener.setTheme(theme)
                    dismiss()
                }
                dialogThemesLayoutInflater.addView(textView)
            }

            builder.setView(view)
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