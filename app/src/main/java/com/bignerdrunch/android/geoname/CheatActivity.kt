package com.bignerdrunch.android.geoname

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.bignerdrunch.android.geoname.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showAnswerButton.setOnClickListener {
            val answerText = intent.getBooleanExtra(CORRECT_ANSWER)
            binding.answerTextView.setText(answerText)
        }
    }

    data class Output(
        val answer: Boolean,
        val wasChecked: Boolean
    )

    class Contract: ActivityResultContract<Boolean,Output>(){

        override fun createIntent(context: Context, input: Boolean) = Intent(context,CheatActivity::class.java).apply {
            putExtra(CORRECT_ANSWER,input)
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Output {

        }
    }

    companion object{
        private const val CORRECT_ANSWER = "SHOW_ANSWER_CODE"
    }
}

