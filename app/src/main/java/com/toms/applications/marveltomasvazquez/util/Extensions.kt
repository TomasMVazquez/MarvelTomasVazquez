package com.toms.applications.marveltomasvazquez.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
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
 * Reduce Boilerplate when collecting flows
 */
fun <T> CoroutineScope.collectFlow(flow: Flow<T>, body: suspend (T) -> Unit) {
    flow.onEach { body(it) }
        .launchIn(this)
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

fun <T> MutableLiveData<MutableList<T>>.addAllItems(items: List<T>) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.addAll(items)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.addNewItemsAt(index: Int, items: List<T>) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.addAll(index, items.subList(index, items.lastIndex))
    this.value = oldValue
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

/**
 * Reduce Boilerplate when scrolling recycler
 */
val RecyclerView.lastVisibleEvents: Flow<Int>
    get() = callbackFlow<Int> {
        val lm = layoutManager as GridLayoutManager

        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                trySend(lm.findLastVisibleItemPosition())
            }
        }
        addOnScrollListener(listener)
        awaitClose { removeOnScrollListener(listener) }
    }.conflate()