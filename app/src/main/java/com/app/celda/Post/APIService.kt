package com.app.celda.Post


import com.app.celda.TokenManager
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("/api/register/")
    suspend fun pushPostReg(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/api/token/")
    suspend fun pushPostLog(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("api/admission_course/{id}")
    suspend fun pushPostSub(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @GET("/courses/")
    suspend fun getCourses(): Response<ResponseBody>

    @GET("/api/sub_course/{id}")
    suspend fun getSubCourse(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @GET("/profile/")
    suspend fun getProfile(@Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @GET("/info_course/{id}")
    suspend fun getInfoCourse(@Path("id") id: Int): Response<ResponseBody>

    @GET("/all_data_course/{id}")
    suspend fun getModules(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @GET("/api/refresh_token/")
    suspend fun refreshToken(): Response<ResponseBody>

    @GET("/api/lesson/{id}")
    suspend fun getLecture(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @GET("/api/content_progress_course/{id}")
    suspend fun getDone(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @POST("/search_course/")
    suspend fun getSearch(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/progress_course/")
    suspend fun getProgressCourse(@Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>

    @PUT("/api/progress_lesson/{id}")
    suspend fun putProgressLesson(@Path("id") id: Int, @Header("Authorization") token: String = "Bearer " + TokenManager().getToken()): Response<ResponseBody>
}

