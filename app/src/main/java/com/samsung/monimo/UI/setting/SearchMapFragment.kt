package com.samsung.monimo.UI.setting

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.samsung.monimo.API.model.ApartmentListResult
import com.samsung.monimo.BuildConfig
import com.samsung.monimo.MainActivity
import com.samsung.monimo.R
import com.samsung.monimo.UI.BottomSheet.SearchBottomSheet
import com.samsung.monimo.UI.setting.viewModel.ApartmentListViewModel
import com.samsung.monimo.databinding.FragmentSearchMapBinding

class SearchMapFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentSearchMapBinding
    lateinit var viewModel: ApartmentListViewModel
    lateinit var mainActivity: MainActivity

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    var apartmentList = mutableListOf<ApartmentListResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchMapBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(requireActivity())[ApartmentListViewModel::class.java]

        viewModel.run {
            apartmentInfoList.observe(mainActivity) {
                apartmentList = it
            }
        }

        initView()

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val sheetBehavior =
            BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetLayout)

        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        NaverMapSdk.getInstance(requireContext()).client =
            NaverMapSdk.NaverCloudPlatformClient("${BuildConfig.MAP_API_KEY}")

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        viewModel.getApartmentList(mainActivity)
        getHouseMapList()

        // 확대 축소
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // 지도 옵션 설정
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        naverMap.isIndoorEnabled = true

        // 초기 위치 설정
        val cameraUpdate = CameraUpdate.scrollTo((LatLng(37.495974, 127.0212844))).animate(
            CameraAnimation.Easing,
        )
        naverMap.moveCamera(cameraUpdate)
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "목표 설정"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun modalBottomSheet(search: String) {
        val modal = SearchBottomSheet(search)
        modal.show(requireActivity().supportFragmentManager, "지도")
    }

    fun getHouseMapList() {
        // Marker
        val markers = mutableListOf<Marker>()

        for (i in 0 until apartmentList.size) {
            val marker = Marker()
            var latitude = apartmentList?.get(i)!!.latitude.toDouble()
            var longitude = apartmentList?.get(i)!!.longitude.toDouble()
            marker.position = LatLng(latitude, longitude)
            marker.icon = OverlayImage.fromResource(R.drawable.ic_marker)
            markers.add(marker)
        }

        for (m in 0 until markers.size) {
            markers[m].map = naverMap
            markers[m].setOnClickListener {
                for (i in 0 until markers.size) {
                    if (markers[i].icon == OverlayImage.fromResource(R.drawable.ic_marker_selected)) {
                        markers[i].icon =
                            OverlayImage.fromResource(R.drawable.ic_marker)
                    }
                }
                markers[m].icon = OverlayImage.fromResource(R.drawable.ic_marker_selected)

                modalBottomSheet(apartmentList[m].apartmentName)

                true
            }
        }
    }
}
