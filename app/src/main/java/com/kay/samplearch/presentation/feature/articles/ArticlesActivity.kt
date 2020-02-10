package com.kay.samplearch.presentation.feature.articles

import android.os.Bundle
import com.kay.samplearch.base.BaseActivity
import com.kay.samplearch.common.extensions.replaceFragment

class ArticlesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFragmentIfNotExists(bundle = savedInstanceState) {
            replaceFragment(ArticlesFragment.newInstance())
        }
    }
}
