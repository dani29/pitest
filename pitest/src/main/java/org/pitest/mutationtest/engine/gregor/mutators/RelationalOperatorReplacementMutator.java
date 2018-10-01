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
    };
}

class EqualsOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to equals operator";
    private static final Map<Integer, Substitution> MUTATIONS = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFEQ, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFEQ, DESCRIPTION));
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


class NotEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to not-equals operator";
    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFNE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFNE, DESCRIPTION));
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

class GreaterOrEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to greater-or-equal operator";
    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGE, DESCRIPTION));
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

class GreaterOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to greater operator";
    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFGT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFGT, DESCRIPTION));
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

class LessOrEqualOpeatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to less-or-equal operator";
    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFLE, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLE, DESCRIPTION));
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

class LessOperatorMethodVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "Changing all relational operators to less operator";
    private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<>();

    static {
        MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLT, DESCRIPTION));
        MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLT, DESCRIPTION));
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