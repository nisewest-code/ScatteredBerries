package com.scatt.eredbe.rries.activity

import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.scatt.eredbe.rries.databinding.ActivityScarretedGameBoardBinding
import com.scatt.eredbe.rries.rv.AdapterRv
import com.scatt.eredbe.rries.utils.GameUtil


class ScarretedGameBoardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityScarretedGameBoardBinding
    private var currentImage = 0
    private var currentList = mutableListOf<Int>()
    private lateinit var adapter: AdapterRv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScarretedGameBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = AdapterRv(this::callback)
        binding.rv.adapter = adapter
        binding.back.setOnClickListener(this)
        startGame()
    }

    private fun startGame(){
        currentImage = GameUtil.randomId()
        currentList = GameUtil.randomList().toMutableList()
        while (!currentList.contains(currentImage)){
            currentList = GameUtil.randomList().toMutableList()
        }

        updateUI()
    }

    private fun callback(item: Int, pos: Int){
        if (item == currentImage){
            currentList.removeAt(pos)
            if (!currentList.contains(currentImage)){
                startGame()
            } else {
                updateListUI(pos)
            }
        } else {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Not Correct item") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(
                    R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    private fun updateListUI(pos: Int){
        adapter.updateItem(pos)
    }

    private fun updateUI(){
        binding.currentImage.setImageResource(currentImage)
        adapter.updateList(currentList)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.back -> finish()
            else -> {}
        }
    }
}