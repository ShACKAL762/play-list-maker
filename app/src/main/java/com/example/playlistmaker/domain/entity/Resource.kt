package com.example.playlistmaker.domain.entity

sealed class Resource<T>( val code: Int, val message:String? = null) {
    class Success<T>(val data: T, responseCode: Int) : Resource<T>(responseCode)
    class Fail<T>(responseCode: Int, message: String): Resource<T>(responseCode,message )
}