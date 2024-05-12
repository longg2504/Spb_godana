SELECT
    MONTH(`u`.`created_at`) AS `month`,
    COUNT(`u`.`id`) AS `count`
FROM
    `users` `u`
WHERE
    (YEAR(`u`.`created_at`) = YEAR(CURDATE()))
GROUP BY MONTH(`u`.`created_at`)
ORDER BY `month`