package com.eugeneprojects.funnysquares.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import com.eugeneprojects.funnysquares.R
import com.eugeneprojects.funnysquares.databinding.ActivityMainBinding
import com.eugeneprojects.funnysquares.model.Squares

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.squares.observe(this) {
            renderSquares(it)
        }

        binding.btGenerateColor.setOnClickListener { viewModel.generateSquares() }
    }

    private fun renderSquares(squares: Squares) = with(binding) {
        colorsContainer.removeAllViews()
        val identifiers = squares.colors.indices.map { View.generateViewId() }
        for (i in squares.colors.indices) {
            val row = i / squares.size
            val column = i % squares.size

            val view = View(this@MainActivity)
            view.setBackgroundColor(squares.colors[i])
            view.id = identifiers[i]

            val params = ConstraintLayout.LayoutParams(0,0)
            params.setMargins(resources.getDimensionPixelSize(R.dimen.space))
            view.layoutParams = params

            if (column == 0) params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            else params.startToEnd = identifiers[i - 1]

            if (column == squares.size - 1) params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            else params.endToStart = identifiers[i + 1]

            if (row == 0) params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            else params.topToBottom = identifiers[i - squares.size]

            if (row == squares.size - 1) params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            else params.bottomToTop = identifiers[i + squares.size]

            colorsContainer.addView(view)
        }
    }
}