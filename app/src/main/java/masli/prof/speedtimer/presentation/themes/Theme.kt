package masli.prof.speedtimer.presentation.themes

interface Theme {
    val name: String
    val background: Int // background
    val textColor: Int // text color
    val textColorOnMainColor: Int // text color on itemview
    val penaltyButtonBackground: Int // bg on dnf and +2
    val resultBackground: Int // bg on item result
    val roundButtonBackground: Int // bg on delete and description buttons
    val eventButtonTint: Int // color of event image
    val resultsButtonTint: Int // color of "move to results" image
    val iconTint: Int // color of icons without bg
}