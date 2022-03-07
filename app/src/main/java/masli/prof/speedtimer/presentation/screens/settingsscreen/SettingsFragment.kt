package masli.prof.speedtimer.presentation.screens.settingsscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentSettingsBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetThemeListener
import masli.prof.speedtimer.presentation.screens.dialogs.DialogSetTheme
import masli.prof.speedtimer.presentation.themes.AppTheme
import masli.prof.speedtimer.presentation.themes.Theme
import java.io.Serializable

private const val DIALOG_SET_THEME_TAG = "dialog_set_theme"

class SettingsFragment : Fragment(), DialogSetThemeListener, Serializable {

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        setTheme()

        val settingsStrings = requireContext().resources.getStringArray(R.array.settings).toList()
        val adapter = SettingsAdapter(settingsStrings)
        binding?.settingsListView?.adapter = adapter

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.settingsListView?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, itemClicked, position, id ->
                when (id) {
                    0L -> {
                        val bundle = Bundle()
                        bundle.putSerializable(FRAGMENT_KEY, this)
                        val dialog = DialogSetTheme.newInstance(bundle)
                        dialog.show(childFragmentManager, DIALOG_SET_THEME_TAG)
                    }
                }
            }
    }

    override fun setTheme(theme: Theme) {//after closing dialog set theme
        AppTheme.theme = theme
    }

    private fun setTheme() {
        binding?.settingsConstraintLayout?.background =
            ContextCompat.getDrawable(requireContext(), AppTheme.theme.background)
    }

    inner class SettingsAdapter(private val settingsList: List<String>) : BaseAdapter() {
        override fun getCount(): Int = settingsList.size

        override fun getItem(position: Int): Any = settingsList[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.item_settings, parent, false)
            val itemSettingsTextView = view.findViewById<TextView>(R.id.item_settings_text_view)
            itemSettingsTextView.text = settingsList[position]
            itemSettingsTextView.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
            return view
        }
    }


}