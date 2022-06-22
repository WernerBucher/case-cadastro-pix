CREATE TABLE chave (
    id UUID NOT NULL DEFAULT random_uuid() PRIMARY KEY,
    tipoChave VARCHAR(9) NOT NULL,
    valorChave VARCHAR(77) NOT NULL UNIQUE,
    tipoConta VARCHAR(10) NOT NULL,
    numeroAgencia NUMERIC(4) NOT NULL,
    numeroConta NUMERIC(8) NOT NULL,
    nomeCorrentista VARCHAR(30) NOT NULL,
    sobrenomeCorrentista VARCHAR(45) NULL,
    tipoPessoa CHAR(1) NOT NULL,
    CONSTRAINT CHK_tipoChave CHECK (tipoChave in ('ALEATORIO', 'CELULAR', 'CNPF', 'CPF', 'EMAIL')),
    CONSTRAINT CHK_tipoConta CHECK (tipoConta in ('CORRENTE', 'POUPANCA')),
    CONSTRAINT CHK_tipoPessoa CHECK (tipoPessoa in ('F', 'J'))
);
