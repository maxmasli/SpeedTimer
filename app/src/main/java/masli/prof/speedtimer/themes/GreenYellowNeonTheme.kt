package masli.prof.speedtimer.themes

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import masli.prof.speedtimer.R

class GreenYellowNeonTheme (
    override val name: String = "Green-Yellow Neon",
    @DrawableRes
    override val background: Int = R.drawable.bg_darkgray_black,
    @ColorRes
    override val textColor: Int = R.color.white,
    @ColorRes
    override val textColorOnMainColor: Int = R.color.black,
    @DrawableRes
    override val penaltyButtonBackground: Int = R.drawable.bg_penalty_button_neon_green_yellow,
    @DrawableRes
    override val itemBackground: Int = R.drawable.bg_rounded_neon_green_yellow,
    @DrawableRes
    override val roundButtonBackground: Int = R.drawable.bg_round_button_neon_green_yellow,
    @ColorRes
    override val eventButtonTint: Int = R.color.white,
    @ColorRes
    override val resultsButtonTint: Int = R.color.white,
    @ColorRes
    override val iconTint: Int = R.color.white
) : Theme