package com.example.filmsapp

import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingDelegate<V : ViewBinding>(
    private val fragment : Fragment,
    private val bind: (view: View) -> V) :
    ReadOnlyProperty<Fragment, V> {
    private var viewBinding: V? = null
    private val handler = android.os.Handler(Looper.getMainLooper())
    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment){
            it.lifecycle.addObserver(object : LifecycleObserver{
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun destroy(){
                    handler.post {
                        viewBinding = null
                    }
                }
            })
        }
    }
    override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
        return viewBinding ?: run {
            val view = thisRef.requireView()
            bind.invoke(view).also {
                viewBinding = it
            }
        }
    }
}