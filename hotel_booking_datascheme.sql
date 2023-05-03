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
  `booking_id` integer,
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
  `start_date` date,
  `end_date` date
);

CREATE TABLE `package_types` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `type` varchar(255),
  `description` text,
  `price` integer,
  `amount` integer
);

CREATE TABLE `package_bookings` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `package_type` integer,
  `booking_id` integer,
  `for_date` date COMMENT 'optional',
  `timestamp` timestamp
);

CREATE TABLE `room_cleanings` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `room_id` integer,
  `cleaning_personnel_id` integer,
  `date` date,
  `time` time,
  `cleaning_type_id` integer
);

CREATE TABLE `cleaning_type` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `type` varchar(255),
  `description` text
);

CREATE TABLE `cleaning_personnel` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date COMMENT 'optional',
  `address_id` integer,
  `telephone_number` varchar(255),
  `mobile_number` varchar(255),
  `email_address` varchar(255)
);



ALTER TABLE `guests` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `rooms` ADD FOREIGN KEY (`type_id`) REFERENCES `room_types` (`id`);

ALTER TABLE `room_type_amenities` ADD FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`);

ALTER TABLE `room_type_amenities` ADD FOREIGN KEY (`amenity_id`) REFERENCES `amenities` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`catering_type`) REFERENCES `catering_types` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `package_bookings` ADD FOREIGN KEY (`package_type`) REFERENCES `package_types` (`id`);

ALTER TABLE `package_bookings` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`cleaning_personnel_id`) REFERENCES `cleaning_personnel` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`cleaning_type_id`) REFERENCES `cleaning_type` (`id`);

ALTER TABLE `cleaning_personnel` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
