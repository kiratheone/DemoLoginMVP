package com.parkingreservation.iuh.demologinmvp.util

class StringLengthHandler {
    companion object {
        private const val TEXT_DESCRIPTION_LENGTH = 20

        fun getText(text: String): String {
            var resText = text
            if (text.length > TEXT_DESCRIPTION_LENGTH) resText = text.substring(0, TEXT_DESCRIPTION_LENGTH) + "..."
            return resText
        }
    }

}