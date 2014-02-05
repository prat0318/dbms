-- 1. What is the price of the part named "Dirty Harry"?

select PRICE from parts where PNAME = 'Dirty Harry';

--  2.   What orders have been shipped after date '03-feb-95'?

select orders.ONO from orders where TO_DATE(SHIPPED,'DD-MON-YY') > TO_DATE('03-FEB-95','DD-MON-YY');

-- 3.   What are the ono and cname values of customers whose orders have not been shipped (i.e., the shipped column has a null value)?

select orders.ONO, customers.CNAME from customers, orders where customers.CNO = orders.CNO and orders.SHIPPED is null;

--  4.   Retrieve the names of parts whose quantity on hand (QOH) is between 20 and 70.

select PNAME from parts where QOH between 20 and 70;

--  5.   Get all unique pairs of cno values for customers that have the same zip code.

select c1.CNO, c2.CNO from customers c1, customers c2 where c1.ZIP = c2.ZIP and c1.CNO < c2.CNO;

--  6.   Create a nested SQL select statement that returns the cname values of customers who have placed orders with employees living in Fort Dodge.

select c.CNAME from customers c where 'Fort Dodge' = ALL(select z.CITY from employees e, zipcodes z, orders o where c.CNO = o.CNO and o.ENO = e.ENO and e.ZIP = z.ZIP);
--  7.   What orders have been shipped to Wichita?

select o.ONO from orders o, customers c, zipcodes z where o.CNO = c.CNO and c.ZIP = z.ZIP and z.CITY = 'Wichita';

--  8.   Get the pname values of parts with the lowest price.

select PNAME from (select * from parts order by PRICE asc) where rownum <= 1;

--  9.   What is the name of the part with the lowest price? (use qualified comparison in your predicate, i.e., <=all).

select PNAME from parts where PRICE <= ALL(select PRICE from parts);

--  10.   What parts cost more than the most expensive Land Before Time part? (Hint: you should use pattern-matching, e.g., pname like 'Land Before Time%').

select PNO from PARTS where PRICE > ALL(select PRICE from PARTS where PNAME like 'Land Before Time%');

--  11.   Write a correlated query to return the cities of zipcodes from which an order has been placed.

select CITY from zipcodes z where (select count(*) from customers c, orders o where c.CNO = o.CNO and c.ZIP = z.ZIP) > 0;

--  12.   Get cname values of customers who have placed at least one part order through employee with eno = 1000.

select distinct c.CNAME from customers c, orders o, employees e where c.CNO = o.CNO and o.ENO = e.ENO and e.ENO = 1000;

--  13.   Get the total number of customers.

select count(*) from customers;

--  14.   Get the pname values of parts that cost more than the average cost of all parts.

select PNAME from parts where PRICE > (select avg(PRICE) from parts);

--  15.   For each part, get pno and pname values along with the total sales in dollars.

select PNO, PNAME, ((select SUM(QTY) from odetails where PNO = parts.PNO) * PRICE) SALES from parts;

--  16.   For each part, get pno and pname values along with the total sales in dollars, but only for total sales exceeding $200.

select PNO, PNAME, ((select SUM(QTY) from odetails where PNO = parts.PNO) * PRICE) SALES from parts where ((select SUM(QTY) from odetails where PNO = parts.PNO) * PRICE) > 200;

--  17.   Repeat the last 2 queries, except this time create a view to simplify your work. Define the view and each query on that view.
create view sales as select PNO, SUM(QTY) TOTAL from odetails group by PNO;
--  17.1
select p.PNO, PNAME, (TOTAL * PRICE) SALES from parts p, sales s where p.PNO = s.PNO;
--  17.2
select p.PNO, PNAME, (TOTAL * PRICE) SALES from parts p, sales s where p.PNO = s.PNO and TOTAL * PRICE > 200;

--  18.   Delete order 1021 and its order details.
delete from odetails where ONO = 1021;
delete from orders where ONO = 1021;

--  19.   Increase the cost of all parts by 5%.

UPDATE parts set PRICE = (PRICE * 1.05);

--  20.   Retrieve employees by name in reverse alphabetical order.

select ENAME from employees order by ENAME desc;

--  21.   What tuples of Employees and Zipcodes do not participate in a join of these relations? Use the outerjoin and minus operations.

select z.ZIP, e.ENO from employees e FULL JOIN zipcodes z on e.ZIP = z.ZIP
MINUS
select z.ZIP, e.ENO from employees e JOIN zipcodes z on e.ZIP = z.ZIP;
