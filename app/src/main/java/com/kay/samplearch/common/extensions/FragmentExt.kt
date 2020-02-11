package com.kay.samplearch.common.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kay.samplearch.R
import com.kay.samplearch.presentation.base.BaseActivity
import com.kay.samplearch.presentation.base.BaseFragment
import kotlin.reflect.KClass

fun <T: Any> KClass<T>.name(): String {
    return this::java.get().canonicalName ?: ""
}

fun Fragment.name(): String{
    return this.javaClass.canonicalName ?: ""
}

fun FragmentTransaction.animationLeftRight(): FragmentTransaction {
    return setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
}

fun FragmentTransaction.animationBottomTop(): FragmentTransaction = setCustomAnimations(
    R.anim.enter_from_bottom, R.anim.anim_stay,
    R.anim.anim_stay, R.anim.exit_to_bottom
)

fun <T: Fragment> T.addArguments(block: Bundle.() -> Unit): T {
    arguments = Bundle().apply { this.block() }

    return this
}

fun BaseActivity.currentFragment(): BaseFragment? {
    return this.supportFragmentManager.findFragmentById(this.fragmentContainerId) as? BaseFragment
}