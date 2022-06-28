# case-cadastro-pix

- Projeto Maven com Java8 e SpringBoot
- Banco de dados H2, salvando em disco
- Flyway para controle e versionamento de scripts BD
  - Constraints para garantir integridade do BD, conforme regras de negócios
- Princípio de Fail Fast (Falhe Rapido): Usando Java Bean Validation para realizar validações de entrada antes das regras de negócio.
- Principios de SOLID
- Padrão de projeto (Chain of Responsibility) com inversão de dependencia do Spring, proporcionando executar todas as validações de negócio com baixo acoplamento.
- Padrão de projeto (Builder) Facilitando a construção de objetos de resposta diferentes.
- Dockerfile: Para execução em container nos ambientes Docker
- GitHub CI: Workflow de build para cada commit realizado
- Documentação Swagger: http://localhost:8080/swagger-ui/index.html#/
- Cobertura de testes Jacoco: http://localhost:63342/pix/target/site/jacoco/index.html


- ROADMAP de Melhorias
  - Ajustar tipos de respostas no Swagger (Atualmente default)
  - Criar profiles para ambiente Dev, Homol e Prod
  - Implementar OpenTracing (Jaeger)
  - Implementar geração de metricas (Prometheus)
  - Implementar cache nas consultas (MemCache ou Redis)