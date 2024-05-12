SELECT
    COUNT(`u`.`id`) AS `count`
FROM
    `users` `u`
WHERE
    (CAST(`u`.`created_at` AS DATE) = CURDATE())