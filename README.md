# case-cadastro-pix

Resumo:
- Projeto Maven com Java8 e SpringBoot
- Banco de dados H2, salvando em disco
- Flyway para controle e versionamento de scripts BD
  - Constraints para garantir integridade do BD, conforme regras de negócios
- Maven jacoco-check configurado para cobertura de testes acima de 90%
- Versionamento Git

Padrões de projetos utilizados:
  - Padrão (Chain of Responsibility) com inversão de dependência do Spring, proporcionando executar todas as validações de negócio com baixo acoplamento.
  - Padrão (Builder) Facilitando a construção de objetos de resposta diferentes.

Seguindo princípios:
  - Princípios de SOLID
  - Princípio de Fail Fast (Falhe Rápido): Usando Java Bean Validation para realizar validações de entrada antes das regras de negócio.


Extras:
- Dockerfile: Para execução em container nos ambientes Docker
- GitHub CI: Workflow de build para cada commit realizado
- Documentação Swagger: http://localhost:8080/swagger-ui/index.html#/
- Cobertura de testes Jacoco: http://localhost:63342/pix/target/site/jacoco/index.html
- Configurado no maven o Check para 90% de cobertura

- ROADMAP de Melhorias
  - Ajustar tipos de respostas no documentação do Swagger (Atualmente default)
  - Criar profiles para ambiente Dev, Homol e Prod. Reduzindo a quantidade de variáveis a ser informadas.
  - Criar docker-compose para facilitar o deploy
  - Implementar OpenTracing (Jaeger)
  - Implementar geração de metricas (Prometheus)
  - Implementar cache nas consultas (MemCache ou Redis)