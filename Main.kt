class Transacao(
    val valorOriginal: Double,
    val moedaOrigem: String,
    val moedaDestino: Int
)

fun main() {
    val gasto = Transacao(100.0, "USD", 1)

    println("Valor: ${gasto.valorOriginal}")
    println("Moeda: ${gasto.moedaOrigem}")
    println("Código de destino: ${gasto.moedaDestino}")
}