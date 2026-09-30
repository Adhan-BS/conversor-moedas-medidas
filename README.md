# Conversor de Moedas e Medidas

**Estudo de Caso 22**
**Aluno:** Adhan Borges de Souza

Este projeto é o núcleo de cálculo de um aplicativo para viajantes, feito em Kotlin. Ele converte moedas, transforma unidades de medida (como km em milhas), aplica a taxa de turismo (IOF) e calcula o total dos gastos da viagem em Reais.

## Estrutura do código

Todo o código está em um único arquivo, `src/main/kotlin/Main.kt`:

- **`Transacao`**: guarda os dados de cada gasto (`valorOriginal`, `moedaOrigem`, `moedaDestino`, `cartaoCredito` e `cupomIsencaoIOF`).
- **`ConversorMoeda`**: converte uma transação para Reais (`converter`) e processa uma lista de gastos, somando o total da viagem (`converterLote`).
- **`ConversorMedida`**: converte quilômetros em milhas e Celsius em Fahrenheit.
- **`main`**: cria uma lista de 5 gastos, calcula o total e mostra exemplos de conversão de medidas.

## Regras de negócio

- **Cotações:** o sistema usa um código numérico para escolher a cotação (estrutura `when`): `1` para USD (5,20), `2` para EUR (5,80) e `3` para GBP (6,50).
- **IOF:** em compras internacionais no cartão de crédito, é acrescentada uma taxa de 5,38% sobre o valor convertido (estrutura `if`).
- **Cupom de isenção:** o campo `cupomIsencaoIOF` é opcional (`String?`) e pode ser `null` (ausente). Se o cupom não for nulo, o IOF é anulado. A verificação usa Safe Call (`?.length`).
- **Cálculo em lote:** o programa percorre uma lista de 5 gastos com um laço `for`, converte cada um e soma o total da viagem em Reais.

## Decisões de implementação

1. **Cupom vazio isenta o IOF:** o enunciado diz que, se o cupom não for nulo, a taxa é anulada. Como uma string vazia (`""`) não é `null`, o sistema também zera o IOF nesse caso, seguindo o texto ao pé da letra.
2. **Moeda de origem:** o nome da moeda (por exemplo, `"USD"`) serve só para deixar as mensagens do console mais claras. O cálculo depende apenas do código numérico (`moedaDestino`).
3. **Moeda desconhecida:** se for informado um código diferente de 1, 2 ou 3, o `when` cai no `else` e usa a cotação 1,0. Como esse código não é considerado moeda internacional, o IOF não é cobrado.
4. **Classe de medidas:** o enunciado pede a classe `ConversorMedida`, mas não define o que ela deve fazer. Implementei duas conversões úteis para viagem (km para milhas e Celsius para Fahrenheit), sugeridas com apoio de IA.
5. **Formatação do total (`%.2f`):** essa formatação é aplicada só na hora de exibir o resultado, para mostrar duas casas decimais. O cálculo interno mantém todos os decimais. Dependendo da configuração de idioma do sistema, o separador decimal pode aparecer como vírgula.

## Como rodar

1. Clone este repositório.
2. Abra o projeto em uma IDE como **IntelliJ IDEA** ou **Android Studio**. É necessário ter o Java JDK instalado (versão 17 ou 21).
3. Execute a função `main` do arquivo `src/main/kotlin/Main.kt`.

Saída esperada:

```
Cupom aplicado para a moeda USD! IOF isento.
IOF cobrado para a moeda GBP.
IOF cobrado para a moeda USD.
Total da viagem em Reais: R$ 2189.19
100.0 km = 62.1371 milhas
35.0 °C = 95.0 °F
```

## Processo de estudo e desenvolvimento

Antes de usar IA, passei cerca de dois dias tentando resolver o problema sozinho. Só encontrei projetos muito complexos, como o [CCC](https://github.com/Oztechan/CCC), que usei como referência geral de como um conversor de moedas pode ser organizado.

Depois disso, pedi ao **Gemini** uma solução completa, que serviu de ponto de partida para entender o panorama geral. Em seguida, refiz o projeto do zero, em etapas, com apoio do **Claude**, que explicou os conceitos e revisou o meu código. Nas etapas 1 e 2 (classe `Transacao` e `when` das cotações), segui exemplos explicados pelo Claude. Da etapa 3 em diante (IOF, cupom, lote e medidas), escrevi o código por conta própria e usei a IA para revisar a lógica e corrigir erros, como um símbolo `$` fora do lugar nas mensagens do console.

Para estudar, usei a documentação oficial do Kotlin, com foco em *Control flow*, *Null safety* e *Functions*. Já tinha experiência com POO em Python, então a parte nova foi a sintaxe do Kotlin, a tipagem e o null safety.

O histórico de commits segue as etapas em que o projeto foi refeito.