package masli.prof.speedtimer.presentation.themes

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import masli.prof.speedtimer.R

class HoneyTheme(
    override val name: String = "Honey",
    @DrawableRes
    override val background: Int = R.drawable.bg_honey_orange,
    @ColorRes
    override val textColor: Int = R.color.black,
    @ColorRes
    override val textColorOnMainColor: Int = R.color.black,
    @DrawableRes
    override val penaltyButtonBackground: Int = R.drawable.bg_penalty_button_yellow,
    @DrawableRes
    override val resultBackground: Int = R.drawable.bg_yellow,
    @DrawableRes
    override val roundButtonBackground: Int = R.drawable.bg_round_button_yellow,
    @ColorRes
    override val eventButtonTint: Int = R.color.black,
    @ColorRes
    override val resultsButtonTint: Int = R.color.black,
    @ColorRes
    override val iconTint: Int = R.color.gray
) : Theme