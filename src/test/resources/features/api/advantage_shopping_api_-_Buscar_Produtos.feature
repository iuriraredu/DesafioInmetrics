#language: pt
#enconding: UTF-8
#author: Iuri Ramos
#date: 02/08/2025
#version: 1.0

@api @BuscarProdutos @AdvantageShopping @Todos
Funcionalidade: Advantage Shopping - BUscar Produtos

  Eu como consumidor da API do Advantage Online Shopping
  Quero buscar produtos por nome
  Para encontrar itens específicos no catálogo

  @01
  Cenário: 01 - Realizar GET por nome exato (HP Stream - 11-d020nr Laptop) sem paremetro quantityPerEachCategory configurado - Status Code: 200
    Dado que busco pelo nome "HP Stream - 11-d020nr Laptop" sem o parâmetro "quantityPerEachCategory" configurado
    Quando envio uma requisição GET
    Então devo receber status code 200
    E apenas 1 categoria deve ser retornada
    E apenas 1 produto deve ser retornado
    E a estrutura da resposta deve ser válida

  @02
  Cenário: 02 - Realizar GET por nome exato (Game of Thrones) com paremetro quantityPerEachCategory configurado para apenas 1 resultado - Status Code: 200
    Dado que busco pelo nome "Game of Thrones" com parâmetro "quantityPerEachCategory" configurado para 1
    Quando envio uma requisição GET
    Então devo receber status code 200
    E apenas 1 categoria deve ser retornada
    E apenas 1 produto deve ser retornado
    E a estrutura da resposta deve ser válida

  @03
  Cenário: 03 - Realizar GET por nome exato (Game of Thrones) com paremetro quantityPerEachCategory configurado para 10 resultado - Status Code: 200
    Dado que busco pelo nome "Game of Thrones" com parâmetro "quantityPerEachCategory" configurado para 10
    Quando envio uma requisição GET
    Então devo receber status code 200
    E apenas 1 categoria deve ser retornada
    E apenas 10 produto deve ser retornado
    E a estrutura da resposta deve ser válida

  @04
  Cenário: 04 - Realizar GET por nome exato (Game of Thrones) com paremetro quantityPerEachCategory configurado para todos os resultado - Status Code: 200
    Dado que busco pelo nome "Game of Thrones" com parâmetro "quantityPerEachCategory" configurado para -1
    Quando envio uma requisição GET
    Então devo receber status code 200
    E apenas 1 categoria deve ser retornada
    E todos os produtos devem ser retornados
    E a estrutura da resposta deve ser válida

  @05
  Cenario: 05 - Realizar GET por nome exato (HP Stream - 11-d020nr Laptop) para url incorreta - Status Code: 404
    Dado que busco pelo nome "HP Stream - 11-d020nr Laptop"
    Quando envio uma requisição GET para uma URL incorreta
    Então devo receber status code 404
    E resposta HTML de erro
