package com.samsung.monimo.Utils

import android.app.Application

class MyApplication : Application() {
    companion object {

        var selectedApartmentName = ""
        var selectedApartmentId = 0

        var selectedPeriod = 0

        var roi = ""
    }
}
