package com.example.mvrx

import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.MavericksView

open class BaseMavericksActivity : AppCompatActivity(), MavericksView {
    override fun invalidate() = Unit
}
