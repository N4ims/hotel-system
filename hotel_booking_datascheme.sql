CREATE TABLE `customers` (
  `id` integer PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date,
  `address_id` integer COMMENT 'foreign key',
  `telephone_number` varchar(255),
  `mobile_number` varchar(255),
  `email_address` varchar(255)
);

CREATE TABLE `customer_fees` (
  `id` integer PRIMARY KEY,
  `booking_id` integer,
  `type` varchar(255),
  `description` integer,
  `amount` integer
);

CREATE TABLE `booking_preferences` (
  `booking_id` integer,
  `person_nr` integer,
  `diet` varchar(255),
  `allergies` varchar(255),
  PRIMARY KEY (`booking_id`, `person_nr`)
);

CREATE TABLE `addresses` (
  `id` integer PRIMARY KEY,
  `street` varchar(255),
  `street_nr` varchar(255),
  `place` varchar(255),
  `post_code` varchar(255),
  `country` varchar(255)
);

CREATE TABLE `room_bookings` (
  `id` integer PRIMARY KEY,
  `room_id` integer,
  `check_in_date` date,
  `check_out_date` date,
  `number_of_adults` integer,
  `number_of_children` integer,
  `number_of_infants` integer,
  `booked_parking_spots` integer,
  `timestamp` timestamp NOT NULL DEFAULT "now()",
  `deposit` integer,
  `deposit_ispayed` boolean,
  `deposit_isregained` boolean,
  `checked_out_on_time` boolean,
  `room_left_behind_acceptable` boolean,
  `notes` varchar(255)
);

CREATE TABLE `packages` (
  `id` integer PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `price_per_day` integer
);

CREATE TABLE `package_bookings` (
  `id` integer PRIMARY KEY,
  `package_id` integer,
  `booking_id` integer,
  `start_date` date,
  `end_date` date
);

CREATE TABLE `payments` (
  `id` integer PRIMARY KEY,
  `customer_id` integer,
  `payment_type` varchar(255),
  `description` text,
  `timestamp` timestamp NOT NULL DEFAULT "now()",
  `amount` integer,
  `fee` integer
);

CREATE TABLE `rooms` (
  `number` integer PRIMARY KEY,
  `type` varchar(255),
  `description` text,
  `bed_type` varchar(255),
  `number_of_beds` integer,
  `max_persons` integer,
  `basic_price` integer,
  `extra_price_per_adult` integer,
  `extra_price_per_children` integer
);

CREATE TABLE `additional_configurations` (
  `id` integer PRIMARY KEY,
  `booking_id` integer,
  `type` varchar(255),
  `description` text,
  `price` integer,
  `amount` integer
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
  `customer_id` integer,
  `course_appointment_id` integer,
  `amount` integer,
  `price` integer,
  PRIMARY KEY (`customer_id`, `course_appointment_id`)
);

CREATE TABLE `tutors` (
  `id` integer PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `birthdate` date COMMENT 'optional',
  `address_id` integer,
  `telephone_number` integer,
  `mobile_number` integer,
  `email_address` varchar(255)
);

ALTER TABLE `customers` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

ALTER TABLE `customer_fees` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `booking_preferences` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `room_bookings` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`number`);

ALTER TABLE `payments` ADD FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE `additional_configurations` ADD FOREIGN KEY (`booking_id`) REFERENCES `room_bookings` (`id`);

ALTER TABLE `courses` ADD FOREIGN KEY (`tutor_id`) REFERENCES `tutors` (`id`);

ALTER TABLE `course_appointments` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`);

ALTER TABLE `course_bookings` ADD FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE `course_bookings` ADD FOREIGN KEY (`course_appointment_id`) REFERENCES `course_appointments` (`id`);

ALTER TABLE `tutors` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
