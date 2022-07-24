package it.elsalamander.loaderclass

import androidx.appcompat.app.AppCompatActivity

interface OnFirstLoad {

    fun onFirstLoad(holder : Holder, activity : AppCompatActivity)
}