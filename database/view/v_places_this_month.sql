SELECT
    COUNT(p.id) AS count
FROM
    places p
WHERE
    MONTH(p.created_at) = MONTH(CURDATE())
  AND YEAR(p.created_at) = YEAR(CURDATE());