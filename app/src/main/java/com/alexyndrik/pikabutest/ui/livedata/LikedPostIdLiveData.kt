package com.alexyndrik.pikabutest.ui.livedata

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.service.PikabuApiClient

object LikedPostIdLiveData : MutableLiveData<PikabuApiClient.Response<Int>>()