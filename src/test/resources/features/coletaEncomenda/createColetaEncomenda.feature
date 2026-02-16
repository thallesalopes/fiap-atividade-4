# language: pt

Funcionalidade: Coleta de Encomendas pelos Moradores

  Como morador do condomínio
  Quero retirar minha encomenda na portaria
  Para que o sistema registre a baixa da encomenda

  Contexto:
    Dado que existe uma encomenda recebida e aguardando coleta

  Cenário: Registrar coleta de encomenda com sucesso
    Quando o morador retira a encomenda na portaria
    E o porteiro confirma a coleta informando CPF e nome do morador
    Então a coleta deve ser registrada no sistema
    E o status da encomenda deve ser atualizado para "COLETADA"

  Cenário: Tentar registrar coleta de encomenda inexistente
    Quando o porteiro tenta registrar uma coleta para uma encomenda que não existe
    Então devo receber uma mensagem de erro informando que a encomenda não foi encontrada

  Cenário: Tentar registrar coleta com data futura
    Quando o porteiro tenta registrar uma coleta com data no futuro
    Então devo receber uma mensagem de erro informando que a data é inválida
