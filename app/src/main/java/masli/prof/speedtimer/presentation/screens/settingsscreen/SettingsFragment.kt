package masli.prof.speedtimer.presentation.screens.settingsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import masli.prof.domain.enums.ThemeEnum
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentSettingsBinding
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogSetDelayListener
import masli.prof.speedtimer.presentation.listeners.DialogSetThemeListener
import masli.prof.speedtimer.presentation.screens.dialogs.DialogSetDelay
import masli.prof.speedtimer.presentation.screens.dialogs.DialogSetTheme
import masli.prof.speedtimer.themes.Theme
import masli.prof.speedtimer.themes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable
import java.lang.Exception
import java.lang.IllegalArgumentException

private const val DIALOG_SET_THEME_TAG = "dialog_set_theme"
private const val DIALOG_SET_DELAY_TAG = "dialog_set_delay"

class SettingsFragment : Fragment(), DialogSetThemeListener,DialogSetDelayListener ,Serializable {

    private var binding: FragmentSettingsBinding? = null
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        setTheme()

        val settingsStrings = requireContext().resources.getStringArray(R.array.settings).toList()
        val adapter = SettingsAdapter(settingsStrings)
        binding?.settingsListView?.adapter = adapter

        //delay


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

                    1L -> {
                        val bundle = Bundle()
                        bundle.putSerializable(FRAGMENT_KEY, this)
                        val dialog = DialogSetDelay.newInstance(bundle)
                        dialog.show(childFragmentManager, DIALOG_SET_DELAY_TAG)
                    }

                    2L -> {
                        findNavController().navigate(R.id.action_settingsFragment_to_algorithmsFragment)
                    }
                }
            }
    }

    override fun setTheme(theme: Theme) {//after closing dialog set theme
        AppTheme.theme = theme

        val themeEnum = when(theme.name) { // save theme
            DefaultTheme().name -> ThemeEnum.DefaultTheme
            GreenNeonTheme().name -> ThemeEnum.GreenNeonTheme
            GreenYellowNeonTheme().name -> ThemeEnum.GreenYellowNeonTheme
            HoneyTheme().name -> ThemeEnum.HoneyTheme
            else -> throw IllegalArgumentException("wrong theme")
        }
        viewModel.setTheme(themeEnum)
    }

    override fun setDelay(delay: Long) {//after closing dialog set delay
        viewModel.setDelay(delay)
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
            when(position) {
                0 -> {
                    val view = layoutInflater.inflate(R.layout.item_settings, parent, false) // theme item
                    val itemSettingsTextView = view.findViewById<TextView>(R.id.item_settings_text_view)
                    itemSettingsTextView.text = settingsList[position]
                    itemSettingsTextView.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
                    return view
                }
                1 -> {
                    val view = layoutInflater.inflate(R.layout.item_settings_delay, parent, false)
                    val itemSettingsTextView = view.findViewById<TextView>(R.id.item_settings_delay_text_view)
                    val itemSettingsDelayTextView = view.findViewById<TextView>(R.id.item_settings_delay_value_text_view)
                    itemSettingsTextView.text = settingsList[position]

                    itemSettingsTextView.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
                    itemSettingsDelayTextView.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
                    return view
                }
                2 -> {
                    val view = layoutInflater.inflate(R.layout.item_settings_algs, parent, false)
                    val itemSettingsTextView = view.findViewById<TextView>(R.id.item_settings_algs_text_view)
                    itemSettingsTextView.text = settingsList[position]

                    itemSettingsTextView.setTextColor(requireContext().getColor(AppTheme.theme.textColor))
                    return view
                }
                else -> throw Exception("Settings item is not inflated $position")
            }
        }
    }
}