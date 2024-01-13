package com.meli.melichallenge.presentation.base

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.meli.melichallenge.R
import com.meli.melichallenge.util.BindingString
import com.meli.melichallenge.util.hideKeyboard

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflate: LayoutInflater) -> VB,
) : Fragment() {

    protected val binding: VB by lazy {
        checkNotNull(_binding) { "Binding cannot be accessed before onCreateView() or after onDestroyView()" }
    }

    private var _binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun handleError(errorBindingString: BindingString) {
        try {
            errorBindingString.getString(requireContext())?.let {
                view?.let { view ->
                    val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(
                        requireContext().getColor(R.color.color_error),
                    )
                    snackbar.setTextColor(
                        requireContext().getColor(R.color.color_error),
                    )
                    snackbar.show()
                }
                hideKeyboard()
            }
        } catch (ex: Exception) {
            handleErrorWithToast(errorBindingString)
        }
    }

    protected fun handleErrorWithToast(errorBindingString: BindingString) {
        errorBindingString.getString(requireContext())?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            hideKeyboard()
        }
    }

}
