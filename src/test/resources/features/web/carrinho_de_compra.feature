#language: pt
#enconding: UTF-8
#author: Iuri Ramos
#date: 15/07/2025
#version: 1.0

@web
Funcionalidade: Carrinho de compras

  Eu como usuário do site Advantage Online Shopping
  Quero buscar produtos e colocá-los no carrinho
  Para realizar um compra

  Esquema do Cenario: 01 - Realize a busca de um produto
    Dado que estou na página inicial com o campo de busca ativo
    Quando digito o nome do produto "LAPTOP"
    E pressiono a tecla "ENTER"
    Então sou apresentado aos "<Produtos>"

    Exemplos:
      | Produtos                   |
      | HP ENVY - 17t Touch Laptop |
#      | HP ENVY x360 - 15t Laptop           |
#      | HP Pavilion 15t Touch Laptop        |
#      | HP Pavilion 15z Laptop              |
#      | HP Pavilion 15z Touch Laptop        |
#      | HP Pavilion x360 - 11t Touch Laptop |
#      | HP Stream - 11-d020nr Laptop        |


  Cenario: 02 - Incluir produto no carrinho
    Dado que navego até a página do produto "HP Stream - 11-d020nr Laptop"
    E seleciono a opção de cor "BLUE"
    Quando clino no botão "ADD TO CART"
    E clico no icone do carrinho de compras
    Então confirmo que o produto "HP STREAM - 11-D020NR LAPTOP" foi adicionado ao carrinho


  Cenario: 03 - Validar os produtos incluídos no carrinho na tela de pagamento
    Dado que adicionei os seguintes produtos ao carrinho:
      | HP ENVY - 17t Touch Laptop   |
      | HP Stream - 11-d020nr Laptop |
      | HP H2310 In-ear Headset      |
    Quando acesso a tela de pagamento
    Então devo visualizar os mesmos produtos listados:
      | HP ENVY - 17T TOUCH LAPTOP   |
      | HP STREAM - 11-D020NR LAPTOP |
      | HP H2310 IN-EAR HEADSET      |