package com.kay.samplearch.presentation.feature.articles

import android.os.Bundle
import com.kay.samplearch.presentation.base.BaseActivity
import com.kay.samplearch.presentation.extensions.replaceFragment

class ArticlesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFragmentIfNotExists(bundle = savedInstanceState) {
            replaceFragment(ArticlesFragment.newInstance())
        }
    }
}
