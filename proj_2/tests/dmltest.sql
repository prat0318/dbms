// a simple test of the SQL MDB DML
// 

open "don/mdb.database";
.

insert into emp values ( 54, "Don" );
.

delete emp where empno = 4;
.

select  R.A, R.b, R.c, R.d, S.x, S.y
from  R, S
where R.d = 4 
and   R.a = S.x
and   S.y = "UT";
.

update R set a = "DON", d = 41
where d = 4;
.

abort;
.
commit;
.

select name, emp.age
from   emp
where  emp.name > "Batory";
.

select emp.name, dept.name
from   emp, dept
where  emp.empno = 17 and emp.dept_name = dept.dept_name
       and dept.chairman = "Dale";
.

select name, age
from   emp
where  name > "Batory";
.

select *
from   emp, dept
where  emp.empno = 17 and emp.dept_name = dept.dept_name
       and dept.chairman = "Dale";
.

insert into emp 
       values (10100, 26, "Computer Science", "Anderson, John" );
.

update emp set age = 26 where name = "Anderson, John" ;
.

delete emp where name = "Anderson, John" ;
.
select * from ALL;
.

close;
.
exit;
.
