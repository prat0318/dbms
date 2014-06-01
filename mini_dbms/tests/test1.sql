open "don";
.
script "../tests/test1.dml";
.
select * from item_sale where item_sold>5 and cid<=4500;
.
select c_name from customers where c_id=4009;
.
select item_name from item_master where item_id=9999;
.
select * from item_master where item_id = 1;
.
update item_master set item_name="aaa" where item_id=1;
.
commit;
.
select * from item_master where item_id = 1;
.
update item_master set item_name="bbb" where item_id=1;
.
select * from item_master where item_id = 1;
.
abort;
.
select * from item_master where item_id = 1;
.
delete item_sale where cid=4999;
.
select item_master.item_name,customers.c_name,item_sale.item_sold
from customers,item_master,item_sale
where customers.c_id=item_sale.cid and item_master.item_id=item_sale.itemid and item_sale.cid=4009;
.
update item_master set item_name="temp" where item_id>1;
.
select item_master.item_name,customers.c_name,item_sale.item_sold
from customers,item_master,item_sale
where customers.c_id=item_sale.cid and item_master.item_id=item_sale.itemid and item_sale.cid=4009;
.
commit;
.
close;
.
