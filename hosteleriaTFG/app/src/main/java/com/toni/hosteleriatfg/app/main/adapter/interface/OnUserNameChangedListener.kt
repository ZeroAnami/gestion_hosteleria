package com.toni.hosteleriatfg.app.main.adapter.`interface`

import com.toni.hosteleriatfg.data.model.User

interface OnUserNameChangedListener {
    fun onUserNameChanged(position: Int, newUser: User)
}