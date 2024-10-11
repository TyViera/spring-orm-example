CREATE TABLE `roles`
(
    `id`      int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`   varchar(20) NOT NULL DEFAULT '',
    `user_id` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
commit;