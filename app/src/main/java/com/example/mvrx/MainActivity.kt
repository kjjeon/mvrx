package com.example.mvrx

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import com.airbnb.mvrx.viewModel
import com.example.mvrx.databinding.ActivityMainBinding

class MainActivity : BaseMavericksActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserve()

        binding.button.doOnClick {
            viewModel.setStateAsync()
//            viewModel.test2()
        }
        binding.button2.doOnClick {
            viewModel.setStateTest2(count)
        }
    }

    fun initObserve() {
//        viewModel.onEach(
//            TestState::test,
//            TestState::asyncTest,
//            TestState::test2,
//            uniqueOnly("searchAsync"), // stop -> start 상태가 될때 또 호출 안되도록 하는 것
//        ) { a, b, c ->
//            println("onEach $a, ${b()}, $c")
//        }
//
//        viewModel.onEach(
//            TestState::test,
//            TestState::asyncTest,
//            TestState::test2,
//        ) { a, b, c ->
//            println("onEach2 $a, ${b()}, $c")
//        }

        viewModel.onEach(
            TestState::asyncTest,
        ) { a ->
            println("onEach async $a")
        }

        viewModel.onAsync(
            TestState::asyncTest,
            onFail = { throwable -> println("$throwable") },
        ) {
            println("$it")
        }
//
//        viewModel.onAsync(TestState::asyncTest,
//            onFail = { throwable -> println("$throwable") },
//            onSuccess = { println("onSuccess") }
//        )
    }

    inline fun View.doOnClick(throttle: Long = 300L, crossinline onClicked: (View) -> Unit) {
        setOnClickListener(object : View.OnClickListener {
            private var lastClickedAt: Long? = null

            override fun onClick(v: View?) {
                val prevClickedAt = lastClickedAt
                val now = SystemClock.uptimeMillis()
                if (prevClickedAt == null || now - prevClickedAt > throttle) {
                    onClicked(this@doOnClick)
                    lastClickedAt = now
                }
            }
        })
    }

    data class Order(
        private val list: List<String>
    )
}
