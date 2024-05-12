package com.anirudh.findfalcone.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anirudh.findfalcone.R
import com.anirudh.findfalcone.data.entity.InfoItem
import com.anirudh.findfalcone.data.entity.PlanetInfo
import com.anirudh.findfalcone.data.entity.VehicleInfo
import com.anirudh.findfalcone.databinding.FragmentFindBinding
import com.anirudh.findfalcone.view.adapters.CustomArrayAdapter
import com.anirudh.findfalcone.view.viewModel.FindFalconViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class FindFalconFragment : Fragment() {

    private lateinit var binding: FragmentFindBinding

    @Inject
    lateinit var findFalconViewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FindFalconViewModel
    private lateinit var destinationAdapter: CustomArrayAdapter
    private lateinit var vehiclesAdapter: CustomArrayAdapter
    private var destinationsInfo: ArrayList<InfoItem?> = arrayListOf()
    private var vehiclesInfo: ArrayList<InfoItem?> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel =
            activity?.viewModelStore?.let { ViewModelProvider(it, findFalconViewModelFactory) }
                ?.get(FindFalconViewModel::class.java) ?: ViewModelProvider(
                this, findFalconViewModelFactory
            )[FindFalconViewModel::class.java]
    }

    private fun setupAdapter() {
        destinationAdapter = CustomArrayAdapter(requireContext(), arrayListOf())
        vehiclesAdapter = CustomArrayAdapter(requireContext(), arrayListOf())
        binding.let {
            it.firstDestination. onItemSelectedListener = planetSelectedListener
            it.secondDestination. onItemSelectedListener = planetSelectedListener
            it.thirdDestination. onItemSelectedListener = planetSelectedListener
            it.fourthDestination. onItemSelectedListener = planetSelectedListener
            it.firstDestinationVehicle. onItemSelectedListener = vehicleSelectedListener
            it.secondDestinationVehicle. onItemSelectedListener = vehicleSelectedListener
            it.thirdDestinationVehicle. onItemSelectedListener = vehicleSelectedListener
            it.fourthDestinationVehicle. onItemSelectedListener = vehicleSelectedListener
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFindBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
        setupClickListeners()
        fetchData()
        setupData()
    }

    private fun setupData() {
        val res = resources
        binding.tvTimeTaken.text = java.lang.String.format(
            res.getString(R.string.totalTimeTaken), "0"
        )
    }


    private fun setupClickListeners() {
        binding.btnFind.setOnClickListener {
            val possible = validate()
            if (possible) {
                viewModel.findFalcone()
            }
        }
    }

    private fun fetchData() {
        viewModel.fetchData()
    }

    private var planetSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position > 0) {
                updatePlanetVehicleInfo()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

//    private var planetSelectedListener = object : AdapterView. onItemSelectedListener {
//        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//            if (position > 0) {
//                updatePlanetVehicleInfo()
//            }
//        }
//    }

    private var vehicleSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position > 0) {
                updatePlanetVehicleInfo()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

//    private var vehicleSelectedListener =
//        AdapterView. onItemSelectedListener { parent, view, position, id ->
//            if (position > 0) {
//                updatePlanetVehicleInfo()
//            }
//        }

    fun updatePlanetVehicleInfo() {
        val firstDestination = binding.firstDestination.selectedItem as? PlanetInfo ?: return
        val secondDestination = binding.secondDestination.selectedItem as? PlanetInfo ?: return
        val thirdDestination = binding.thirdDestination.selectedItem as? PlanetInfo ?: return
        val fourthDestination = binding.fourthDestination.selectedItem as? PlanetInfo ?: return
        val firstVehicleDestination = binding.firstDestinationVehicle.selectedItem as? VehicleInfo ?: return
        val secondVehicleDestination = binding.secondDestinationVehicle.selectedItem as? VehicleInfo ?: return
        val thirdVehicleDestination = binding.thirdDestinationVehicle.selectedItem as? VehicleInfo ?: return
        val fourthVehicleDestination = binding.fourthDestinationVehicle.selectedItem as? VehicleInfo ?: return
        vehiclesInfo.clear()
        vehiclesInfo.addAll(
            arrayListOf(
                firstVehicleDestination,
                secondVehicleDestination,
                thirdVehicleDestination,
                fourthVehicleDestination
            )
        )
        destinationsInfo.clear()
        destinationsInfo.addAll(
            arrayListOf(
                firstDestination,
                secondDestination,
                thirdDestination,
                fourthDestination
            )
        )
        if (destinationsInfo.isEmpty() || vehiclesInfo.isEmpty()) {
            return
        }
        viewModel.updateDestinationVehicleInfo(destinationsInfo, vehiclesInfo)
    }

    private fun updateDestinationAdapter(planets: List<InfoItem>) {
        destinationAdapter = CustomArrayAdapter(requireContext(), planets as ArrayList<InfoItem>)
        destinationAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        destinationAdapter.let {
            binding.firstDestination.adapter = it
            binding.firstDestination.setSelection(0)
            binding.secondDestination.adapter = it
            binding.secondDestination.setSelection(1)
            binding.thirdDestination.adapter = it
            binding.thirdDestination.setSelection(2)
            binding.fourthDestination.adapter = it
            binding.fourthDestination.setSelection(3)
        }
        destinationsInfo.clear()
        destinationsInfo.addAll(planets.take(4))
        destinationAdapter.notifyDataSetChanged()
    }

    private fun updateVehiclesAdapter(vehicles: List<InfoItem>) {
        vehiclesAdapter = CustomArrayAdapter(requireContext(), vehicles as ArrayList<InfoItem>)
        vehiclesAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        vehiclesAdapter.let {
            binding.firstDestinationVehicle.adapter = it
            binding.firstDestinationVehicle.setSelection(0)
            binding.secondDestinationVehicle.adapter = it
            binding.secondDestinationVehicle.setSelection(1)
            binding.thirdDestinationVehicle.adapter = it
            binding.thirdDestinationVehicle.setSelection(2)
            binding.fourthDestinationVehicle.adapter = it
            binding.fourthDestinationVehicle.setSelection(3)
        }
        vehiclesInfo.clear()
        vehiclesInfo.addAll(vehicles)
        vehiclesAdapter.notifyDataSetChanged()
    }

    private fun setupObservers() {

        viewModel.vehicleList.observe(viewLifecycleOwner) {
            updateVehiclesAdapter(it)
            updatePlanetVehicleInfo()
        }

        viewModel.planetsList.observe(viewLifecycleOwner) {
            updateDestinationAdapter(it)
            updatePlanetVehicleInfo()

        }

        viewModel.travelSummary.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.showError.observe(viewLifecycleOwner) {
        }

        viewModel.loadingProgressLiveData.observe(viewLifecycleOwner) {}
    }


    private fun validate(): Boolean {
        return viewModel.validateTravel(destinationsInfo,vehiclesInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String? = null, param2: String? = null) =
            FindFalconFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}