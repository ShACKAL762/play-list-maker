package com.example.playlistmaker.ui.library.activity

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AlbumCreateBinding
import com.example.playlistmaker.ui.library.view_models.CreateAlbumViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

open class CreateAlbumFragment : Fragment() {
    companion object {
        const val ALBUM_ID = "albumId"
        fun newInstance() = CreateAlbumFragment().apply {}
    }

    private val viewModel: CreateAlbumViewModel by viewModel()

    private lateinit var textWatcher: TextWatcher
    private lateinit var aboutTextWatcher: TextWatcher
    private lateinit var binding: AlbumCreateBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textWatchersInit()
        observeInit()
        binding.name.addTextChangedListener(textWatcher)
        binding.aboutCreate.addTextChangedListener(aboutTextWatcher)

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.image.setImageURI(uri)
                viewModel.uriCash(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }

        }

        binding.image.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.createButton.setOnClickListener {
            viewModel.createAlbum()
            Toast.makeText(
                requireContext(),
                "${requireContext().getString(R.string.playlist)} ${binding.name.text} ${
                    requireContext().getString(R.string.created)
                }.",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
        binding.arrowBack.setOnClickListener {
            if (!viewModel.isEmpty()) {
                dialog()
            } else
                findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!viewModel.isEmpty()) {
                        dialog()
                    } else
                        findNavController().popBackStack()
                }
            })
        checkInput()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun checkInput() {
        if (requireArguments().size() > 0) {
            updateBinding()
            binding.image.background = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.placeholder
            )
        } else
            binding.image.background = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.album_create_placeholder
            )
    }

    private fun updateBinding() {
        binding.title.text = requireContext().getString(R.string.edit)
        binding.createButton.text = requireContext().getString(R.string.save)
        requireArguments().getString(ALBUM_ID)?.toInt()?.let { viewModel.getAlbum(it) }
        binding.arrowBack.setOnClickListener { findNavController().popBackStack() }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private fun observeInit() {
        viewModel.albumLiveData.observe(viewLifecycleOwner) { album ->
            binding.name.setText(album.name)
            binding.aboutCreate.setText(album.about)
            binding.createButton.setOnClickListener {
                viewModel.updateAlbum(album)
                findNavController().popBackStack()
            }
            binding.image.setImageURI(Uri.parse(album.imageSrc))
            viewModel.uriCash(Uri.parse(album.imageSrc))

        }
    }

    private fun buttonStateCheck(s: CharSequence?) {
        binding.createButton.isEnabled = !s.isNullOrEmpty()
    }

    private fun checkState(border: TextInputLayout, s: CharSequence?) {
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
                viewModel.setAlbumName(s)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        aboutTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkState(binding.borderAbout, s)
                viewModel.setAlbumAbout(s)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
    }

    fun dialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertText)
            .setTitle(requireContext().getString(R.string.end_of_create))
            .setMessage(requireContext().getString(R.string.unsaved_data_lost))
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ ->
            }
            .setPositiveButton(requireContext().getString(R.string.complete)) { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

}