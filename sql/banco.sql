DROP DATABASE IF EXISTS agencia_viagens;
CREATE DATABASE agencia_viagens;
USE agencia_viagens;

-- Tabela de clientes
CREATE TABLE clientes (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100),
                          tipo ENUM('nacional', 'estrangeiro'),
                          cpf VARCHAR(14) NULL,
                          passaporte VARCHAR(20) NULL,
                          telefone VARCHAR(20),
                          email VARCHAR(100)
);

-- Tabela de pacotes
CREATE TABLE pacotes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100),
                         destino VARCHAR(100),
                         duracao INT,
                         preco DECIMAL(10,2),
                         tipo VARCHAR(50)
);

-- Tabela de serviços adicionais
CREATE TABLE servicos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100),
                          descricao TEXT,
                          preco DECIMAL(10,2)
);

-- Contratos entre clientes e pacotes
CREATE TABLE contratos (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           cliente_id INT,
                           pacote_id INT,
                           FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                           FOREIGN KEY (pacote_id) REFERENCES pacotes(id) ON DELETE CASCADE
);

-- Relacionamento entre contratos e serviços adicionais
CREATE TABLE contrato_servico (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  contrato_id INT,
                                  servico_id INT,
                                  FOREIGN KEY (contrato_id) REFERENCES contratos(id) ON DELETE CASCADE,
                                  FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
);

-- Inserindo clientes
INSERT INTO clientes (nome, tipo, cpf, telefone, email) VALUES
                                                            ('João Silva', 'nacional', '12345678900', '11999999999', 'joao@gmail.com'),
                                                            ('Maria Oliveira', 'nacional', '98765432100', '61988888888', 'maria@gmail.com');

INSERT INTO clientes (nome, tipo, passaporte, telefone, email) VALUES
                                                                   ('Anna Müller', 'estrangeiro', 'DE1234567', '+49 1523456789', 'anna@email.de'),
                                                                   ('John Smith', 'estrangeiro', 'US9876543', '+1 2025550181', 'john@usa.com');

-- Inserindo pacotes
INSERT INTO pacotes (nome, destino, duracao, preco, tipo) VALUES
                                                              ('Aventura Amazônica', 'Manaus', 7, 3000.00, 'aventura'),
                                                              ('Luxo em Paris', 'Paris', 5, 8500.00, 'luxo'),
                                                              ('Turismo Histórico em Roma', 'Roma', 6, 5200.00, 'cultural'),
                                                              ('Praias do Nordeste', 'Natal', 8, 4000.00, 'praia');

-- Inserindo serviços adicionais
INSERT INTO servicos (nome, descricao, preco) VALUES
                                                  ('Translado Aeroporto-Hotel', 'Serviço de translado ida e volta', 200.00),
                                                  ('Passeio de barco', 'Passeio turístico pelos rios locais', 150.00),
                                                  ('Aluguel de Carro', 'Carro disponível durante toda a estadia', 500.00),
                                                  ('Guia Turístico', 'Guia fluente em português e inglês', 300.00);

-- Inserindo contratos
INSERT INTO contratos (cliente_id, pacote_id) VALUES
                                                  (1, 1), -- João → Aventura Amazônica
                                                  (2, 4), -- Maria → Praias do Nordeste
                                                  (3, 2), -- Anna → Luxo em Paris
                                                  (4, 3); -- John → Roma

-- Inserindo serviços contratados
INSERT INTO contrato_servico (contrato_id, servico_id) VALUES
                                                           (1, 1),
                                                           (1, 2),
                                                           (2, 3),
                                                           (3, 1),
                                                           (3, 4),
                                                           (4, 4);
