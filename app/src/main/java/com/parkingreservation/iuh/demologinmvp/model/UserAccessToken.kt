package com.parkingreservation.iuh.demologinmvp.model

/**
 * {
"access_token": "1dcf42d4-6cbf-49a2-b853-4feca05024bc",
"token_type": "bearer",
"expires_in": 2591999,
"scope": "read write"
}
 */
data class UserAccessToken(val access_token: String, val token_type: String, val expires_in: Int, val scope: String)