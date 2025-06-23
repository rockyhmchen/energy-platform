CREATE TABLE IF NOT EXISTS "consumptions" (
    "id"                    uuid PRIMARY KEY,
    "timestamp"             TIMESTAMP,
    "total_consumption_kwh" NUMERIC,
    "created_at"            TIMESTAMP,
    "updated_at"            TIMESTAMP,
    "created_by"            VARCHAR,
    "updated_by"            VARCHAR,
    "version"               INTEGER
);

CREATE TABLE IF NOT EXISTS "energy_details" (
    "id"                    uuid PRIMARY KEY,
    "timestamp"             TIMESTAMP,
    "consumption_kwh"       NUMERIC,
    "production_kwh"        NUMERIC,
    "solar_production_kwh"  NUMERIC,
    "created_at"            TIMESTAMP,
    "updated_at"            TIMESTAMP,
    "created_by"            VARCHAR,
    "updated_by"            VARCHAR,
    "version"               INTEGER
);

