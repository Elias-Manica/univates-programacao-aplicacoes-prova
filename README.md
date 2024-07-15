CREATE TABLE GASTOS (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    descricao_gasto TEXT NOT NULL,
    valor INT NOT NULL
);
