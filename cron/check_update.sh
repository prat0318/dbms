#!/bin/bash

# Copyright 2009 Ruben Berenguel

# ruben /at/ maia /dot/ ub /dot/ es

# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program. If not, see <http://www.gnu.org/licenses/>.

# PageDiffs: Fill in an array of webpages, with the option "write"
# will download them, with the "diff" option will re-download them and
# check the new against the old for differences. With the "diff mail"
# option, will send an email to $MAILRECIPIENT, assuming mail works.
# You can find the most up to date version of this file here
# http://rberenguel.googlecode.com/svn/trunk/Bash/PageDiffs.sh

# 20091226@00:24 Ruben Berenguel

# 20100818@07:10 Steve Almond from silkandslug.com found & solved a
# sh/bash problem. Suggestion for next revision: emailing diff

# 20100818@07:27 Previous suggestion forced as default. Now there is
# also the diff in the mail. For the next revision, add a command line
# switch to enable/disable it.

MAILRECIPIENT="prat0318@gmail.com"

j=0
#Pages[j++]="http://www.cs.utexas.edu/users/dsb/cs386d/index.html"
Pages[j++]="http://www.cs.utexas.edu/users/dsb/cs386d/Projects/Project1.html"

if [ "$1" = "write" ]; then
    echo Generate files
    count=0
    for i in "${Pages[@]}"
    do
	echo Getting "$i" into File$count
	wget "$i" -v -O "File$count"
	let count=$count+1
    done
fi
if [ "$1" = "diff" ]; then
    count=0

    for i in "${Pages[@]}"
    do
	# echo Getting "$i" into Test$count
	wget "$i" -q -O "Test$count"
	Output=$(diff -q "Test$count" "File$count" | grep differ)
	Result=$?
	if [ "$Result" = "0" ]; then
	    if [ "$2" = "mail" ]; then
		echo "******************************************" >> MailCont
		echo Page at "$i" has changed since last check! >> MailCont
		echo Diff follows >> MailCont
		echo -e "\n\n\n" >> MailCont
		diff "Test$count" "File$count" >> MailCont
		echo Diff follows >> MailCont
		echo "******************************************" >> MailCont
		mail="1"
	    fi
	    echo Page at "$i" has changed since last check!
	else
	    echo Page at "$i" has not changed since last check!
	fi
	#rm Test$count
	let count=$count+1
    done
    if [ "$mail" = "1" ]; then 
	mail -s "dbms Page changed alert!" $MAILRECIPIENT < MailCont			
	rm MailCont
    fi
fi
