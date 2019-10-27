package br.com.app4lawyer.data.remote.network

import com.squareup.moshi.Json

data class AppResponse(
    @Json(name = "lista")
    val lista: List<String>,
    @Json(name = "data")
    val objeto: AppNetwork?,
    @Json(name = "mensagem")
    val mensagem: String,
    @Json(name = "status")
    val status: String
)

data class AppNetwork(
    @Json(name = "nome")
    val nome: String? = null
)


data class AppDTO(
    @Json(name = "doc_name")
    val docName: String? = null
)