# Invillia recruitment challenge


## Como executar o projeto

 * Acesse a pastar docker e altere o mapeamento do volume para sua pasta local. Assim o MySQL e o RabbitMQ serão inicializados.
 * Comece inicializando o eureka-server e os outros serviços podem subir em qualquer order.
 * Para facilitar os testes tesmos um arquivo do postman compartilhado **invillia-backend-challenge.postman_collection.json** 

## Serviços

Foram criados 4 serviços o service discovery com Eureka Service, serviço de loja, serviço de pedidos e serviço de pagamento. O fluxo que desenhei foi o seguinte:
* Registro e manipulação da loja no Serviço de loja
* Registro e manupulação do pedido no Serviço de pedido, como pre requisito informando o id da loja para cadastro e update.
* O pagamento foi desenhado seguindo o conseito do Saga Orquestrado. Quando a orderm de pagamento chega é registada no banco de dados local com o status de processando e é publicado um comando na fila de baixa de pedido para finaliar o pedido, caso o processo normalmente o serviço de pagamento é avisado para concluir o pagamento e nos casos de erro uma operação de rollback é disparada e o pagamento termina com o status de pendente.
* O reembolso segue essa mesma lógica de implementação.

## O que gostaria de melhorar?
* Subir os serviços criados com o docker
* Melhorar os teste unitários e implentar teste de integração
* Implementar segurança com Spring Cloud OAuth2

