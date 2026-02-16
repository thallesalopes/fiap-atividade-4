# language: pt

Funcionalidade: Recebimento de Encomendas pela Portaria

  Como porteiro do condomínio
  Quero registrar o recebimento de encomendas
  Para que os moradores sejam notificados automaticamente

  Contexto:
    Dado que existe um apartamento cadastrado no sistema

  Cenário: Registrar recebimento de encomenda com sucesso
    Quando o porteiro registra uma nova encomenda para o apartamento
    Então a encomenda deve ser salva no sistema
    E uma notificação deve ser enviada para a fila do Kafka
    E o morador do apartamento deve receber um email

  Cenário: Tentar registrar encomenda para apartamento inexistente
    Quando o porteiro tenta registrar uma encomenda para um apartamento que não existe
    Então devo receber uma mensagem de erro informando que o apartamento não foi encontrado

  Cenário: Tentar registrar encomenda com data futura
    Quando o porteiro tenta registrar uma encomenda com data de entrega no futuro
    Então devo receber uma mensagem de erro informando que a data é inválida
