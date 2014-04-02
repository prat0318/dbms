insert into emp values (1, 54, "CS", "Don" );
.
insert into emp values (2, 27, "CS", "Prat" );
.
insert into emp values (4, 43, "EC", "AG" );
.
insert into emp values (17, 34, "EC", "AB" );
.

insert into dept values (2, "CS", "bruce");
.
insert into dept values (3, "EC", "bruce");
.
insert into dept values (4, "CS", "batman");
.
insert into dept values (5, "EC", "Dale");
.

insert into univ values (1, "UT", 1, 3);
.
insert into univ values (2, "UTA", 2, 10);
.
insert into univ values (3, "UT", 3, 4);
.
delete emp where empno = 4;
.
// RESULT:
//emp.name	emp.age	
//------------------------
//"Don"	54	
//"Prat"	27	

select name, emp.age
from   emp
where  emp.name > "Batory";
.

//emp.name	
//------------------------
//"AB"	
select emp.name, dept.name
from   emp, dept
where  emp.empno = 17 and emp.dept_name = dept.dept_name
       and dept.chairman = "Dale";
.

//emp.name	emp.age	
//------------------------
//"Don"	54	
//"Prat"	27	
select name, age
from   emp
where  name > "Batory";
.

//dept.deptno	dept.dept_name	dept.chairman	emp.empno	emp.age	emp.dept_name	emp.name	
//------------------------
//5	"EC"	"Dale"	17	34	"EC"	"AB"	
select *
from   emp, dept
where  emp.empno = 17 and emp.dept_name = dept.dept_name
       and dept.chairman = "Dale";
.

insert into emp 
       values (10100, 26, "Computer Science", "Anderson, John" );
.

update emp set age = 36, name = "John, Anderson" where name = "Anderson, John" ;
.

//emp.empno	emp.age	emp.dept_name	emp.name	
//------------------------
//10100	36	"Computer Science"	"John, Anderson"	
select * from emp where empno = 10100;
.

delete emp where name = "John, Anderson" ;
.

///BELOW IS IMPLEMENTATION SPECIFIC - ALL SHOWS FROM ALL TABLES///
//(deptno:int   dept_name:str   chairman:str    )
//2     "CS"    "bruce"
//3     "EC"    "bruce"
//4     "CS"    "batman"
//5     "EC"    "Dale"
//
//-----------------------
//emp
//(empno:int    age:int dept_name:str   name:str        )
//1     54      "CS"    "Don"
//17    34      "EC"    "AB"
//2     27      "CS"    "Prat"
//
//-----------------------
//univ
//(univno:int   univ_name:str   empno:int       deptno:int      )
//1     "UT"    1       3
//2     "UTA"   2       10
//3     "UT"    3       4 
//
//-----------------------
select * from ALL;
.

//emp.empno	emp.age	emp.dept_name	emp.name	
//------------------------
//17	34	"EC"	"AB"	
select * from emp where empno > 9;
.

update dept set dept_name = "CS" where dept_name = "EC" and chairman = "bruce";
.

//emp.name	dept.chairman	univ.univ_name	
//------------------------
//"Don"	"bruce"	"UT"	
select emp.name, dept.chairman, univ.univ_name from emp, dept, univ where emp.dept_name = dept.dept_name and univ.empno = emp.empno and dept.deptno = univ.deptno;
.

//-- EXTRA POINTS --
//univ.univno	univ.univ_name	univ.empno	univ.deptno	
//------------------------
//2	"UTA"	2	10	
select * from univ where univ.univno = univ.empno and univ_name = "UTA";
.
