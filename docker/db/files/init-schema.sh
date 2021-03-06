#!/bin/bash

set -e

DB_NAME=$1
SCHEMA_NAME=$2
PASSWORD=$3

psql -U admin -tc "SELECT 1 FROM pg_database WHERE datname = '${DB_NAME}'" | grep -q 1 || psql -U admin -c "CREATE DATABASE ${DB_NAME}"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
\c ${DB_NAME};

CREATE SCHEMA IF NOT EXISTS ${SCHEMA_NAME};

DO \$\$DECLARE count int;
BEGIN
  SELECT count(*) INTO count FROM pg_roles WHERE rolname = '${SCHEMA_NAME}';
  IF count > 0 THEN
    EXECUTE 'REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA ${SCHEMA_NAME} FROM ${SCHEMA_NAME}';
    EXECUTE 'REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA ${SCHEMA_NAME} FROM ${SCHEMA_NAME}';
    EXECUTE 'REVOKE ALL PRIVILEGES ON SCHEMA ${SCHEMA_NAME} FROM ${SCHEMA_NAME}';
  END IF;
END\$\$;
DROP USER IF EXISTS ${SCHEMA_NAME};
CREATE USER ${SCHEMA_NAME} WITH password '${PASSWORD}';
GRANT ALL PRIVILEGES ON SCHEMA ${SCHEMA_NAME} TO ${SCHEMA_NAME};
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA ${SCHEMA_NAME} TO ${SCHEMA_NAME};
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA ${SCHEMA_NAME} TO ${SCHEMA_NAME};
EOSQL