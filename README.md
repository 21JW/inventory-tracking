# Inventory Tracking

## Prerequisites
* Java 8
* MySQL 8.0.15 or higher version
* Maven

## Technologies used
* Spring Boot 2.2.2
* MyBatis

## How to run this project
1. Clone this project in IntelliJ IDEA;
2. Modify `\src\main\resources\application.properties`: replace `spring.datasource.username` and `spring.datasource.password` with your MySQL's username and password respectively;
3. Make sure there is no database called `inventory` in MySQL;
4. Run `\src\main\java\com\shopify\inventory\InventoryApplication.java` (tables will be created automatically).

## Tables

Three tables are created for this project, which are **item**, **inventory_in** and **inventory_out**.

### item

this table stores the basic information of items

| Field | Type | Key | Nullable | Unique | Note |
| :--- | :--- | :--- | :--- | :--- | :--- |
| id | char(36) | Yes | | Yes | primary key |
| name | varchar(255) | | | | |
| code | varchar(20) | | | Yes | each item has a unique code |
| note | varchar(255) | | Yes | | |
| created_time | datetime | | | | the time that this row was inserted |
| ts | datetime | | | | the last time that created or modified this row |

### inventory_in

this table stores the records of items that come into the inventory

| Field | Type | Key | Nullable | Unique | Note |
| :--- | :--- | :--- | :--- | :--- | :--- |
| id | char(36) | Yes | | Yes | primary key |
| item_code | varchar(20) | | | | code of item |
| initial_num | decimal(12,2) | | | | number of items that came into the inventory initially |
| available_num | decimal(12,2) | | | | number of items that are still available in the inventory |
| unit | varchar(20) | | | | unit of items (kg, box etc.)
| cost | decimal(12,2) | | | | cost of these items |
| currency | varchar(20) | | | | currency of the cost (CAD, USD etc.) |
| note | varchar(20) | | Yes | | |
| created_time | datetime | | | | the time that this row was inserted |
| ts | datetime | | | | the last time that created or modified this row |

### inventory_out

this table stores the records of items that come out of the inventory

| Field | Type | Key | Nullable | Unique | Note |
| :--- | :--- | :--- | :--- | :--- | :--- |
| id | char(36) | Yes | | Yes | primary key |
| inventory_in_id | char(36) | | | | id of inventory_in |
| num | decimal(12,2) | | | | number of items that came out of the inventory |
| note | varchar(255) | | Yes | | |
| created_time | datetime | | | | the time that this row was inserted |
| ts | datetime | | | | the last time that created or modified this row |

## Logic

### inventory_in

Before **inserting** an inventory_in record, back-end should check:
1. whether item_code exists or not; ✔
2. whether all fields except note are not null and valid. ✔

For **editing** an inventory_in record, only unit, currency and note are allowed to be modified since it is improper to modify initial_num and cost. ✔

Before **deleting** an inventory_in record, back-end should check whether this record is referenced in inventory_out or not. If yes, this record could not be deleted. ✔

### inventory_out

When **inserting** an inventory_out record, back-end should:
1. check whether inventory_in_id exists or not; ✔
2. check whether num is less than the available_num of the corresponding record in inventory_in; ✔
3. decrease the available_num of the corresponding record in inventory_in. ✔

For **editing** an inventory_out record, only note is allowed to be modified. ✔

After **deleting** an inventory_out record, back-end should restore the corresponding row in inventory_in to a valid state. ✔

## APIs

API examples are given in `\api-examples`. Here are a few notes:

1. **Pushing a button export product data to a CSV** is chosen as the additional feature. CSV files will be stored in `\csv`;
2. For listing inventories, two APIs are provided: one simply lists all inventories, while the other one divides them into pages which is more popular in the real world.