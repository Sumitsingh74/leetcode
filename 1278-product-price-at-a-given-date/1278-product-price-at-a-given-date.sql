# Write your MySQL query statement below
with ac as(
    select product_id, new_price, change_date,
    rank()over(partition by product_id order by change_date desc) as rk
    from products
    where date(change_date)<='2019-08-16'
)
select distinct p.product_id , ifnull(ac.new_price,10) as price
from products p left join ac
on p.product_id = ac.product_id and rk=1