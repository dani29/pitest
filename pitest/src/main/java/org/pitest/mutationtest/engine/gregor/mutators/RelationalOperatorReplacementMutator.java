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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
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
            return this.getClass().getName();
        }

        @Override
        public String getName() {
            return name();
        }
    }
}

// Changing all relational operators to equals sign
class EqualsOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, Substitution> MUTATIONS = new HashMap<>();
    static {
        String DESCRIPTION = "Changed > to ==";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPEQ, DESCRIPTION));

        DESCRIPTION = "Changed < to ==";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPEQ, DESCRIPTION));

        DESCRIPTION = "Changed >= to ==";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPEQ, DESCRIPTION));

        DESCRIPTION = "Changed <= to ==";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPEQ, DESCRIPTION));

        DESCRIPTION = "Changed != to ==";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ACMPNE, new Substitution(Opcodes.IF_ACMPEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNONNULL, new Substitution(Opcodes.IFNULL, DESCRIPTION));
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
        String DESCRIPTION = "Changed > to !=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPNE, DESCRIPTION));

        DESCRIPTION = "Changed < to !=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPNE, DESCRIPTION));

        DESCRIPTION = "Changed >= to !=";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPNE, DESCRIPTION));

        DESCRIPTION = "Changed <= to !=";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPNE, DESCRIPTION));

        DESCRIPTION = "Changed == to !=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ACMPEQ, new Substitution(Opcodes.IF_ACMPNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNULL, new Substitution(Opcodes.IFNONNULL, DESCRIPTION));
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
        String DESCRIPTION = "Changed > to >=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPGE, DESCRIPTION));

        DESCRIPTION = "Changed < to >=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPGE, DESCRIPTION));

        DESCRIPTION = "Changed != to >=";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPGE, DESCRIPTION));

        DESCRIPTION = "Changed <= to >=";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPGE, DESCRIPTION));

        DESCRIPTION = "Changed == to >=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPGE, DESCRIPTION));
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
        String DESCRIPTION = "Changed >= to >";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPGT, DESCRIPTION));

        DESCRIPTION = "Changed < to >";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPGT, DESCRIPTION));

        DESCRIPTION = "Changed != to >";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPGT, DESCRIPTION));

        DESCRIPTION = "Changed <= to >";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPGT, DESCRIPTION));

        DESCRIPTION = "Changed == to >";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPGT, DESCRIPTION));
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
        String DESCRIPTION = "Changed > to <=";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPLE, DESCRIPTION));

        DESCRIPTION = "Changed < to <=";
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPLE, DESCRIPTION));

        DESCRIPTION = "Changed != to <=";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPLE, DESCRIPTION));

        DESCRIPTION = "Changed >= to <=";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPLE, DESCRIPTION));

        DESCRIPTION = "Changed == to <=";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPLE, DESCRIPTION));
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
        String DESCRIPTION = "Changed > to <";
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPLT, DESCRIPTION));

        DESCRIPTION = "Changed <= to <";
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPLT, DESCRIPTION));

        DESCRIPTION = "Changed != to <";
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPLT, DESCRIPTION));

        DESCRIPTION = "Changed >= to <";
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPLT, DESCRIPTION));

        DESCRIPTION = "Changed == to <";
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPLT, DESCRIPTION));
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