// MemeRepository.kt
package com.nayab.memegeneratorapp.repository

import com.nayab.memegeneratorapp.api.RetrofitInstance
import com.nayab.memegeneratorapp.data.RedditMeme

class MemeRepository {
    suspend fun getRandomMeme(): RedditMeme {
        return RetrofitInstance.api.getMeme()
    }
}
