package com.kay.samplearch.presentation.feature.webView

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WebViewLauncher (
    val url: String,
    val toolbarTitle: String = "",
    val content: String
): Parcelable