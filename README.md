# case-cadastro-pix

- Projeto Java8 + SpringBoot
- Banco de dados H2, salvando em disco
- Flyway para controle e versionamento de scripts BD
  - Constraints para garantir integridade do BD, conforme regras de negócios
- Princípio  de Fail Fast (Falhe Rapido): Usando Java Bean Validation para realizar validações de entrada antes das regras de negócio.
- Padrão de projeto (Chain of Responsibility) com inversão de dependencia do Spring, proporcionando executar todas as validações de negócio com baixo acoplamento.
- SOLID
- Documentação: http://localhost:8080/swagger-ui/index.html#/
- Cobertura de testes: http://localhost:63342/pix/target/site/jacoco/index.html


- ROADMAP
  - Melhorar mensagens e criar arquivo de properties
  - Implementar OpenTracing
  - Implementar geração de metricas
  - Implementar cache nas consultas