INSERT INTO country(id, name) VALUES (1, 'Brazil');

INSERT INTO address(id, street, city, state, countryId) VALUES (1, 'Rua Carneiro Lobo', 'Curitiba', 'Parana', 1);

INSERT INTO customer(id, birthDate, email, fullName, password, phoneNumber, username, addressId) VALUES (1000, NULL, NULL, 'Terumi Isobe', 'password', NULL, 'terumiisobe', 1);
INSERT INTO customer(id, birthDate, email, fullName, password, phoneNumber, username, addressId) VALUES (1001, NULL, NULL, 'João Grams', 'password', NULL, 'joaograms', 1);

INSERT INTO author(id, fullName) VALUES (1, 'Patrick Rothfuss');
INSERT INTO author(id, fullName) VALUES (2, 'George R. R. Martin');
INSERT INTO author(id, fullName) VALUES (3, 'J. R. R. Tolkien');
INSERT INTO author(id, fullName) VALUES (4, 'John Green');
INSERT INTO author(id, fullName) VALUES (5, 'Stephen King');

INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (1, 'The Name of the Wind', 1,'SCIENCE FICTION', NULL, CURRENT_TIMESTAMP, 80, 1500);
INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (2, 'The Game of Thrones', 2,'SCIENCE FICTION', NULL, CURRENT_TIMESTAMP, 90, 1000);
INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (3, 'Lord of the Rings', 3,'SCIENCE FICTION', NULL, CURRENT_TIMESTAMP, 80, 500);
INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (4, 'The Fault in our Stars', 4, 'ROMANCE', NULL, CURRENT_TIMESTAMP, 50, 200);
INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (5, 'Paper Towns', 4, 'ROMANCE', NULL, CURRENT_TIMESTAMP, 30, 300);
INSERT INTO item (id, title, authorId, subject, publisher, publicationDate, cost, availability) VALUES (6, 'It', 5, 'HORROR', NULL, CURRENT_TIMESTAMP, 70, 1000);
