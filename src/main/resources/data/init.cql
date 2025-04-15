-- Create a keyspace
CREATE KEYSPACE IF NOT EXISTS my_keyspace WITH REPLICATION = {
    'class': 'SimpleStrategy',
    'replication_factor': 1
};

-- Use the keyspace
USE my_keyspace;

-- Create a table
CREATE TABLE IF NOT EXISTS company (
    id uuid PRIMARY KEY,
    name text,
    address text
);

-- Create a table
CREATE TABLE IF NOT EXISTS employee (
    id uuid PRIMARY KEY,
    inn text,
    companyid uuid,
    firstname text,
    lastname text
);