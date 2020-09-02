package com.alexyndrik.pikabutest.ui.livedata

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.PikabuApiClient

object AllPostsLiveData : MutableLiveData<PikabuApiClient.Response<Map<Int, PikabuPost>>>()