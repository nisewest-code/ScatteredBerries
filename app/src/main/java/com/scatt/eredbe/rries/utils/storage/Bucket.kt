package com.scatt.eredbe.rries.utils.storage

import android.app.Application

object Bucket {
    var startUrl = ""
        set(value) {
            field = value
            PrefScatteredBerries.saveStartUrl(value)
        }
        get() {
            field = PrefScatteredBerries.getStartUrl()
            return field
        }
    var lastUrl = ""
        set(value) {
            field = value
            PrefScatteredBerries.saveLastUrl(value)
        }
        get() {
            field = PrefScatteredBerries.getLastUrl()
            return field
        }
    var status = ""
        set(value) {
            field = value
            PrefScatteredBerries.saveStatus(value)
        }
        get() {
            field = PrefScatteredBerries.getStatus()
            return field
        }
    var campaign = ""
        set(value) {
            field = value
            PrefScatteredBerries.saveCampaign(value)
        }
        get() {
            field = PrefScatteredBerries.getCampaign()
            return field
        }
    var fbclid = ""
        set(value) {
            field = value
            PrefScatteredBerries.saveFbclid(value)
        }
        get() {
            field = PrefScatteredBerries.getFbclid()
            return field
        }
    var pixelId = ""
        set(value) {
            field = value
            PrefScatteredBerries.savePixelId(value)
        }
        get() {
            field = PrefScatteredBerries.getPixelId()
            return field
        }
    var isMusic = false
        set(value) {
            field = value
            PrefScatteredBerries.saveMusic(value)
        }
        get() {
            field = PrefScatteredBerries.isMusic()
            return field
        }

    suspend fun init(context: Application) {
        PrefScatteredBerries.initPref(context)
    }
}