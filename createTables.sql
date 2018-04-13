-------------------------
-- Table creation & populating scripts
-------------------------------------------------

-------------------------
-- our dear users
-------------------------
CREATE TABLE users
(
  u_id         bigint       PRIMARY KEY,
  u_name	   varchar(12)  UNIQUE NOT NULL,
  u_passwd     varchar(20)  NOT NULL,
  u_score	   bigint	    DEFAULT 0
);

--------------------------
-- the pics
--------------------------
CREATE TABLE pictures
(
  p_id  	bigint       PRIMARY KEY,
  p_desc	text		 NOT NULL,
  p_name 	text         NOT NULL
);


----------------------------
-- the pics uploaded by user
----------------------------
CREATE TABLE user_uploaded_pics
(
  u_id  bigint     REFERENCES users,
  p_id  bigint     REFERENCES pictures
);


------------------------
-- pics seen by user
------------------------

CREATE TABLE user_seen_pics
(
  u_id  bigint     REFERENCES users,
  p_id  bigint     REFERENCES pictures
);

-------------------------------------------------------------
-- populate 'users' table with the user admin, password:admin
-------------------------------------------------------------
INSERT INTO users (u_id, u_name, u_passwd, u_score)
VALUES (0, 'admin', 'admin', 0);

-------------------------------------------------------
-- add 10 pictures to be seen by newly registered users
-------------------------------------------------------
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (1, 'car.jpg', 'car');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (2, 'cloud.jpg', 'cloud');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (3, 'desert.jpg', 'desert');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (4, 'house.jpg', 'house');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (5, 'lightning.jpg', 'lightning');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (6, 'mountains.jpg', 'mountain');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (7, 'galaxy.jpg', 'galaxy');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (8, 'saturn.jpg', 'saturn');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (9, 'train.jpg', 'train');
INSERT INTO pictures (p_id, p_name, p_desc)
VALUES (10, 'tree.jpg', 'tree');
