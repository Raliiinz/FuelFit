package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

object FuelFitTextField {
    @Composable
    fun Outlined(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        label: String? = null,
        placeholder: String? = null,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        errorMessage: String? = null,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        singleLine: Boolean = true,
        shape: Shape = MaterialTheme.shapes.large,
        colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            label = label?.let { { Text(it) } },
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            supportingText = {
                if (isError && errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            shape = shape,
            colors = colors,
            visualTransformation = visualTransformation
        )
    }

    @Composable
    fun Filled(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        placeholder: String,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        enabled: Boolean = true,
        shape: Shape = MaterialTheme.shapes.medium,
        containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            enabled = enabled,
            shape = shape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor
            )
        )
    }

    @Composable
    fun Password(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        label: String = "Password",
        isError: Boolean = false,
        errorMessage: String? = null
    ) {
        var visible by remember { mutableStateOf(false) }

        val visualTransformation = if (visible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }

        Outlined(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label,
            isError = isError,
            errorMessage = errorMessage,
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = if (visible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            },
            singleLine = true,
            visualTransformation = visualTransformation
        )
    }

    data class FuelFitOption(
        val id: String,
        val label: String
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Dropdown(
        options: List<FuelFitOption>,
        selected: FuelFitOption?,
        onSelected: (FuelFitOption) -> Unit,
        placeholder: String,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        ) {
            Outlined(
                value = selected?.label ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = placeholder,
                trailingIcon = {
                    Icon(Icons.Default.KeyboardArrowDown, null)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
