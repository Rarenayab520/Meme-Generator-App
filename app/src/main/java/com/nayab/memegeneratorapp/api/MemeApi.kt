package com.nayab.memegeneratorapp.api

import com.nayab.memegeneratorapp.data.RedditMeme
import retrofit2.http.GET

interface MemeApi {
    @GET("gimme")
    suspend fun getMeme(): RedditMeme
}
