// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.

package mdb;
import Jakarta.util.*;
import java.io.*;
import java.util.*;

public class Neq extends Rel {

    final public static int ARG_LENGTH = 1 /* Kludge! */ ;
    final public static int TOK_LENGTH = 2 ;

    public void execute () {
        
        super.execute();
    }

    public AstToken getBANG () {
        
        return (AstToken) tok [0] ;
    }

    public AstToken getEQ () {
        
        return (AstToken) tok [1] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, true} ;
    }

    public Neq setParms (AstToken tok0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* BANG */
        tok [1] = tok1 ;            /* EQ */
        
        InitChildren () ;
        return (Neq) this ;
    }

}