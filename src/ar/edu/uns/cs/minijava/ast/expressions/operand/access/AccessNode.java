package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.ChainedNode;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

public abstract class AccessNode extends OperandNode {
    private ReferenceType castingType; //TODO: check si esto es válido y necesario.
    private ChainedNode chained;
}
