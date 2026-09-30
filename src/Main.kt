//classe que guarda os dados de cada gasto da viagem
class Transacao(
    val valorOriginal: Double, //valor na moeda estrangeira
    val moedaOrigem: String,   //nome da moeda (USD, EUR, GBP), só pra mostrar
    val moedaDestino: Int,      //codigo da moeda: 1 = USD, 2 = EUR, 3 = GBP
    val cartaoCredito: Boolean //true se usou cartão, false se não
)

//classe que faz a conversao pra reais
class ConversorMoeda {

    //recebe uma Transacao e devolve o valor em reais
    fun converter(transacao: Transacao): Double {

        //o when escolhe a cotacao de acordo com o codigo da moeda
        val cotacao = when (transacao.moedaDestino) {
            1 -> 5.20 //dolar
            2 -> 5.80 //euro
            3 -> 6.50 //libra
            else -> 1.0 //codigo desconhecidp  nao muda o valor
        }

        //valor original vezes a cotacao
        val valorConvertido = transacao.valorOriginal * cotacao

        //calculo do IOF
        var valorIof = 0.0

        // checa se a moeda de destino é internacional (códigos 1, 2 ou 3)
        val moedaInternacional = transacao.moedaDestino in 1..3

        // se a moeda for internacional E usou cartão de crédito
        if (moedaInternacional && transacao.cartaoCredito) {
            valorIof = valorConvertido * 0.0538
        }
        //return devolve o resultado pra quem chamou a funcao
        return valorConvertido + valorIof
    }
}

fun main() {
    // cria o conversor pra poder usar a funcao converter
    val conversor = ConversorMoeda()

    // cria 3 gastos de teste (agora com o true ou false do cartão)
    val gasto1 = Transacao(100.0, "USD", 1, true)  // usou cartão (vai ter IOF)
    val gasto2 = Transacao(50.0, "EUR", 2, false)  // dinheiro espécie (sem IOF)
    val gasto3 = Transacao(20.0, "GBP", 3, true)   // usou cartão (vai ter IOF)

    // converte e mostra cada gasto
    println("Gasto 1: R$ ${conversor.converter(gasto1)}")
    println("Gasto 2: R$ ${conversor.converter(gasto2)}")
    println("Gasto 3: R$ ${conversor.converter(gasto3)}")
}