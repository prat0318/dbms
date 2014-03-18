package minidb.je;

import mdb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredicateHelpers {

    public static Map<String, List<AstNode>> generateClauses(String firstRelation, AstNode e) {
        AstCursor c = new AstCursor();
        Map<String, List<AstNode>> clauses = new HashMap<String, List<AstNode>>();
        for (c.FirstElement(e); c.MoreElement(); c.NextElement() ) {
            AstNode node = c.node;
            if(!(node instanceof JoinClause)) {
                //local predicate
                String relation = (node.arg[0] instanceof Rel_dot_field) ?
                        node.arg[0].arg[0].toString().trim() : firstRelation;  //if relation not given, assume only one table.
                //create Map Entry for each relation
                List<AstNode> clauseList = clauses.get(relation);
                if(clauseList == null) clauseList = new ArrayList<AstNode>();
                clauseList.add(node); clauses.put(relation, clauseList);
            }
        }
        return clauses;
    }

    public static int[] setIndices(Map<String, String[]> metaColumnRelation, Map<String, List<AstNode>> clauses, String relation) {
        int[] indices = new int[clauses.get(relation).size()];
        for(int i = 0; i < indices.length; i++) {
            indices[i] = -1;
            String clauseName = clauses.get(relation).get(i).arg[0] instanceof Rel_dot_field ?
                    clauses.get(relation).get(i).arg[0].toString().trim()
                    : relation+"."+clauses.get(relation).get(i).arg[0].toString().trim();
            for(int j = 0; j < metaColumnRelation.get(relation).length; j++) {
                if(clauseName.equals(metaColumnRelation.get(relation)[j])) {
                    indices[i] = j; break;
                }
            }
        }
        return indices;
    }

    public static boolean applyLocalPredicate(String[] metaColumnType, Map<String, List<AstNode>> clauses, String relation, int[] indices, String[] row) {
        boolean keepRow = true;
        for (int i = 0; i < indices.length; i++) {
            //clauses.get(relation) is ArrayList
            Rel operator = (Rel) clauses.get(relation).get(i).arg[1];
            String rhs1 = clauses.get(relation).get(i).arg[2].toString().trim().replaceAll(",", "&&");
            String col1 = row[indices[i]];
            if ("int".equals(metaColumnType[indices[i]])) {
                int col = Integer.parseInt(col1);
                int rhs = Integer.parseInt(rhs1);
                if (
                        (operator instanceof Equ && !(col == rhs)) ||
                                (operator instanceof Neq && !(col != rhs)) ||
                                (operator instanceof Geq && !(col >= rhs)) ||
                                (operator instanceof Leq && !(col <= rhs)) ||
                                (operator instanceof Lss && !(col < rhs)) ||
                                (operator instanceof Gtr && !(col > rhs)))
                    keepRow = false;
            } else if (
                    (operator instanceof Equ && !(col1.compareTo(rhs1) == 0)) ||
                            (operator instanceof Neq && !(col1.compareTo(rhs1) != 0)) ||
                            (operator instanceof Geq && !(col1.compareTo(rhs1) >= 0)) ||
                            (operator instanceof Leq && !(col1.compareTo(rhs1) <= 0)) ||
                            (operator instanceof Lss && !(col1.compareTo(rhs1) < 0)) ||
                            (operator instanceof Gtr && !(col1.compareTo(rhs1) > 0)))
                keepRow = false;
        }
        return keepRow;
    }

    public static void formatData(Map<String, String[]> metaColumnRelation,
                                  Map<String, String[]> metaColumnTypeRelation,
                                  Map<String, List<String[]>> allRowsOfRelations,
                                  List<String> data) {
        String[] meta = (data.remove(0)).split(",", 2);
        // relationName -> ["relationName.col1", "relationName.col2" ...]
        String[] columnNameAndType = meta[1].split(",");
        String columnNames[] = new String[columnNameAndType.length];
        String columnTypes[] = new String[columnNameAndType.length];
        for(int i = 0; i < columnNameAndType.length; i++) {
            columnNames[i] = meta[0] + "." + columnNameAndType[i].split(":")[0];
            columnTypes[i] = columnNameAndType[i].split(":")[1];
        }
        metaColumnRelation.put(meta[0], columnNames);
        metaColumnTypeRelation.put(meta[0], columnTypes);
        List<String[]> rows = new ArrayList<String[]>();
        for(String s: data) rows.add(s.split(","));
        // relationName -> [["val1","val2"], ["val1","val2"]...]
        allRowsOfRelations.put(meta[0], rows);
    }
}
