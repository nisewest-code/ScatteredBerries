package com.scatt.eredbe.rries.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.scatt.eredbe.rries.databinding.ActivityScerretedInfoBoardBinding

class ScerretedInfoBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityScerretedInfoBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScerretedInfoBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener(this)
        binding.back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.start -> startActivity(Intent(this, ScarretedGameBoardActivity::class.java))
            binding.back -> finish()
        }
    }
}