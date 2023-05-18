package com.scatt.eredbe.rries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.scatt.eredbe.rries.activity.ScerretedInfoBoard
import com.scatt.eredbe.rries.databinding.ActivityScatteredBoardBinding

class ScatteredBoard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityScatteredBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScatteredBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener(this)
        binding.exit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.start -> startActivity(Intent(this, ScerretedInfoBoard::class.java))
            binding.exit -> finish()
        }
    }
}