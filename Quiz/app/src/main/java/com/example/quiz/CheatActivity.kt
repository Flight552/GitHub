package com.example.quiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import com.example.quiz.databinding.ActivityCheatBinding
import kotlin.properties.Delegates

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue: Boolean = false
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.isCheater = savedInstanceState?.getBoolean(SAVED_CHEAT_STATE, false) ?: false
        viewModel.cheatCounter = savedInstanceState?.getInt(CHEAT_COUNTER_SAVE_INSTANCE, viewModel.cheatCounter) ?: 0

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        viewModel.cheatCounter = intent.getIntExtra(CHEAT_COUNTER, 0)

//        Log.d("CheatCounter", "onCreate viewModelCheatCounter- ${viewModel.cheatCounter}")
        Log.d("CheatCounter", "onCreate newCheatCounter- ${viewModel.cheatCounter}")

        setAnswerShownResult()

        binding.btShowAnswer.setOnClickListener {
            viewModel.cheatCounter++
            Log.d("CheatCounter", "Increment- ${viewModel.cheatCounter}")
            setAnswer()
            viewModel.isCheater = true
            setAnswerShownResult()
        }
        getVersionCodes()
    }


    private fun getVersionCodes() {
        val versionCode = Build.VERSION.SDK_INT
        val versionRelease = Build.VERSION.RELEASE
        val text = "$versionRelease API - $versionCode"
        binding.tvAPI.text = text
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_CHEAT_STATE, viewModel.isCheater)
        outState.putInt(CHEAT_COUNTER_SAVE_INSTANCE, viewModel.cheatCounter)
        Log.d("IsCheater", "onSaveInstanceState - ${viewModel.isCheater}")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("IsCheater", "on Destroy - ${viewModel.isCheater}")
    }

    private fun setAnswer() {
        val answerText = when {
            answerIsTrue -> R.string.bt_true
            else -> R.string.bt_false
        }
        binding.tvAnswer.text = getString(answerText)
    }

    private fun setAnswerShownResult() {
        Log.d("IsCheater", "on setAnswerShownResult - ${viewModel.isCheater}")
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, viewModel.isCheater)
            putExtra(CHEAT_COUNTER, viewModel.cheatCounter)
        }
        Log.d("CheatCounter", "setAnswerShownResult newCounter - ${viewModel.cheatCounter}")

        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.example.quiz.answer_is_true"
        const val SAVED_CHEAT_STATE = "saved_state"
        const val EXTRA_ANSWER_SHOWN = "com.example.quiz.cheat"
        const val CHEAT_COUNTER = "cheatCounter"
        const val CHEAT_COUNTER_SAVE_INSTANCE = "cheatSaveCounter"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, counter: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java)
                .putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                .putExtra(CHEAT_COUNTER, counter)
        }
    }
}