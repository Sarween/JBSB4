package com.example.jbsb4.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.jbsb4.R
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonthYearPickerDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonthYearPickerDialog(private val listener: DatePickerDialog.OnDateSetListener) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        return DatePickerDialog(requireActivity(), listener, year, month, 0)
    }

    override fun onStart() {
        super.onStart()
        // Customize the DatePicker to show only month and year
        (dialog as DatePickerDialog).datePicker.apply {
            calendarViewShown = false
            spinnersShown = true
            descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
        }
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_month_year_picker_dialog, container, false)
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MonthYearPickerDialog.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MonthYearPickerDialog().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}
//class MonthYearPickerDialog : DialogFragment() {
//    private var listener: OnDateSetListener? = null
//    fun setListener(listener: OnDateSetListener?) {
//        this.listener = listener
//    }
//
//    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder: AlertDialog.Builder = Builder(getActivity())
//        // Get the layout inflater
//        val inflater: LayoutInflater = getActivity().getLayoutInflater()
//        val cal: Calendar = Calendar.getInstance()
//        val dialog: View = inflater.inflate(R.layout.date_picker_dialog, null)
//        val monthPicker = dialog.findViewById<View>(R.id.picker_month) as NumberPicker
//        val yearPicker = dialog.findViewById<View>(R.id.picker_year) as NumberPicker
//        monthPicker.minValue = 0
//        monthPicker.maxValue = 11
//        monthPicker.value = cal.get(Calendar.MONTH)
//        val year: Int = cal.get(Calendar.YEAR)
//        yearPicker.minValue = year
//        yearPicker.maxValue = Companion.MAX_YEAR
//        yearPicker.value = year
//        builder.setView(dialog) // Add action buttons
//            .setPositiveButton(R.string.ok,
//                DialogInterface.OnClickListener { dialog, id ->
//                    listener!!.onDateSet(
//                        null,
//                        yearPicker.value,
//                        monthPicker.value,
//                        0
//                    )
//                })
//            .setNegativeButton(R.string.cancel,
//                DialogInterface.OnClickListener { dialog, id ->
//                    this@MonthYearPickerDialog.getDialog().cancel()
//                })
//        return builder.create()
//    }
//
//    companion object {
//        private const val MAX_YEAR = 2099
//    }
//}