SELECT
    COUNT(p.id) AS count
FROM
    places p
WHERE
    DATE(p.created_at) = CURDATE();