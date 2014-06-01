open "don/mdb.database";
.
create table customers(cid int,cname str,cloc str);
.
create table products(pid int,pname str,price int);
.
create table orders(c_id int,p_id int,qty int);
.
insert into customers  values (1,"blad","houston");
.
insert into customers  values (2,"glen","houston");
.
insert into customers  values (3,"guy","charlotte");
.
insert into customers  values (4,"chas","columbus");
.
insert into customers  values (5,"annie","boston");
.
insert into customers  values (6,"don","phoenix");
.
insert into customers  values (7,"eric","austin");
.
insert into customers  values (8,"craig","columbus");
.
insert into customers  values (9,"petra","charlotte");
.
insert into customers  values (10,"john","memphis");
.
insert into customers  values (11,"vincent","seattle");
.
insert into customers  values (12,"bill","columbus");
.
insert into customers  values (13,"craig","boston ");
.
insert into customers  values (14,"bonnie","columbus");
.
insert into customers  values (15,"scott","austin");
.
insert into customers  values (16,"jerry","charlotte");
.
insert into customers  values (17,"ray","charlotte");
.
insert into customers  values (18,"kees","phoenix");
.
insert into customers  values (19,"russ","houston");
.
insert into customers  values (20,"stan","austin");
.
insert into customers  values (21,"james","memphis");
.
insert into customers  values (22,"raymond","seattle");
.
insert into customers  values (23,"rick","austin");
.
insert into customers  values (24,"ken","boston ");
.
insert into customers  values (25,"jan","seattle");
.
insert into customers  values (26,"steve","charlotte");
.
insert into customers  values (27,"ronald","memphis");
.
insert into customers  values (28,"leon","seattle");
.
insert into customers  values (29,"rex","phoenix");
.
insert into customers  values (30,"joe","dallas");
.
insert into customers  values (31,"stu","dallas");
.
insert into customers  values (32,"tony","la");
.
insert into customers  values (33,"eduardo","seattle");
.
insert into customers  values (34,"wayne","charlotte");
.
insert into customers  values (35,"anthony","austin");
.
insert into customers  values (36,"lentor","memphis");
.
insert into customers  values (37,"mike","charlotte");
.
insert into customers  values (38,"matt","houston");
.
insert into customers  values (39,"josh","la");
.
insert into customers  values (40,"bob","columbus");
.
insert into customers  values (41,"mike","dallas");
.
insert into customers  values (42,"elvira","dallas");
.
insert into customers  values (43,"ron","austin");
.
insert into customers  values (44,"jeff","boston ");
.
insert into customers  values (45,"lea","columbus");
.
insert into customers  values (46,"phil","seattle");
.
insert into customers  values (47,"joan","la");
.
insert into customers  values (48,"ducu","columbus");
.
insert into customers  values (49,"john","dallas");
.
insert into customers  values (50,"mantar","la");
.
insert into products  values (100,"fruits",5);
.
insert into products  values (101,"detergent",10);
.
insert into products  values (102,"homeware",20);
.
insert into products  values (103,"seasonal",20);
.
insert into products  values (104,"pharmacy",15);
.
insert into products  values (105,"beverage",12);
.
insert into products  values (106,"detergent",10);
.
insert into products values (107,"pharmacy",15);
.
insert into products  values (108,"detergent",10);
.
insert into products  values (109,"seasonal",20);
.
insert into products values (110,"seasonal",20);
.
insert into products values (111,"snacks",8);
.
insert into products  values (112,"cereal",3);
.
insert into products  values (113,"seasonal",20);
.
insert into products  values (114,"pharmacy",15);
.
insert into products  values (115,"snacks",8);
.
insert into products  values (116,"homeware",20);
.
insert into products  values (117,"snacks",8);
.
insert into products  values (118,"pharmacy",15);
.
insert into products  values (119,"seasonal",20);
.
insert into products  values (120,"homeware",20);
.
insert into products  values (121,"snacks",8);
.
insert into products  values (122,"seasonal",20);
.
insert into products  values (123,"seasonal",20);
.
insert into products  values (124,"candy",5);
.
insert into products  values (125,"beverage",12);
.
insert into products  values (126,"beverage",12);
.
insert into products  values (127,"detergent",10);
.
insert into products  values (128,"detergent",10);
.
insert into products  values (129,"detergent",10);
.
insert into products  values (130,"cosmetics",30);
.
insert into products  values (131,"fruits",5);
.
insert into products  values (132,"homeware",20);
.
insert into products  values (133,"homeware",20);
.
insert into products  values (134,"homeware",20);
.
insert into products  values (135,"detergent",10);
.
insert into products  values (136,"candy",5);
.
insert into products  values (137,"fruits",5);
.
insert into products  values (138,"cosmetics",30);
.
insert into products  values (139,"snacks",8);
.
insert into products  values (140,"candy",5);
.
insert into products  values (141,"cereal",3);
.
insert into products  values (142,"detergent",10);
.
insert into products  values (143,"detergent",10);
.
insert into products  values (144,"fruits",5);
.
insert into products  values (145,"beverage",12);
.
insert into products  values (146,"detergent",10);
.
insert into products  values (147,"pharmacy",15);
.
insert into products  values (148,"cosmetics",30);
.
insert into products  values (149,"candy",5);
.
insert into orders  values (1,100,10);
.
insert into orders  values (1,101,5);
.
insert into orders  values (1,102,5);
.
insert into orders  values (2,100,10);
.
insert into orders  values (3,100,20);
.
insert into orders  values (4,103,15);
.
insert into orders  values (5,103,5);
.
insert into orders  values (6,104,5);
.
insert into orders  values (7,105,20);
.
insert into orders  values (8,105,10);
.
insert into orders  values (9,149,5);
.
insert into orders  values (10,149,10);
.
insert into orders  values (11,130,5);
.
insert into orders  values (12,135,5);
.
insert into orders  values (12,137,5);
.
insert into orders  values (12,136,10);
.
insert into orders  values (13,138,5);
.
insert into orders  values (14,110,30);
.
insert into orders  values (15,111,30);
.
insert into orders  values (16,112,10);
.
insert into orders  values (17,112,30);
.
insert into orders  values (18,114,5);
.
insert into orders  values (19,115,5);
.
insert into orders  values (20,125,10);
.
insert into orders  values (21,126,5);
.
insert into orders  values (22,127,5);
.
insert into orders  values (23,127,10);
.
insert into orders  values (24,127,20);
.
insert into orders  values (25,130,5);
.
insert into orders  values (26,131,10);
.
insert into orders  values (27,140,15);
.
insert into orders  values (28,140,20);
.
insert into orders  values (29,145,5);
.
insert into orders  values (30,111,5);
.
insert into orders  values (31,144,5);
.
insert into orders  values (32,144,10);
.
insert into orders  values (33,146,5);
.
insert into orders  values (34,148,10);
.
insert into orders  values (35,134,15);
.
insert into orders  values (36,132,20);
.
insert into orders  values (37,129,25);
.
insert into orders  values (38,100,10);
.
insert into orders  values (39,100,1);
.
insert into orders  values (39,101,10);
.
insert into orders  values (40,110,10);
.
insert into orders  values (40,120,20);
.
insert into orders  values (40,130,30);
.
insert into orders  values (45,111,10);
.
insert into orders  values (45,116,5);
.
insert into orders  values (50,104,5);
.
select * from products
where price>=20 and price<=30;
.
select cname,cloc 
from customers
where cloc="austin";
.
select cname
from customers 
where cid=1000;
.
update products
set price=31
where price=30;
.
commit;
.
update customers 
set cname="abc"
where cname="xyz";
.
delete products
where pid=150;
.
commit;
.
delete orders
where c_id=50;
.
abort;
.
select customers.cname, products.pname, orders.qty
from customers,products,orders
where orders.c_id=customers.cid and orders.p_id=products.pid and customers.cname="blad" and orders.qty<10;
.
select cname,pname,qty
from customers,products,orders
where c_id=cid and p_id=pid and cname="blad" and qty<10;
.
select products.pname
from products,orders
where products.pid=orders.p_id and orders.qty>15;
.
select pname
from products,orders
where pid=p_id and qty>15;
.
select customers.cname
from customers,orders
where customers.cid=orders.c_id and customers.cloc="austin" and orders.qty>=5 and orders.qty<=10;
.
select cname
from customers,orders
where cid=c_id and cloc="austin" and qty>=5 and qty<=10;
.


