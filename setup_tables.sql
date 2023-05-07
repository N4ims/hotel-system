-- Create tables

CREATE TABLE `guests` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date COMMENT 'optional',
  `address_id` integer COMMENT 'optional',
  `telephone_number` varchar(255) COMMENT 'optional',
  `mobile_number` varchar(255) COMMENT 'optional',
  `email_address` varchar(255) COMMENT 'optional',
  `member_since` date
);

CREATE TABLE `addresses` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `street_name` varchar(255),
  `street_nr` varchar(255),
  `place` varchar(255),
  `postcode` varchar(255),
  `country` varchar(255)
);

CREATE TABLE `room_bookings` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `guest_id` integer,
  `room_id` integer,
  `from_date` date,
  `to_date` date,
  `number_of_adults` integer,
  `number_of_children` integer COMMENT '4-18 years old',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notes` text COMMENT 'For diets, allergies, special treatment etc.'
);

CREATE TABLE `room_occupations` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `room_booking_id` integer,
  `room_id` integer,
  `check_in` timestamp,
  `check_out` timestamp,
  `deposit` integer,
  `room_left_behind_acceptable` boolean COMMENT 'Occ',
  `notes` text COMMENT 'For notes that come up during the room occupation'
);

CREATE TABLE `rooms` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT COMMENT 'Room number = id',
  `type_id` integer
);

CREATE TABLE `room_types` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `type` varchar(255),
  `description` text,
  `bed_type` varchar(255),
  `number_of_beds` integer,
  `max_persons` integer,
  `base_price` integer,
  `size` integer
);

CREATE TABLE `amenities` (
  `id` integer PRIMARY KEY,
  `name` varchar(255),
  `description` text COMMENT 'optional'
);

CREATE TABLE `room_type_amenities` (
  `id` Integer PRIMARY KEY,
  `room_type_id` Integer,
  `amenity_id` Integer
);

CREATE TABLE `catering_types` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `type` varchar(255),
  `description` varchar(255),
  `price` integer COMMENT 'per day and person'
);

CREATE TABLE `catering_bookings` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `catering_type_id` integer,
  `room_booking_id` integer,
  `from_date` date,
  `to_date` date
);



-- Create foreign key references

ALTER TABLE `guests` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`room_booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `rooms` ADD FOREIGN KEY (`type_id`) REFERENCES `room_types` (`id`);

ALTER TABLE `room_type_amenities` ADD FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`);

ALTER TABLE `room_type_amenities` ADD FOREIGN KEY (`amenity_id`) REFERENCES `amenities` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`catering_type_id`) REFERENCES `catering_types` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`room_booking_id`) REFERENCES `room_bookings` (`id`);



-- Fill tables

INSERT INTO addresses VALUES (2, 'Brentonstreet', "1337", '69111', 'Mannheim', 'Germany');

INSERT INTO guests (id, first_name, last_name, birthdate, address_id, telephone_number, member_since) VALUES (1, 'Günther', 'Hellmann', '1963-05-05', 2, '06623 4466', '2023-04-20');
INSERT INTO guests (id, first_name, last_name, birthdate, address_id, email_address, member_since) VALUES (2, 'Maxima', 'Musterfrau', '1999-06-16', 2, 'maxima.musterfrau@gmail.com', '2023-04-18');

INSERT INTO room_types VALUES (2, 'Doppelzimmer luxus', 'Unser luxuriöses Doppelzimmer', 'king size Boxspringbett', 2, 4, 25000, 45);
INSERT INTO room_types VALUES (1, 'Einzelzimmer nomal', 'Unser kuscheliges Standardzimmer mit einem Einzelbett', 'normales Einzelbett 120cm', 1, 1, 10000, 18);

INSERT INTO rooms VALUES (1, 1);
INSERT INTO rooms VALUES (2, 1);
INSERT INTO rooms VALUES (3, 2);

INSERT INTO amenities VALUES (1, 'Klimaanlage', '');
INSERT INTO amenities VALUES (2, 'Fußbodebheizung', '');

INSERT INTO room_type_amenities VALUES (1, 2, 1);
INSERT INTO room_type_amenities VALUES (2, 2, 2);
INSERT INTO room_type_amenities VALUES (3, 1, 2);

INSERT INTO room_bookings VALUES (1, 2, 1, '2023-05-01', '2023-05-10', 2, 1, '2023-05-01 20:55:20', 'nice customer');
INSERT INTO room_bookings VALUES (2, 1, 1, '2023-06-01', '2023-06-10', 2, 1, '2023-05-01 20:55:20', 'nice customer');

INSERT INTO catering_types VALUES (1, 'Frühstück', 'Frühstück von unserem wundervollen Frühstücksbuffet. Von 7:00 bis 10:30 Uhr.', 12);
INSERT INTO catering_types VALUES (2, 'Halbpension', 'Frühstück und Abendessen. Frühstück von 7:00 Uhr bis 10:30 Uhr. Abendessen von 18:00 Uhr bis 21 Uhr.', 30);
INSERT INTO catering_types VALUES (3, 'Vollpension', 'Frühstück und Abendessen. Frühstück von 7:00 Uhr bis 10:30 Uhr. Mittagessen von 11:30 Uhr bis 14 Uhr. Abendessen von 18:00 Uhr bis 21 Uhr.', 45);
