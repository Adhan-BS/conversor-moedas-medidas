// classe que guarda os dados de cada gasto da viagem
class Transacao(
    val valorOriginal: Double, // valor na moeda estrangeira
    val moedaOrigem: String,   // nome da moeda (USD, EUR, GBP), só pra mostrar
    val moedaDestino: Int,      // código da moeda: 1 = USD, 2 = EUR, 3 = GBP
    val cartaoCredito: Boolean, // true se usou cartão, false se não
    val cupomIsencaoIOF: String? // permite uma string ser null
)

// classe que faz a conversão pra reais
class ConversorMoeda {

    // recebe uma Transacao e devolve o valor em reais
    fun converter(transacao: Transacao): Double {

        // o when escolhe a cotação de acordo com o código da moeda
        val cotacao = when (transacao.moedaDestino) {
            1 -> 5.20 // dólar
            2 -> 5.80 // euro
            3 -> 6.50 // libra
            else -> 1.0 // código desconhecido, não muda o valor
        }

        // valor original vezes a cotação
        val valorConvertido = transacao.valorOriginal * cotacao

        // cálculo do IOF
        var valorIof = 0.0

        // checa se a moeda de destino é internacional (códigos 1, 2 ou 3)
        val moedaInternacional = transacao.moedaDestino in 1..3

        // se a moeda for internacional E usou cartão de crédito
        if (moedaInternacional && transacao.cartaoCredito) {
            valorIof = valorConvertido * 0.0538

            // usa a safecall ? para pegar o tamanho do cupom
            // se o tamanho for null o valor também é null
            val tamanhoCupom = transacao.cupomIsencaoIOF?.length

            // se o valor não for null, significa que o cupom existe
            if (tamanhoCupom != null) {
                valorIof = 0.0
                println("Cupom aplicado para a moeda ${transacao.moedaOrigem}! IOF isento.")
            } else {
                println("IOF cobrado para a moeda ${transacao.moedaOrigem}.")
            }
        }

        // return devolve o resultado pra quem chamou a função
        return valorConvertido + valorIof
    }

    fun converterLote(gastos: List<Transacao>): Double {
        var totalViagem = 0.0

        // a estrutura do for percorre cada item da lista, a cada volta do laço a variável gasto representa uma transação diferente
        for (gasto in gastos) {
            // chame a função converter passando o gasto da vez e guarde o resultado
            val valorFinalDoGasto = converter(gasto)

            // Soma o valor final do gasto ao total da viagem
            totalViagem += valorFinalDoGasto
        }

        return totalViagem
    }
}

fun main() {
    // cria o conversor pra poder usar a função converter
    val conversor = ConversorMoeda()

    // usar listOf para agrupar
    val listaDeGastos = listOf(
        Transacao(100.0, "USD", 1, true, "ISENTOIOF"), // Ex: Compras diversas
        Transacao(50.0, "EUR", 2, false, null),        // Ex: Lanche rápido (estilo 99 Food)
        Transacao(20.0, "GBP", 3, true, null),         // Ex: Ingresso Cinépolis Millenium
        Transacao(15.0, "USD", 1, true, null),         // Ex: Compras no Supermercados DB
        Transacao(200.0, "EUR", 2, false, null)        // Ex: Lembrancinhas e transporte
    )

    val total = conversor.converterLote(listaDeGastos)

    // converte e mostra o total
    println("Total da viagem em Reais: R$ ${"%.2f".format(total)}")
}