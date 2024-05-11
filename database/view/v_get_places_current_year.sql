SELECT
    MONTH(p.created_at) AS month,
    COUNT(p.id) AS count
FROM
    places AS p
WHERE
    YEAR(p.created_at) = YEAR(CURDATE())
GROUP BY
    MONTH(p.created_at)
ORDER BY
    month;