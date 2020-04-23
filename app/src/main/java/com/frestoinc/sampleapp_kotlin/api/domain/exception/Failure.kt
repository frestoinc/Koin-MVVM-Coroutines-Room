package com.frestoinc.sampleapp_kotlin.api.domain.exception

/**
 * Created by frestoinc on 11,April,2020 for SampleApp_Kotlin.
 */
sealed class Failure(val ex: Exception = Exception("Failure")) {
    object None : Failure()
    object NetworkConnection : Failure()
    object ServerError : Failure()

    open class FeatureFailure(featureException: Exception = Exception("Feature failure")) :
        Failure(featureException)
}