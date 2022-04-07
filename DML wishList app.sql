USE `wish_list_app`;

-- USERS
INSERT INTO `users` (`user_name`, `user_email`, `user_password`) VALUES ('Peter Falk', 'colombo@mail.com', 'JustOneMoreThing100');
INSERT INTO `users` (`user_name`, `user_email`, `user_password`) VALUES ('John Doe', 'johnDoe@mail.com', 'HotHot1919');
INSERT INTO `users` (`user_name`, `user_email`, `user_password`) VALUES ('Britney Spears', 'britney@mail.com', 'hitMeBaby1MoreTime');

-- WISH LISTS
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (1, 'Jule gaver', 'julen 2022');
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (1, 'Fødselsdags gaver', '66 år');
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (2, 'Dåbs gaver', 'Lille Charlies dåb');
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (2, 'Værtinde gaver', 'Min kone vil gerne bestille værtindegaver til festen på lørdag');
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (3, 'Bryllups gaver', 'Britney og Geroge giftes sommeren 2021');
INSERT INTO `wish_lists` (`user_id`, `title`, `description`) VALUES (3, 'Konfirmations gaver', 'Min datter skal konfirmeres');

-- WISHES
INSERT INTO `wishes` (`wish_list_id`, `title`, `description`, `price`, `url_link`) VALUES (1, 'Slips', 'I en blå farve', 220, 'www.slips.dk/blåslips');
INSERT INTO `wishes` (`wish_list_id`, `title`, `description`, `price`, `url_link`) VALUES (2, 'uld tøj', 'str. 22', 600, 'www.uldtøj.dk');
INSERT INTO `wishes` (`wish_list_id`, `title`, `description`, `price`, `url_link`) VALUES (4, 'Food processor', 'med alt tilbehør', 1999, 'www.imerco.dk');