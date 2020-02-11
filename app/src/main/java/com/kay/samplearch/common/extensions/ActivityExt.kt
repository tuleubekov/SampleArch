package com.kay.samplearch.common.extensions

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.kay.samplearch.presentation.base.AlertDialogFragment
import com.kay.samplearch.presentation.base.BaseActivity
import kotlin.reflect.KClass

fun <T : Activity> Activity.startNewActivity(activityClass: KClass<T>, block: Intent.() -> Unit = {}) {
    val intent = Intent(this, activityClass.java)
    intent.block()
    this.startActivity(intent)
}

fun <T : Activity> Activity.startNewActivity(activityClass: KClass<T>, requestCode: Int, block: Intent.() -> Unit = {}) {
    val intent = Intent(this, activityClass.java)
    intent.block()
    this.startActivityForResult(intent, requestCode)
}

fun FragmentActivity.fragmentExists(tag: Class<*>): Boolean {
    return supportFragmentManager.findFragmentByTag(tag.name) != null
}

fun FragmentActivity.fragmentExists(ktag: KClass<*>): Boolean {
    return supportFragmentManager.findFragmentByTag(ktag.java.name) != null
}

fun FragmentActivity.fragmentExists(tag: String): Boolean {
    return supportFragmentManager.findFragmentByTag(tag) != null
}

fun BaseActivity.createFragmentIfNotExists(bundle: Bundle? = null, replace: () -> Unit) {
    if (supportFragmentManager.findFragmentById(fragmentContainerId) == null) {
        if (bundle != null) return

        replace()
    }
}

fun BaseActivity.replaceFragment(fragment: Fragment, block: FragmentTransaction.() -> FragmentTransaction = { this }): Fragment {
    val transaction = supportFragmentManager.beginTransaction()
    transaction
        .block()
        .replace(fragmentContainerId, fragment, fragment.javaClass.name)
        .commitAllowingStateLoss()

    return fragment
}

fun BaseActivity.addFragment(fragment: Fragment, block: FragmentTransaction.() -> FragmentTransaction = { this }): Fragment {
    val transaction = supportFragmentManager.beginTransaction()
    transaction
        .block()
        .add(fragmentContainerId, fragment, fragment.javaClass.name)
        .commitAllowingStateLoss()

    return fragment
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .remove(fragment)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.base() = this.cast(BaseActivity::class)

fun AppCompatActivity.showAlert(
    message: String,
    title: String = "",
    positive: String = "Ok",
    negative: String? = null,
    negativeCallback: (DialogInterface) -> Unit = { it.dismiss() },
    positiveCallback: (DialogInterface) -> Unit = { it.dismiss() },
    buttonColor: Int? = null
) {
    supportFragmentManager.beginTransaction().add(
        AlertDialogFragment().apply {
            this.message = message
            this.title = title
            this.positive = positive
            this.negative = negative
            this.negativeCallback = negativeCallback
            this.positiveCallback = positiveCallback
            buttonColor?.let { this.buttonColor = it }
        }
        , "AlertDialogFragment").commitAllowingStateLoss()
}