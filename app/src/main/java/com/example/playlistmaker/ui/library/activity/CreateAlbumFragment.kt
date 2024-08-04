package com.example.playlistmaker.ui.library.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AlbumCreateBinding
import com.google.android.material.textfield.TextInputLayout

class CreateAlbumFragment : Fragment() {
    companion object {
        fun newInstance() = CreateAlbumFragment().apply {}
    }

    //private val viewModel: AlbumsListViewModel by viewModel()

    private lateinit var textWatcher:TextWatcher
    private lateinit var aboutTextWatcher:TextWatcher
    private lateinit var binding: AlbumCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumCreateBinding.inflate(inflater, container, false)
        textWatchersInit()
        binding.name.addTextChangedListener(textWatcher)
        binding.about.addTextChangedListener(aboutTextWatcher)
        binding.createButton.setOnClickListener {
            binding.name.setText("")
        }
        binding.arrowBack.setOnClickListener { findNavController().popBackStack() }
        return binding.root
    }


    private fun buttonStateCheck(s: CharSequence?) {
        binding.createButton.isEnabled = !s.isNullOrEmpty()
    }

    private fun checkState(border: TextInputLayout, s: CharSequence?,) {
        if (!s.isNullOrEmpty()) {
            border.defaultHintTextColor =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yp_blue
                    )
                )

            border.setBoxStrokeColorStateList(
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_hovered, android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_blue
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_text_grey
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_blue
                        )
                    )
                )
            )
        } else {
            border.defaultHintTextColor =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yp_text_grey
                    )
                )
            border.hintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.yp_blue
                )
            )
            border.setBoxStrokeColorStateList(
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_hovered, android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_text_grey
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_text_grey
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yp_blue
                        )
                    )
                )
            )

        }

    }
    private fun textWatchersInit() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                buttonStateCheck(s)
                checkState(binding.border, s)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        aboutTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkState(binding.borderAbout, s)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
    }

}