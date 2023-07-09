CREATE DATABASE inventory;

USE inventory;

CREATE TABLE `item`  (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `code` varchar(20) NOT NULL,
  `note` varchar(255),
  `created_time` datetime(0) NOT NULL,
  `ts` datetime(0) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `inventory_in`  (
  `id` char(36) NOT NULL,
  `item_code` varchar(20) NOT NULL,
  `initial_num` DECIMAL(12,2) NOT NULL,
  `available_num` DECIMAL(12,2) NOT NULL,
  `unit` varchar(20) NOT NULL,
  `cost` DECIMAL(12,2) NOT NULL,
  `currency` varchar(20) NOT NULL,
  `note` varchar(255),
  `created_time` datetime(0) NOT NULL,
  `ts` datetime(0) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `inventory_out`  (
  `id` char(36) NOT NULL,
  `inventory_in_id` char(36) NOT NULL,
  `num` DECIMAL(12,2) NOT NULL,
  `note` varchar(255),
  `created_time` datetime(0) NOT NULL,
  `ts` datetime(0) NOT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('2438579a-b9ec-4bf8-a88d-aa1d3fdce7b4', 'item 6', 'A0006', null, '2022-01-18 15:01:50', '2022-01-18 15:01:50');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('27b3d93d-045a-407c-a191-85ea771a1c16', 'item 7', 'A0007', null, '2022-01-18 15:01:57', '2022-01-18 15:01:57');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('5e59e578-a64a-4cf2-909e-765a44f24e5b', 'item 10', 'A0010', null, '2022-01-18 15:02:17', '2022-01-18 15:02:17');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('61fc733a-7f61-413c-9d11-49db0177927b', 'item 9', 'A0009', null, '2022-01-18 15:02:10', '2022-01-18 15:02:10');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('6fc54314-2e4b-4764-be69-755dda917497', 'item 11', 'A0011', null, '2022-01-18 15:02:24', '2022-01-18 15:02:24');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('8dc8e048-d14f-4c1f-b549-d7ef052bb111', 'item 3', 'A0003', null, '2022-01-15 21:36:15', '2022-01-15 21:36:15');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('a1047951-6ec0-4418-a035-ff30be48e503', 'item 2', 'A0002', null, '2022-01-15 21:36:08', '2022-01-15 21:36:08');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('b6856a86-ab1c-40fb-ae01-75b2a981b0d1', 'item 4', 'A0004', null, '2022-01-15 21:36:25', '2022-01-15 21:36:25');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('baa036ee-cc25-4bf6-9f3b-91b05874c981', 'item 8', 'A0008', null, '2022-01-18 15:02:04', '2022-01-18 15:02:04');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('c4d869a9-8f81-452b-91c4-35e6a2bff014', 'item 5', 'A0005', null, '2022-01-15 21:36:32', '2022-01-15 21:36:32');
INSERT INTO item (id, name, code, note, created_time, ts) VALUES ('d6e73625-02aa-40ae-84f7-dc1848c7c07a', 'item 1', 'A0001', null, '2022-01-15 21:35:29', '2022-01-15 21:35:29');
