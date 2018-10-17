/* Dani Raznikov - dxr151630@utdallas.edu */
package org.pitest.mutationtest.engine.gregor.mutators;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.HashMap;
import java.util.Map;

public enum RelationalOperatorReplacementMutator implements MethodMutatorFactory {

    EQUALS_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new EqualsOpeatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() +  " EqualsMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.EQUALS_MUTATOR.name();
        }
    },

    NOT_EQUAL_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new NotEqualOpeatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " NonEqualMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.NOT_EQUAL_MUTATOR.name();
        }
    },

    GREATER_OR_EQUAL_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new GreaterOrEqualOpeatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " GreaterOrEqualMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.GREATER_OR_EQUAL_MUTATOR.name();
        }
    },

    GREATER_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new GreaterOpeatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " GreaterMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.GREATER_MUTATOR.name();
        }
    },

    LESS_OR_EQUAL_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new LessOrEqualOpeatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " LessOrEqualMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.LESS_OR_EQUAL_MUTATOR.name();
        }
    },

    LESS_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new LessOperatorMethodVisitor(this, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " LessMutator";
        }

        @Override
        public String getName() {
            return RelationalOperatorReplacementMutator.LESS_MUTATOR.name();
        }
    }
}

// Changing all relational operators to equals sign
class EqualsOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS = new HashMap<>();
    static {
        String description = "ROR: Changed > to ==";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFEQ, description));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPEQ, description));

        description = "ROR: Changed < to ==";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFEQ, description));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPEQ, description));

        description = "ROR: Changed >= to ==";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFEQ, description));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPEQ, description));

        description = "ROR: Changed <= to ==";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFEQ, description));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPEQ, description));

        description = "ROR: Changed != to ==";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFEQ, description));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPEQ, description));
        MUTATIONS.put(Opcodes.IF_ACMPNE, new Substitution(Opcodes.IF_ACMPEQ, description));
        MUTATIONS.put(Opcodes.IFNONNULL, new Substitution(Opcodes.IFNULL, description));
    }

    EqualsOpeatorMethodVisitor(final MethodMutatorFactory factory,
                               final MutationContext context,
                               final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}

// Changing all relational operators to non-equals sign
class NotEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        String description = "ROR: Changed > to !=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFNE, description));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPNE, description));

        description = "ROR: Changed < to !=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFNE, description));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPNE, description));

        description = "ROR: Changed >= to !=";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFNE, description));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPNE, description));

        description = "ROR: Changed <= to !=";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFNE, description));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPNE, description));

        description = "ROR: Changed == to !=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFNE, description));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPNE, description));
        MUTATIONS.put(Opcodes.IF_ACMPEQ, new Substitution(Opcodes.IF_ACMPNE, description));
        MUTATIONS.put(Opcodes.IFNULL, new Substitution(Opcodes.IFNONNULL, description));
    }

    NotEqualOpeatorMethodVisitor(final MethodMutatorFactory factory,
                               final MutationContext context,
                               final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}

// Changing all relational operators to greater-or-equals sign
class GreaterOrEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        String description = "ROR: Changed > to >=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFGE, description));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPGE, description));

        description = "ROR: Changed < to >=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGE, description));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPGE, description));

        description = "ROR: Changed != to >=";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGE, description));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPGE, description));

        description = "ROR: Changed <= to >=";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGE, description));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPGE, description));

        description = "ROR: Changed == to >=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGE, description));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPGE, description));
    }

    GreaterOrEqualOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                       final MutationContext context,
                                       final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}

// Changing all relational operators to greater sign
class GreaterOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        String description = "ROR: Changed >= to >";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFGT, description));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPGT, description));

        description = "ROR: Changed < to >";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGT, description));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPGT, description));

        description = "ROR: Changed != to >";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGT, description));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPGT, description));

        description = "ROR: Changed <= to >";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGT, description));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPGT, description));

        description = "ROR: Changed == to >";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGT, description));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPGT, description));
    }

    GreaterOpeatorMethodVisitor(final MethodMutatorFactory factory,
                               final MutationContext context,
                               final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}

// Changing all relational operators to less-or-equals sign
class LessOrEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        String description = "ROR: Changed > to <=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLE, description));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPLE, description));

        description = "ROR: Changed < to <=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFLE, description));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPLE, description));

        description = "ROR: Changed != to <=";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLE, description));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPLE, description));

        description = "ROR: Changed >= to <=";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLE, description));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPLE, description));

        description = "ROR: Changed == to <=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLE, description));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPLE, description));
    }

    LessOrEqualOpeatorMethodVisitor(final MethodMutatorFactory factory,
                               final MutationContext context,
                               final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}

// Changing all relational operators to less sign
class LessOperatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        String description = "ROR: Changed > to <";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLT, description));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPLT, description));

        description = "ROR: Changed <= to <";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFLT, description));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPLT, description));

        description = "ROR: Changed != to <";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLT, description));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPLT, description));

        description = "ROR: Changed >= to <";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLT, description));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPLT, description));

        description = "ROR: Changed == to <";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLT, description));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPLT, description));
    }

    LessOperatorMethodVisitor(final MethodMutatorFactory factory,
                              final MutationContext context,
                              final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}