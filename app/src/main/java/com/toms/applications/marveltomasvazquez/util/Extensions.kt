package com.toms.applications.marveltomasvazquez.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Marvel API requires MD5 Hash created by Timestamp and private and public keys
 */
fun String.md5Hash(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}


/**
 * Reduce Boilerplate when creating view models
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T: ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object: ViewModelProvider.Factory{
        override fun <U : ViewModel?> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this,vmFactory).get(T::class.java)
}

/**
 * To be able to hide softkeyboard on demand
 */
fun Fragment.hideKeyboard() {
    val inputMethodManager = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

/**
 * To be able to treat a MutableLivedata like a list and add o remove items
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(item)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.addNewItemAt(index: Int, item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(index, item)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.removeItemAt(index: Int) {
    if (!this.value.isNullOrEmpty()) {
        val oldValue = this.value
        oldValue?.removeAt(index)
        this.value = oldValue
    } else {
        this.value = mutableListOf()
    }
}

/**
 * To be able to filter LiveData
 */
inline fun <T> LiveData<T>.filter(crossinline filter: (T?) -> Boolean): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(this@filter) {
            if (filter(it)) {
                this.value = it
            }
        }
    }
}