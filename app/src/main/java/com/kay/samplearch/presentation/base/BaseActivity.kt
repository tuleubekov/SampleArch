package com.kay.samplearch.presentation.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.kay.samplearch.R

abstract class BaseActivity : AppCompatActivity() {

    open val fragmentContainerId get() = R.id.fragment_container
    open val layoutId = R.layout.fragments_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(fragmentContainerId)

        if (fragment is BaseFragment && fragment.handleActivityBackPressed()) {
            return
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return
        }

        super.onBackPressed()
    }

    fun createFragmentIfNotExists(bundle: Bundle? = null, replace: () -> Unit) {
        if (supportFragmentManager.findFragmentById(fragmentContainerId) == null) {
            if (bundle != null) return;

            replace()
        }
    }

    fun <T: Parcelable> launcher(): T {
        return intent?.getParcelableExtra<Parcelable>(LAUNCHER) as? T ?: throw RuntimeException("Launcher can not be null")
    }

    fun <T: Parcelable> launcherNullable(): T? {
        return intent?.getParcelableExtra<Parcelable>(LAUNCHER) as? T?
    }

    companion object {
        const val LAUNCHER = "LAUNCHER"

        fun launcher(launcher: Parcelable, additionalBlock: Intent.() -> Unit = {}): Intent.() -> Unit = {
            putExtra(BaseFragment.LAUNCHER, launcher)
            additionalBlock(this)
        }
    }
}
