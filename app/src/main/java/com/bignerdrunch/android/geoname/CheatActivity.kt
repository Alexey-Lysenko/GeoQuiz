package com.bignerdrunch.android.geoname

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.bignerdrunch.android.geoname.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    // declare binding
    private lateinit var binding: ActivityCheatBinding

    private val resultIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initial binding
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showAnswerButton.setOnClickListener{
            val answerText = intent.getBooleanExtra(ANSWER,false)
            binding.answerTextView.text = answerText.toString()
            resultIntent.putExtra(WAS_CHECKED,true)
        }
    }

    override fun onBackPressed() {
        setResult(BACK_BUTTON_WAS_PRESSED,resultIntent)
        super.onBackPressed()
    }

    class Contract:ActivityResultContract<Boolean,Boolean>(){
        override fun createIntent(context: Context, input: Boolean?) = Intent(context,CheatActivity::class.java).apply {
            putExtra(CheatActivity.ANSWER,input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return intent?.getBooleanExtra(WAS_CHECKED,false) ?: false
        }
    }

    companion object{
        private const val ANSWER = "ANSWER"
        private const val WAS_CHECKED = "WAS_CHECKED"
        private const val BACK_BUTTON_WAS_PRESSED = 509
    }
}

