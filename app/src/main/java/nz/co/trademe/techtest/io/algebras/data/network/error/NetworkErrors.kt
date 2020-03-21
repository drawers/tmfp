package nz.co.trademe.techtest.io.algebras.data.network.error

sealed class NetworkError : Throwable() {
    object Unauthorized : NetworkError()
    object NotFound : NetworkError()
    object ServerError : NetworkError()
}
