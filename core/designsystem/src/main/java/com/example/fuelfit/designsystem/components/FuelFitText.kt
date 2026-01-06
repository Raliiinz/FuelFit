package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

object FuelFitText {

    @Composable
    fun DisplayLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current,
        textAlign: TextAlign? = null
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier,
        color = color,
        textAlign = textAlign
    )

    @Composable
    fun HeadlineMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier,
        color = color
    )

    @Composable
    fun TitleLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        color = color
    )

    @Composable
    fun TitleMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        color = color
    )

    @Composable
    fun TitleSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier,
        color = color
    )

    @Composable
    fun BodyLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        color = color
    )

    @Composable
    fun BodyMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        color = color
    )

    @Composable
    fun BodySmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier,
        color = color
    )

    @Composable
    fun LabelLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier,
        color = color
    )

    @Composable
    fun EditableUnderline(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.primary,
        fontSize: Int = 18,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = color,
                fontSize = fontSize.sp,
                textDecoration = TextDecoration.Underline
            ),
            modifier = modifier
                .clickable { onClick() },
            textAlign = textAlign
        )
    }
}

@Composable
private fun FuelFitBaseText(
    text: AnnotatedString,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}
