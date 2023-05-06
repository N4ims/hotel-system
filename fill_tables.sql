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

INSERT INTO room_amenities VALUES (1, 2, 1);
INSERT INTO room_amenities VALUES (2, 2, 2);
INSERT INTO room_amenities VALUES (3, 1, 2);

INSERT INTO room_bookings VALUES (1, 2, 1, '2023-05-01', '2023-05-10', 2, 1, '2023-05-01 20:55:20', 'nice customer');
INSERT INTO room_bookings VALUES (2, 1, 1, '2023-06-01', '2023-06-10', 2, 1, '2023-05-01 20:55:20', 'nice customer');

INSERT INTO package_types VALUES (1, 'Willkommens Weinpaket rot', 'Weinpaket das Gäste zu unserer Sommeraktion bekommen. Besteht aus: 1x Dornfelder trocken 2020 0,75l von Weingut Merlot', '0', '1');
INSERT INTO package_types VALUES (2, 'Willkommens Weinpaket rot', 'Weinpaket das Gäste zu unserer Sommeraktion bekommen. Besteht aus: 1x Riesling halbtrocken 2021 0,75l von Weingut Merlot', '0', '1');

INSERT INTO catering_types VALUES (1, 'Frühstück', 'Frühstück von unserem wundervollen Frühstücksbuffet. Von 7:00 bis 10:30 Uhr.', 12);
INSERT INTO catering_types VALUES (2, 'Halbpension', 'Frühstück und Abendessen. Frühstück von 7:00 Uhr bis 10:30 Uhr. Abendessen von 18:00 Uhr bis 21 Uhr.', 30);
INSERT INTO catering_types VALUES (3, 'Vollpension', 'Frühstück und Abendessen. Frühstück von 7:00 Uhr bis 10:30 Uhr. Mittagessen von 11:30 Uhr bis 14 Uhr. Abendessen von 18:00 Uhr bis 21 Uhr.', 45);
