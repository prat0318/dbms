open "don/mdb.database";
.

create table emp ( empno int,
              age   int,
              dept_name str,
              name str
            );
.

// create table dept relation
create table dept ( deptno int,
               dept_name str,
               chairman str
             );
.

create table univ( univno int,
					univ_name str,
					empno int,
					deptno int);
.

show;
.

script "../tests1/dmltest1.sql";
.
select * from emp;
.
close;
.

exit;
.
