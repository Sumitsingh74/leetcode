# Write your MySQL query statement below
with cte as 
    (select product_id, new_price, change_date,
        rank()over(partition by product_id order by change_date desc) as rk
    from products
    where date(change_date)<='2019-08-16')

select distinct products.product_id, ifnull(cte.new_price,10) as price
from products left join cte 
on products.product_id = cte.product_id and rk=1