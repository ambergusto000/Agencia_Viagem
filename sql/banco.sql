CREATE DATABASE agencia_viagens;

USE agencia_viagens;

-- Criando a tabela clientes
CREATE TABLE clientes (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100),
                          tipo ENUM('nacional', 'estrangeiro'),
                          cpf VARCHAR(14) NULL,  -- O CPF pode ser NULL para estrangeiros
                          passaporte VARCHAR(20) NULL,  -- O Passaporte pode ser NULL para nacionais
                          telefone VARCHAR(20),
                          email VARCHAR(100)
);

-- Criando a tabela pacotes
CREATE TABLE pacotes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100),
                         destino VARCHAR(100),
                         duracao INT,
                         preco DECIMAL(10,2),
                         tipo VARCHAR(50)
);

-- Criando a tabela servicos
CREATE TABLE servicos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100),
                          descricao TEXT,
                          preco DECIMAL(10,2)
);

-- Criando a tabela contratos (apenas uma vez, com ON DELETE CASCADE)
CREATE TABLE contratos (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           cliente_id INT,
                           pacote_id INT,
                           FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                           FOREIGN KEY (pacote_id) REFERENCES pacotes(id) ON DELETE CASCADE
);

-- Criando a tabela contrato_servico (com ON DELETE CASCADE)
CREATE TABLE contrato_servico (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  contrato_id INT,
                                  servico_id INT,
                                  FOREIGN KEY (contrato_id) REFERENCES contratos(id) ON DELETE CASCADE,
                                  FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
);

-- Inserindo dados de clientes
INSERT INTO clientes (nome, tipo, cpf, telefone, email)
VALUES ('João Silva', 'nacional', '123.456.789-00', '11999999999', 'joao@email.com');

INSERT INTO clientes (nome, tipo, passaporte, telefone, email)
VALUES ('Anna Müller', 'estrangeiro', 'XJ982374', '+49 151234567', 'anna@email.de');

-- Inserindo pacotes
INSERT INTO pacotes (nome, destino, duracao, preco, tipo)
VALUES
    ('Aventura Amazônica', 'Manaus', 7, 3000.00, 'aventura'),
    ('Luxo em Paris', 'Paris', 5, 8000.00, 'luxo');

-- Inserindo serviços
INSERT INTO servicos (nome, descricao, preco)
VALUES
    ('Translado Aeroporto-Hotel', 'Serviço de translado ida e volta', 200.00),
    ('Passeio de barco', 'Passeio turístico pelos rios locais', 150.00),
    ('Aluguel de Carro', 'Carro disponível por toda a estadia', 500.00);

-- Inserindo contratos (relacionando clientes e pacotes)
INSERT INTO contratos (cliente_id, pacote_id)
VALUES
    (1, 1), -- João Silva contratando Aventura Amazônica
    (2, 2); -- Anna Müller contratando Luxo em Paris

-- Inserindo serviços associados aos contratos
INSERT INTO contrato_servico (contrato_id, servico_id)
VALUES
    (1, 1), -- Contrato 1 (João Silva) com Translado
    (1, 2), -- Contrato 1 (João Silva) com Passeio de barco
    (2, 3); -- Contrato 2 (Anna Müller) com Aluguel de Carro