CREATE TABLE cartao (
  car_codigo varchar(36) NOT NULL,
  car_numero varchar(36) NOT NULL,
  car_senha varchar(264) NOT NULL,
  car_data_criacao datetime NOT NULL,
  car_saldo DECIMAL(13,2),
  PRIMARY KEY (car_codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
