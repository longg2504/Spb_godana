SELECT
    MONTH(`p`.`created_at`) AS `month`,
    YEAR(`p`.`created_at`) AS `year`,
    COUNT(`p`.`id`) AS `COUNT`
FROM
    `places` `p`
WHERE
    ((`p`.`created_at` BETWEEN CAST((CURDATE() - INTERVAL 5 MONTH) AS DATETIME) AND (CAST((CURDATE() - INTERVAL -(1) DAY) AS DATETIME) - INTERVAL 0 MINUTE))
  AND (`p`.`deleted` = FALSE))
GROUP BY MONTH(`p`.`created_at`) , YEAR(`p`.`created_at`)
ORDER BY YEAR(`p`.`created_at`) , MONTH(`p`.`created_at`)