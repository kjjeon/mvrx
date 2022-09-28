package com.example.mvrx

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import kotlinx.coroutines.delay

class MainViewModel(
    state: TestState
) : BaseMavericksViewModel<TestState>(state) {
//
//    fun setStateTest() {
//        setState {
//            copy(test = !test)
//        }
//    }

    fun setStateTest2(count: Int) {
        setState {
            copy(test = TestData(true, "count : $count"))
        }
    }
//
//    fun abcd(aa: () -> Boolean) {
//    }
//
//    fun bbbb(): Boolean {
//
//        return false
//    }

    fun setStateAsync() = withState { state ->

//        setState { copy(asyncTest = Loading()) }
//        setState { copy(asyncTest = Success(true)) }
//
//        if (state.asyncTest is Loading) {
//            println("Loading")
//            return@withState
//        }

//        ::getDataUseCase.execute { copy(asyncTest = it) }
        // or
        suspend { getDataUseCase() }.execute { copy(asyncTest = it) }
    }
//
//    fun getTest() {
//        withState {
//            setState {
//                println("1111")
//                Thread.sleep(3_000)
//                println("22222 ")
//                copy(test = !test)
//            }
//        }
//
//        withState { state: TestState ->
//            println("test = ${state.test}")
//        }
//
//    }

    suspend fun test(): TestData {
        println("suspend gogogogogo")
        delay(1000)
        println("test end ")
        return TestData(true, "aaaa")
    }

    fun test2() {
        suspend {
            test()
        }.execute { async ->
            println("execute = ${async()?.str}")
            if (async is Success)
                this.copy(test = async())
            else
                this
        }

        println("test2 end")
    }

    fun getState() = withState { state ->
        if (state.asyncTest is Loading) {
            println("Loading state")
        } else if (state.asyncTest is Success) {
            println("Success state")
        }
    }

    suspend fun getDataUseCase(): Boolean {
        return true
    }
}

data class TestState(
    val test: TestData = TestData(false, "test"),
    val asyncTest: Async<Boolean> = Uninitialized
) : MavericksState {
    val test2: TestData = test // 항상 새로 생성되니깐 이건 같이 바뀌어요 그리고 외부에서 바꾸지 못하도록 하려고 ..
}

data class TestData(val bool: Boolean, val str: String)
