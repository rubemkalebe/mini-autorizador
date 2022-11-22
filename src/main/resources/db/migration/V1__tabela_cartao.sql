CREATE TABLE cartao (
  car_codigo varchar(36) NOT NULL,
  car_numero varchar(36) NOT NULL,
  car_senha varchar(264) NOT NULL,
  car_data_criacao datetime NOT NULL,
  PRIMARY KEY (car_codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
