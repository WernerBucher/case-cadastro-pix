
INSERT
INTO
  chave
  (id, tipoChave, valorChave, tipoConta, numeroAgencia, numeroConta, nomeCorrentista, sobrenomeCorrentista, tipoPessoa, dataHoraCadastro)
VALUES
  ('5f3ae2de-4884-41d2-8c7c-387bf5e0f18d', 'CPF', '84652485085', 'CORRENTE', 10, 1000, 'João', '', 'F', NOW());

INSERT
INTO
  chave
  (id, tipoChave, valorChave, tipoConta, numeroAgencia, numeroConta, nomeCorrentista, sobrenomeCorrentista, tipoPessoa, dataHoraCadastro)
VALUES
  ('49546191-d330-428c-8d3f-05b5d9a8acd3', 'EMAIL', 'joao@email.com.br', 'CORRENTE', 10, 1000, 'João', '', 'F', NOW());

INSERT
INTO
  chave
  (id, tipoChave, valorChave, tipoConta, numeroAgencia, numeroConta, nomeCorrentista, sobrenomeCorrentista, tipoPessoa, dataHoraCadastro, dataHoraInativacao)
VALUES
  ('6585e271-0a00-4f15-a06e-10295fcfc030', 'ALEATORIO', 'chavealeatoria', 'CORRENTE', 10, 1000, 'João', '', 'F', NOW(), NOW());


INSERT
  INTO
    chave
    (id, tipoChave, valorChave, tipoConta, numeroAgencia, numeroConta, nomeCorrentista, sobrenomeCorrentista, tipoPessoa, dataHoraCadastro)
  VALUES
    ('06ac926f-fb7e-4cb9-8cdf-2dd825fa6742', 'CPF', '35032632090', 'CORRENTE', 10, 2000, 'Maria', 'Sobrenome', 'F', NOW());