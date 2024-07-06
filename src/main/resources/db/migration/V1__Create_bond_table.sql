CREATE TABLE IF NOT EXISTS bond (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    market VARCHAR(10) NOT NULL,
    maturity_at DATE,
    coupon INTEGER,
    last_price INTEGER,
    country VARCHAR(10) NOT NULL,
    region VARCHAR(100) NOT NULL,
    created_at DATE NOT NULL,
    last_modified_at DATE NOT NULL
);