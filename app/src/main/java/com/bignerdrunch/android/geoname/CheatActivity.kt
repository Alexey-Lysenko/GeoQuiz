package com.bignerdrunch.android.geoname

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.ViewModelProvider
import com.bignerdrunch.android.geoname.databinding.ActivityCheatBinding

private const val INSTANCE = "INSTANCE"
class CheatActivity : AppCompatActivity() {

    // declare binding
    private lateinit var binding: ActivityCheatBinding


    private val cheatActivityViewModel:CheatActivityViewModel by lazy {
        ViewModelProvider(this).get(CheatActivityViewModel::class.java)
    }

    private val resultIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initial binding
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (cheatActivityViewModel.showAnswerButtonWasPressed) callOnClick()

        binding.showAnswerButton.setOnClickListener{
            cheatActivityViewModel.showAnswerButtonWasPressed = true
            callOnClick()
        }
    }

    private fun disablingButton(){
        binding.showAnswerButton.isEnabled = false
    }

    override fun onBackPressed() {
        setResult(BACK_BUTTON_WAS_PRESSED,resultIntent)
        super.onBackPressed()
    }

    fun callOnClick(){
        binding.answerTextView.text = intent.getBooleanExtra(ANSWER, false).toString()
        resultIntent.putExtra(WAS_CHECKED, true)
        disablingButton()
    }

    class Contract:ActivityResultContract<Boolean,Boolean>(){
        override fun createIntent(context: Context, input: Boolean?) = Intent(context,CheatActivity::class.java).apply {
            putExtra(ANSWER,input)
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

