package com.reemsib.shadowreader.model


class Recording(var uri: String, var fileName: String, isPlaying: Boolean) {
    var isPlaying = false

    init {
        this.isPlaying = isPlaying
    }
}