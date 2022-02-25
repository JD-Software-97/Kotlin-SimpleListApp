package com.jd.harrypotterapp.core

interface BaseComponent<T> {
    fun inject(target: T)
}