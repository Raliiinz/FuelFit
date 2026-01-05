package com.example.fuelfit.designsystem.components

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

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
    fun DisplayMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current,
        textAlign: TextAlign? = null
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier,
        color = color,
        textAlign = textAlign
    )

    @Composable
    fun DisplaySmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current,
        textAlign: TextAlign? = null
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier,
        color = color,
        textAlign = textAlign
    )

    @Composable
    fun HeadlineLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier,
        color = color
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
    fun HeadlineSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.headlineSmall,
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
    fun LabelMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier,
        color = color
    )

    @Composable
    fun LabelSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = LocalContentColor.current
    ) = FuelFitBaseText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier,
        color = color
    )
}

@Composable
private fun FuelFitBaseText(
    text: AnnotatedString,
    style: androidx.compose.ui.text.TextStyle,
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
