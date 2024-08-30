CREATE TABLE IF NOT EXISTS job (
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    last_execution_date DATETIME,
    next_execution_date DATETIME NOT NULL,
    data TEXT,
    created_at DATETIME NOT NULL,
    last_modified_at DATETIME NOT NULL
);