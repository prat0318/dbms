// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.

package mdb;

public class Spec_listElem extends AstListNode {

    public AstToken getCOMMA () {
        return (AstToken) tok [0] ;
    }

    public Field_spec getField_spec () {
        
        return (Field_spec) arg [0] ;
    }

    public Spec_listElem setParms (AstToken tok0, Field_spec arg0) {
        
        tok = new AstToken [1] ;
        tok [0] = tok0 ;            /* COMMA */
        return setParms (arg0) ;    /* Field_spec */
    }

    public Spec_listElem setParms (Field_spec arg0) {
        
        super.setParms (arg0) ;     /* Field_spec */
        return (Spec_listElem) this ;
    }

}