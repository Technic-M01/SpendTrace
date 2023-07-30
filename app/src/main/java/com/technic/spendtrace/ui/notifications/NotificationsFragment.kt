package com.technic.spendtrace.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.technic.spendtrace.MainActivity
import com.technic.spendtrace.database.PurchaseViewModel
import com.technic.spendtrace.databinding.FragmentNotificationsBinding
import com.technic.spendtrace.utils.Common
import com.technic.spendtrace.utils.DateFormats
import kotlinx.coroutines.launch

data class HistoricalMonths(val month: Int, val year: Int)

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PurchaseViewModel
        get() = (requireActivity() as MainActivity).pViewModel

    private val entryHistoryList: MutableList<HistoricalMonths> = emptyList<HistoricalMonths>().toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//            getHistoricalMonths()

//        viewModel.dateStamps.observe(viewLifecycleOwner) { stamps ->
//            Log.i("HistoricalMonths", "$stamps")
//        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getHistoricalMonths() {
        var stopChecking: Boolean = false

        var currentMonth = Common.getCurrentDate()
        var previousMonth = Common.determinePreviousMonth()

        do {
            Log.i("HistoricalMonths", "cMonth $currentMonth | pMonth: $previousMonth")

            viewModel.getHistoricalData(previousMonth, currentMonth).observe(viewLifecycleOwner) { itemList ->
                Log.i("HistoricalMonths", "item list: $itemList")

//                stopChecking = itemList.isEmpty()
                stopChecking = true

                if (itemList.isNotEmpty()) {
                    val date = itemList[0].date
//                    val month = date.substringAfter("-").substringBeforeLast("-").toInt()
                    val month = Common.extractMonth(date).toInt()
                    val year = date.substringAfterLast("-").toInt()

                    val entry = HistoricalMonths(month, year)
                    Log.d("HistoricalMonths", "adding entry $entry")

                    entryHistoryList.add(entry)
                }
            }

            currentMonth = previousMonth
            previousMonth = Common.determinePreviousMonth(Common.extractMonth(currentMonth))

        } while (!stopChecking)
        Log.d("HistoricalMonths", "checking stopped")
    }

}