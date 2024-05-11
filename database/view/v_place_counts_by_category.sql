SELECT
    c.title AS categoryName,
    COUNT(p.id) AS count
FROM
    categories c
    LEFT JOIN
    places p ON c.id = p.category_id
GROUP BY
    c.title;