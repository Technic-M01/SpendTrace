package com.technic.spendtrace.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technic.spendtrace.MainActivity
import com.technic.spendtrace.R
import com.technic.spendtrace.database.PurchaseViewModel
import com.technic.spendtrace.databinding.FragmentDashboardBinding
import com.technic.spendtrace.utils.Common
import com.technic.spendtrace.utils.DateFormats

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PurchaseViewModel
        get() = (requireActivity() as MainActivity).pViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView(root)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecyclerView(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.purchasesRecyclerView)
        val adapter = PurchaseListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        with (viewModel) {
            allPurchases.observe(viewLifecycleOwner) { purchases ->
//                purchases?.let { adapter.submitList(it) }
            }

            currentMonthPurchases.observe(viewLifecycleOwner) { purchases ->
                purchases?.let { adapter.submitList(it) }
            }
        }

        viewModel.populate()

    }

}