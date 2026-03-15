
package com.ichi2.anki.notetype

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.ichi2.anki.R
import com.ichi2.utils.getInputField
import com.ichi2.utils.getInputTextLayout
import com.ichi2.utils.input
import com.ichi2.utils.negativeButton
import com.ichi2.utils.positiveButton
import com.ichi2.utils.show
import com.ichi2.utils.title

class AddCardTypeDialog {
    companion object {
        fun showInstance(
            context: Context,
            prefill: String,
            existingNames: List<String>,
            block: (newName: String) -> Unit,
        ) {
            AlertDialog
                .Builder(context)
                .show {
                    title(R.string.add_card_type)
                    positiveButton(R.string.menu_add) { }
                    negativeButton(R.string.dialog_cancel)
                    setView(R.layout.dialog_generic_text_input)
                }.input(
                    hint = context.getString(R.string.card_type_name),
                    displayKeyboard = true,
                    allowEmpty = false,
                    prefill = prefill,
                    waitForPositiveButton = true,
                    callback = { dialog, result ->
                        block(result.toString())
                        dialog.dismiss()
                    },
                ).apply {
                    val field = getInputField()
                    val layout = getInputTextLayout()
                    field.doOnTextChanged { text, _, _, _ ->
                        val name = text.toString().trim()
                        val isDuplicate = existingNames.any { it.equals(name, ignoreCase = true) }
                        if (isDuplicate) {
                            layout.error = context.getString(R.string.card_type_name_used)
                            positiveButton.isEnabled = false
                        } else {
                            layout.error = null
                            positiveButton.isEnabled = name.isNotEmpty()
                        }
                    }
                }
        }
    }
}
