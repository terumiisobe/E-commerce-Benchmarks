
-- Reconstruction
--INSERT INTO UltrasoundImage (userId, fileReference, reconstructionStartTime, reconstructionEndTime, size, iterationsPerformed) VALUES (1, 'filename', CURRENT_DATE, CURRENT_DATE, 1024, 50);
--INSERT INTO User (userId, username) VALUES (1, 'fenix');

--INSERT INTO Item(id, title, author, publisher, cost, availability, timesSold)
--VALUES (1L, 'The Name of the Wind', 'Patrick Rothfuss', 'Sextante', 80, 150L, 0L);
--VALUES (2L, 'The Game of Thrones', 'George R. R. Martin', 'Sextante', 90, 200L, 0L);
--VALUES (3L, 'Lord of the Rings', 'J. R. R. Tolkien', 'Sextante', 50, 130L, 0L);


INSERT INTO Author
(id, fullName)
VALUES(1L, 'Patrick Rothfuss');

INSERT INTO Item
(id, availability, cost, publisher, timesSold, title, authorId)
VALUES(1L, 150L, 80, 'Sextante', 0L, 'The Name of the Wind', 1L);
