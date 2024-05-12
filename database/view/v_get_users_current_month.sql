SELECT
    COUNT(`u`.`id`) AS `count`
FROM
    `users` `u`
WHERE
    ((MONTH(`u`.`created_at`) = MONTH(CURDATE()))
        AND (YEAR(`u`.`created_at`) = YEAR(CURDATE())))