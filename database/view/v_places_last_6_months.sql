SELECT
    MONTH(`b`.`created_at`) AS `month`,
    YEAR(`b`.`created_at`) AS `year`,
    COUNT(`b`.`id`) AS `COUNT`
FROM
    `places` `b`
WHERE
    ((`b`.`created_at` BETWEEN CAST((CURDATE() - INTERVAL 5 MONTH) AS DATETIME) AND (CAST((CURDATE() - INTERVAL -(1) DAY) AS DATETIME) - INTERVAL 0 MINUTE))
  AND (`b`.`deleted` = FALSE))
GROUP BY MONTH(`b`.`created_at`) , YEAR(`b`.`created_at`)
ORDER BY YEAR(`b`.`created_at`) , MONTH(`b`.`created_at`)