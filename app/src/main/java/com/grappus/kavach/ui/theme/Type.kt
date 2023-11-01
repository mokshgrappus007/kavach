package com.grappus.kavach.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.grappus.kavach.R

// Set of Material typography styles to start with

val InterFont = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
)

val BruleFont = FontFamily(
    Font(R.font.brule_medium, FontWeight.Medium),
    Font(R.font.brule_semibold, FontWeight.SemiBold),
)

val LalezarFont = FontFamily(
    Font(R.font.lalezar_regular)
)

val LuckiestGuyFont = FontFamily(
    Font(R.font.luckiestguy_regular)
)

val NeueHaasUnicaFont = FontFamily(
    Font(R.font.neuehaasunicapro_regular),
    Font(R.font.neuehaasunicapro_bold, FontWeight.Bold),
    Font(R.font.neuehaasunicapro_light, FontWeight.Light),
)

val QuickSandFont = FontFamily(
    Font(R.font.quicksand_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = BruleFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = BruleFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 18.sp,
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)