package com.example.mvrx

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

open class BaseMavericksViewModel<S : MavericksState>(
    initialState: S
) : MavericksViewModel<S>(initialState)
