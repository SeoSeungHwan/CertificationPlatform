package com.router.certificateplatform.repository

import com.router.certificateplatform.model.Infomation
import retrofit2.http.GET
import retrofit2.http.Query

interface QnetService {
    companion object{
        const val BASE_URL = "http://api.odcloud.kr/api/15082998/v1/"
    }

    @GET("./uddi:c357330a-6fe8-406c-9a50-c34e18cca518")
    suspend fun getInformation(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey",encoded = true) serviceKey: String
    ) : Infomation
}