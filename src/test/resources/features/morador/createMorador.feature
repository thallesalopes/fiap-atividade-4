# language: pt

Funcionalidade: Cadastro de Morador no Sistema

  Como porteiro do condomínio
  Quero cadastrar um novo morador
  Para que ele possa receber notificações de encomendas

  Contexto:
    Dado que existe um apartamento cadastrado no sistema

  Cenário: Cadastrar morador com sucesso
    Quando eu cadastro um novo morador com dados válidos
    Então o morador deve ser salvo no sistema
    E o morador deve estar vinculado ao apartamento
    E os dados do morador devem estar corretos

  Cenário: Tentar cadastrar morador em apartamento inexistente
    Quando eu tento cadastrar um morador em um apartamento que não existe
    Então devo receber uma mensagem de erro informando que o apartamento não foi encontrado