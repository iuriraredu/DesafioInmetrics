#language: pt
#enconding: UTF-8
#author: Iuri Ramos
#date: 02/08/2025
#version: 1.0

@api @AtualizarImagem @AdvantageShopping @Todos
Funcionalidade: Advantage Shopping - Atualizar imagem de um Produto

  Eu como consumidor da API do Advantage Online Shopping
  Quero atualizar a imagem de um produto
  Para garantir que a imagem esteja atualizada

  @01
  Cenário: 01 - Realizar POST para atualizar a imagem de um produto existente - Status Code: 200
  Dado que eu tenho um token de administrador válido
  E um arquivo de imagem "got.jpg" para upload
  Quando eu envio a imagem para o produto com ID 83
  Então devo receber status code 200
  E o corpo da resposta deve conter os campos:
  | success     | true                            |
  | id          | 83                              |
  | reason      | Product was updated successful  |
  | imageId     | custom_image_                   |
  | imageColor  | red                             |