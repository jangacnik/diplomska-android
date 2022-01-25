package com.example.diplomskanaloga.activities.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.databinding.FragmentProfileBinding
import java.lang.StringBuilder
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var calendar: Calendar
    private lateinit var birthdayEditText: EditText
    private lateinit var datePickerDialog: DatePickerDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        calendar = Calendar.getInstance()
        birthdayEditText = root.findViewById(R.id.birthday_editText)
        birthdayEditText.setOnClickListener {
            Log.i("test","test")
            onDateEditTextClicked()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDateEditTextClicked() {
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 -> birthdayEditText.setText(StringBuilder().append(i3).append(".").append(i2+1).append(".").append(i))},mYear,mMonth,mDay)
        datePickerDialog.show()
    }
}