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
import com.android.volley.VolleyError
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.databinding.FragmentProfileBinding
import com.example.diplomskanaloga.interfaces.VolleyResponse
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.services.EmployeeRestService
import com.google.gson.Gson
import java.lang.StringBuilder
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var calendar: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    lateinit var employeeRestService: EmployeeRestService
    lateinit var userData: Employee
    val gson = Gson()


    private lateinit var birthdayEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var zipEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var statuTextView: TextView
    private lateinit var roleTextview: TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        calendar = Calendar.getInstance()

        employeeRestService = EmployeeRestService()
        getUserData(root)
        return root
    }

    fun getUserData(root: View) {
                    employeeRestService.getUserData(this.requireContext(), object : VolleyResponse {
                override fun onSuccess(response: Any?) {
                    userData = gson.fromJson(response.toString(), Employee::class.java)
                    Log.w("Response", userData.toString())
                    initUserData(root)
                }

                override fun onError(error: VolleyError?) {
                    if (error != null) {
                        Log.e("Response Error", error.networkResponse.toString())
                    }
                }
            })
    }

    fun initUserData(root: View) {
        birthdayEditText = root.findViewById(R.id.birthday_editText) // not in db
        nameEditText = root.findViewById(R.id.name_editText)
        surnameEditText = root.findViewById(R.id.lastname_editText)
        streetEditText = root.findViewById(R.id.street_editText)
        zipEditText = root.findViewById(R.id.zip_editText)
        cityEditText = root.findViewById(R.id.city_editText)
        roleTextview = root.findViewById(R.id.textViewRole)
        birthdayEditText.setOnClickListener {
            onDateEditTextClicked()
        }
        nameEditText.setText(userData.name)
        surnameEditText.setText(userData.surname)
        streetEditText.setText(userData.address.street)
        zipEditText.setText(userData.address.postalCode)
        cityEditText.setText(userData.address.city)
        var roles: String = ""
        userData.roles?.forEach { r -> roles+=r.authority+", " }
        roles = roles.dropLast(2)
        roleTextview.setText(roles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDateEditTextClicked() {
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { _, i, i2, i3 -> birthdayEditText.setText(StringBuilder().append(i3).append(".").append(i2+1).append(".").append(i))},mYear,mMonth,mDay)
        datePickerDialog.show()
    }
}