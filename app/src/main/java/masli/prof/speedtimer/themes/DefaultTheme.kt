package masli.prof.speedtimer.themes

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import masli.prof.speedtimer.R

class DefaultTheme(
    override val name: String = "Default",
    @DrawableRes
    override val background: Int = R.drawable.bg_gradient_blue_to_purple,
    @ColorRes
    override val textColor: Int = R.color.black,
    @ColorRes
    override val textColorOnMainColor: Int = R.color.black,
    @DrawableRes
    override val penaltyButtonBackground: Int = R.drawable.bg_penalty_button_gray,
    @DrawableRes
    override val resultBackground: Int = R.drawable.bg_rounded_light_gray,
    @DrawableRes
    override val roundButtonBackground: Int = R.drawable.bg_round_button_gray,
    @ColorRes
    override val eventButtonTint: Int = R.color.black,
    @ColorRes
    override val resultsButtonTint: Int = R.color.black,
    @ColorRes
    override val iconTint: Int = R.color.gray,
) : Theme