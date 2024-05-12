SELECT
    MONTH(`u`.`created_at`) AS `month`,
    YEAR(`u`.`created_at`) AS `year`,
    COUNT(`u`.`id`) AS `COUNT`
FROM
    `users` `u`
WHERE
    ((`u`.`created_at` BETWEEN CAST((CURDATE() - INTERVAL 5 MONTH) AS DATETIME) AND (CAST((CURDATE() - INTERVAL -(1) DAY) AS DATETIME) - INTERVAL 0 MINUTE))
  AND (`u`.`deleted` = FALSE))
GROUP BY MONTH(`u`.`created_at`) , YEAR(`u`.`created_at`)
ORDER BY YEAR(`u`.`created_at`) , MONTH(`u`.`created_at`)