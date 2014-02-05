This directory contains the source code for Sql MDB.
It also contains the jakarta.jar file, which you should
remove and place on your classpath.

Compile the source by:

> javac *.java

The grammar for MDB is grammar.b and the javacc version
of it is in grammar.jj

------------------------------------------------

This set of files is an update of the previously posted mdb source.
The files that are different are:

BaliParser.java			// you shouldn't have changed this
				// as it is a generated file

FieldDecl.java			// you *might* have changed this,
				// although this is unlikely

Fld_decl_listElem.java		// unlikely you have changed this file.

grammar.b			// you don't use this file; this
				// is the updated grammar file.
grammar.jj			// you use this file, but as it
				// is generated, you shouldn't have
				// changed it.

My suggestion is that you see if you have edited any of the above
files.  Chances are that you haven't.  If so, just replace the
old versions with the new versions.

