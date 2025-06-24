CREATE TABLE IF NOT EXISTS "CONSUMPTIONS" (
    "ID"                    UUID PRIMARY KEY,
    "TIMESTAMP"             TIMESTAMP,
    "TOTAL_CONSUMPTION_KWH" NUMERIC,
    "CREATED_AT"            TIMESTAMP,
    "UPDATED_AT"            TIMESTAMP,
    "CREATED_BY"            VARCHAR,
    "UPDATED_BY"            VARCHAR,
    "VERSION"               INTEGER
);

CREATE TABLE IF NOT EXISTS "PRODUCTIONS" (
    "ID"                    UUID PRIMARY KEY,
    "TIMESTAMP"             TIMESTAMP,
    "TOTAL_PRODUCTION_KWH"  NUMERIC,
    "CREATED_AT"            TIMESTAMP,
    "UPDATED_AT"            TIMESTAMP,
    "CREATED_BY"            VARCHAR,
    "UPDATED_BY"            VARCHAR,
    "VERSION"               INTEGER
);

CREATE TABLE IF NOT EXISTS "PRODUCTION_DETAILS" (
    "ID"                    UUID PRIMARY KEY,
    "PRODUCTION_ID"         UUID,
    "SOURCE_TYPE"           VARCHAR,
    "PRODUCTION_KWH"        NUMERIC,
    "CREATED_AT"            TIMESTAMP,
    "UPDATED_AT"            TIMESTAMP,
    "CREATED_BY"            VARCHAR,
    "UPDATED_BY"            VARCHAR,
    "VERSION"               INTEGER,
    CONSTRAINT FK_PRODUCTIONS_PRODUCTION_DETAILS FOREIGN KEY ("PRODUCTION_ID") REFERENCES "PRODUCTIONS" ("ID")
);

CREATE TABLE IF NOT EXISTS "WEATHER" (
    "ID"                        UUID PRIMARY KEY,
    "TIMESTAMP"                 TIMESTAMP,
    "TEMPERATURE_CELSIUS"       NUMERIC,
    "CLOUDCOVER_DECIMAL"        NUMERIC,
    "WINDSPEED_IN_SECOND"       NUMERIC,
    "UV_INDEX"                  NUMERIC,
    "ATMOSPHERIC_PRESSURE_HPA"  NUMERIC,
    "AVERAGE_VISIBILITY"        NUMERIC,
    "SOLAR_IRRADIANCE_WM2"      NUMERIC,
    "SUNRISE_TIME"              TIMESTAMP,
    "SUNSET_TIME"               TIMESTAMP,
    "CREATED_AT"                TIMESTAMP,
    "UPDATED_AT"                TIMESTAMP,
    "CREATED_BY"                VARCHAR,
    "UPDATED_BY"                VARCHAR,
    "VERSION"                   INTEGER
);

CREATE TABLE IF NOT EXISTS "ENERGY_DETAILS" (
    "ID"                                    UUID PRIMARY KEY,
    "TIMESTAMP"                             TIMESTAMP,
    "CONSUMPTION_KWH"                       NUMERIC,
    "PRODUCTION_KWH"                        NUMERIC,
    "SOLAR_PRODUCTION_KWH"                  NUMERIC,
    "NET_BALANCE_KWH"                       NUMERIC,
    "TEMPERATURE_CELSIUS"                   NUMERIC,
    "CLOUD_COVER_DECIMAL"                    NUMERIC,
    "WIND_SPEED_IN_SECOND"                   NUMERIC,
    "UV_INDEX"                              NUMERIC,
    "SOLAR_IRRADIANCE_WM2"                  NUMERIC,
    "SOLAR_IRRADIANCE_VS_SOLAR_PRODUCTION"  NUMERIC,
    "TEMPERATURE_VS_ENERGY_CONSUMPTION"     NUMERIC,
    "CREATED_AT"                            TIMESTAMP,
    "UPDATED_AT"                            TIMESTAMP,
    "CREATED_BY"                            VARCHAR,
    "UPDATED_BY"                            VARCHAR,
    "VERSION"                               INTEGER
);
