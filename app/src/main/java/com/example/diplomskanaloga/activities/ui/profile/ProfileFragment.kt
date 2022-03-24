package com.example.diplomskanaloga.activities.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.databinding.FragmentProfileBinding
import com.example.diplomskanaloga.interfaces.VolleyResponse
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.services.EmployeeRestService
import com.example.diplomskanaloga.services.WorkHoursRestService
import com.google.gson.Gson
import java.util.*
import kotlin.collections.HashMap

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var calendar: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    lateinit var employeeRestService: EmployeeRestService
    lateinit var workHoursRestService: WorkHoursRestService
    lateinit var userData: Employee
    val gson = Gson()
    private var status = ""
    private var isWorking = false
    private var isOnBreak = false

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var zipEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var statuTextView: TextView
    private lateinit var roleTextview: TextView
    private lateinit var editPersonalDataButton: Button

    private lateinit var workBtn: Button
    private lateinit var breakBtn: Button

    private var editingData: Boolean = false
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
        workHoursRestService = WorkHoursRestService()
        getUserData(root)
        initUserActivity(root)

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
        nameEditText = root.findViewById(R.id.name_editText)
        surnameEditText = root.findViewById(R.id.lastname_editText)
        streetEditText = root.findViewById(R.id.street_editText)
        zipEditText = root.findViewById(R.id.zip_editText)
        cityEditText = root.findViewById(R.id.city_editText)
        roleTextview = root.findViewById(R.id.textViewRole)
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

    fun initUserActivity(root: View) {
        statuTextView = root.findViewById(R.id.status_textView)
        editPersonalDataButton = root.findViewById(R.id.edit_personal_info_button)
        workBtn = root.findViewById(R.id.button_record_day)
        breakBtn = root.findViewById(R.id.button_record_break)
        editPersonalDataButton.setOnClickListener {
            onEditPersonalData()
        }
        getWorkStatus()
        workBtn.setOnClickListener {
            onWorkButtonClick()
        }
        breakBtn.setOnClickListener {
            onBreakButtonClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onEditPersonalData() {
        if(editingData) {
            editingData = false
            nameEditText.isEnabled = false
            surnameEditText.isEnabled = false
            streetEditText.isEnabled = false
            zipEditText.isEnabled = false
            cityEditText.isEnabled = false
            editPersonalDataButton.setText("Edit")
            var changes: HashMap<String, String> = HashMap()
            changes.put("id",userData.uuid.toString())
            changes.put("name",nameEditText.text.toString())
            changes.put("surname",surnameEditText.text.toString())
            changes.put("street",streetEditText.text.toString())
            changes.put("city",cityEditText.text.toString())
            changes.put("postalCode",zipEditText.text.toString())
            employeeRestService.updateUserData(changes, this.requireContext(),object : VolleyResponse {
                override fun onSuccess(response: Any?) {
                    userData = gson.fromJson(response.toString(), Employee::class.java)
                    nameEditText.setText(userData.name)
                    surnameEditText.setText(userData.surname)
                    streetEditText.setText(userData.address.street)
                    zipEditText.setText(userData.address.postalCode)
                    cityEditText.setText(userData.address.city)
                }
                override fun onError(error: VolleyError?) {
                    Toast.makeText(requireContext(), "Update was unsuccessful", Toast.LENGTH_LONG).show()
                }})
        }else {
            editPersonalDataButton.setText("Save")
            editingData = true
            nameEditText.isEnabled = true
            surnameEditText.isEnabled = true
            streetEditText.isEnabled = true
            zipEditText.isEnabled = true
            cityEditText.isEnabled = true
        }
    }

    fun getWorkStatus() {
        workHoursRestService.getWorkStatus(this.requireContext(), object : VolleyResponse {
            override fun onSuccess(response: Any?) {
                status = response.toString()
                statuTextView.setText(status)
                when(status) {
                    "On break" -> {
                        breakBtn.isEnabled = true
                        workBtn.setText(getString(R.string.stop_work))
                        breakBtn.setText(getText(R.string.stop_break))}
                    "Working" -> {
                        breakBtn.isEnabled = true
                        workBtn.setText(getString(R.string.stop_work))
                        breakBtn.setText(getText(R.string.start_break))}
                    else -> {
                        breakBtn.isEnabled = false
                        workBtn.setText(getString(R.string.start_work))
                        breakBtn.setText(getText(R.string.start_break))
                    }
                }

            }

            override fun onError(error: VolleyError?) {
                if (error != null) {
                    Log.e("Response Error", error.networkResponse.toString())
                }
            }
        })
    }

    fun onWorkButtonClick() {
        when(status) {

            "On break" -> {
                workHoursRestService.stopWorkday(userData.uuid.toString(), this.requireContext(), object : VolleyResponse {
                    override fun onSuccess(response: Any?) {

                        getWorkStatus()
                        }

                    override fun onError(error: VolleyError?) {

                    }
                })
            }

            "Working" -> {
                workHoursRestService.stopWorkday(userData.uuid.toString(), this.requireContext(), object : VolleyResponse {
                    override fun onSuccess(response: Any?) {

                        getWorkStatus()
                    }

                    override fun onError(error: VolleyError?) {

                    }
                })
            }
            else -> {
                workHoursRestService.startWorkday(userData.uuid.toString(), this.requireContext(), object : VolleyResponse {
                    override fun onSuccess(response: Any?) {

                        getWorkStatus()
                    }

                    override fun onError(error: VolleyError?) {

                    }
                })
            }

        }
    }

    fun onBreakButtonClick() {
        when(status) {

            "On break" -> {
                workHoursRestService.stopBreak(userData.uuid.toString(), this.requireContext(), object : VolleyResponse {
                    override fun onSuccess(response: Any?) {

                        getWorkStatus()
                    }

                    override fun onError(error: VolleyError?) {

                    }
                })
            }

            "Working" -> {
                workHoursRestService.startBreak(userData.uuid.toString(), this.requireContext(), object : VolleyResponse {
                    override fun onSuccess(response: Any?) {
                        getWorkStatus()
                    }

                    override fun onError(error: VolleyError?) {

                    }
                })
            }
            else -> {
                //nothing
            }

        }
    }
}