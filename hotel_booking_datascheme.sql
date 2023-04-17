CREATE TABLE `guests` (
  `id` integer PRIMARY KEY,
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
  `id` integer PRIMARY KEY,
  `street_name` varchar(255),
  `street_nr` varchar(255),
  `place` varchar(255),
  `postcode` varchar(255),
  `country` varchar(255)
);

CREATE TABLE `room_bookings` (
  `id` integer PRIMARY KEY,
  `guest_id` integer,
  `room_id` integer,
  `from_date` date,
  `to_date` date,
  `number_of_adults` integer,
  `number_of_children` integer COMMENT '4-18 years old',
  `timestamp` timestamp NOT NULL DEFAULT "now()",
  `notes` text COMMENT 'For diets, allergies, special treatment etc.'
);

CREATE TABLE `room_occupations` (
  `id` integer PRIMARY KEY,
  `booking_id` integer,
  `room_id` integer,
  `check_in` timestamp,
  `check_out` timestamp,
  `deposit` integer,
  `room_left_behind_acceptable` boolean COMMENT 'Occ',
  `notes` text COMMENT 'For notes that come up during the room occupation'
);

CREATE TABLE `rooms` (
  `id` integer PRIMARY KEY,
  `type_id` integer,
  `extra_price_per_adult` integer,
  `extra_price_per_children` integer
);

CREATE TABLE `room_types` (
  `id` integer PRIMARY KEY,
  `type` varchar(255),
  `description` text,
  `bed_type` varchar(255),
  `number_of_beds` integer,
  `max_persons` integer,
  `base_price` integer
);

CREATE TABLE `catering_types` (
  `id` integer PRIMARY KEY,
  `type` varchar(255),
  `description` varchar(255),
  `price` integer COMMENT 'per day and person'
);

CREATE TABLE `catering_bookings` (
  `id` integer,
  `catering_type` integer,
  `booking_id` integer,
  `start_date` date,
  `end_date` date
);

CREATE TABLE `package_types` (
  `id` integer PRIMARY KEY,
  `type` varchar(255),
  `description` text,
  `price` integer,
  `amount` integer
);

CREATE TABLE `package_bookings` (
  `id` integer,
  `package_type` integer,
  `booking_id` integer,
  `for_date` date COMMENT 'optional',
  `timestamp` timestamp
);

CREATE TABLE `room_cleanings` (
  `id` integer PRIMARY KEY,
  `room_id` integer,
  `cleaning_personnel_id` integer,
  `date` date,
  `time` time,
  `cleaning_type_id` integer
);

CREATE TABLE `cleaning_type` (
  `id` integer PRIMARY KEY,
  `type` varchar(255),
  `description` text
);

CREATE TABLE `cleaning_personnel` (
  `id` integer PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date COMMENT 'optional',
  `address_id` integer,
  `telephone_number` varchar(255),
  `mobile_number` varchar(255),
  `email_address` varchar(255)
);

CREATE TABLE `users` (
  `to_be_discussed` integer PRIMARY KEY
);

CREATE TABLE `courses` (
  `id` integer PRIMARY KEY,
  `name` varchar(255),
  `description` text,
  `price` varchar(255),
  `tutor_id` varchar(255)
);

CREATE TABLE `course_appointments` (
  `id` integer PRIMARY KEY,
  `course_id` integer,
  `date` date
);

CREATE TABLE `course_bookings` (
  `guest_id` integer,
  `course_appointment_id` integer,
  `amount` integer,
  `price` integer,
  PRIMARY KEY (`guest_id`, `course_appointment_id`)
);

CREATE TABLE `tutors` (
  `id` integer PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date COMMENT 'optional',
  `address_id` integer,
  `telephone_number` varchar(255),
  `mobile_number` varchar(255),
  `email_address` varchar(255)
);

CREATE TABLE `payments` (
  `id` integer PRIMARY KEY,
  `guest_id` integer,
  `payment_type` varchar(255),
  `description` text,
  `timestamp` timestamp NOT NULL DEFAULT "now()",
  `amount` integer,
  `fee` integer
);

CREATE TABLE `guest_fees` (
  `id` integer PRIMARY KEY,
  `booking_id` integer,
  `type` varchar(255),
  `description` integer,
  `amount` integer
);

CREATE TABLE `parking_spots` (
  `to_be_designed` integer PRIMARY KEY
);

CREATE TABLE `parking_spot_bookings` (
  `to_be_designed` integer PRIMARY KEY
);

ALTER TABLE `guests` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_occupations` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `rooms` ADD FOREIGN KEY (`type_id`) REFERENCES `room_types` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`catering_type`) REFERENCES `catering_types` (`id`);

ALTER TABLE `catering_bookings` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `package_bookings` ADD FOREIGN KEY (`package_type`) REFERENCES `package_types` (`id`);

ALTER TABLE `package_bookings` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`cleaning_personnel_id`) REFERENCES `cleaning_personnel` (`id`);

ALTER TABLE `room_cleanings` ADD FOREIGN KEY (`cleaning_type_id`) REFERENCES `cleaning_type` (`id`);

ALTER TABLE `cleaning_personnel` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
