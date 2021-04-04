package com.example.quiz

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.example.quiz.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: QuizViewModel by viewModels()
    private var cheatButtonEnabled by Delegates.notNull<Boolean>()
    private var counter: Int by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        counter = savedInstanceState?.getInt("cheatCounter") ?: 0
        cheatButtonEnabled = savedInstanceState?.getBoolean("isCheatButtonEnabled") ?: true

        updateQuestion()

        binding.btCheat.isEnabled = cheatButtonEnabled

        binding.btTrue.setOnClickListener {
            checkAnswer(true)
            buttonsState(false)
        }
        binding.btFalse.setOnClickListener {
            checkAnswer(false)
            buttonsState(false)
        }

        binding.btNext.setOnClickListener {
            nextQuestion()
            updateQuestion()
            buttonsState(true)
        }

        binding.btPrevious.setOnClickListener {
            previousQuestion()
            updateQuestion()
            buttonsState(true)
        }

        binding.btCheat.setOnClickListener {
            val answer = viewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answer, counter)
            val options =
                ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
        }

    }

    private fun disableCheatButtonOnCounter() {
        if (counter >= 3) {
            cheatButtonEnabled = false
            binding.btCheat.isEnabled = cheatButtonEnabled
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("cheatCounter", counter)
        outState.putBoolean("isCheatButtonEnabled", cheatButtonEnabled)
    }


    private fun buttonsState(on: Boolean) {
        binding.btFalse.isEnabled = on
        binding.btTrue.isEnabled = on
    }

    private fun showToast(@StringRes message: Int) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun updateQuestion() {
        val questionID = viewModel.currentQuestionText
        binding.tvQuestion.text = resources.getString(questionID)
    }

    private fun nextQuestion() {
        viewModel.moveToNext()
    }

    private fun previousQuestion() {
        viewModel.moveToPrevious()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val answer = viewModel.currentQuestionAnswer

        Log.d("IsCheater", "MainActivity - ${viewModel.isCheater}")

        val messageID = if (userAnswer == answer) {
            if (viewModel.isCheater)
                showToast(R.string.toast_judgment)
            viewModel.incrementRightAnswer()
            R.string.toast_correct
        } else {
            R.string.toast_incorrect
        }
        showToast(messageID)
        if (viewModel.getResult()) {
            val result = " You result ${viewModel.totalResult}%"
            binding.tvQuestion.text = result
            binding.btNext.isEnabled = false
            binding.btPrevious.isEnabled = false
        }
        viewModel.isCheater = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            viewModel.isCheater =
                data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
            Log.d("IsCheater", "onActivityResult - ${viewModel.isCheater}")
            counter = data?.getIntExtra(CheatActivity.CHEAT_COUNTER, 0) ?: 0
            Log.d("CheatCounter", "onActivityResult viewModel - ${counter}")
            disableCheatButtonOnCounter()
        }
    }

    companion object {
        private const val REQUEST_CODE_CHEAT = 0
    }
}