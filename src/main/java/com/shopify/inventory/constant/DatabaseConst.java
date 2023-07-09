package com.shopify.inventory.constant;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

public class DatabaseConst
{
    public static final String CHECK_DB_EXIST_SQL = "SELECT count(SCHEMA_NAME) as COUNT FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='inventory'";

    public static final String COUNT_COLUMN = "COUNT";

    public static final String SQL_PATH = "sql/inventory.sql";
}
