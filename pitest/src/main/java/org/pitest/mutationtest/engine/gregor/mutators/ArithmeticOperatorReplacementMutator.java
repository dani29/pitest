/* Dani Raznikov - dxr151630@utdallas.edu */
package org.pitest.mutationtest.engine.gregor.mutators;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;
import org.pitest.mutationtest.engine.gregor.InsnSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.AbstractInsnMutator;

import java.util.HashMap;
import java.util.Map;

public enum ArithmeticOperatorReplacementMutator implements MethodMutatorFactory {

    ADDITION_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new AdditionOpeatorMethodVisitor(this, methodInfo, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() +  " AdditionMutator";
        }

        @Override
        public String getName() {
            return ArithmeticOperatorReplacementMutator.ADDITION_MUTATOR.name();
        }
    },

    SUBTRACTION_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new SubtractionOpeatorMethodVisitor(this, methodInfo, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " SubtractionMutator";
        }

        @Override
        public String getName() {
            return ArithmeticOperatorReplacementMutator.SUBTRACTION_MUTATOR.name();
        }
    },

    MULTIPLICATION_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new MultiplicationOpeatorMethodVisitor(this, methodInfo, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " MultiplicationMutator";
        }

        @Override
        public String getName() {
            return ArithmeticOperatorReplacementMutator.MULTIPLICATION_MUTATOR.name();
        }
    },

    DIVISION_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new DivisionOpeatorMethodVisitor(this, methodInfo, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " DivisionMutator";
        }

        @Override
        public String getName() {
            return ArithmeticOperatorReplacementMutator.DIVISION_MUTATOR.name();
        }
    },

    MODULUS_MUTATOR {
        @Override
        public MethodVisitor create(final MutationContext context,
                                    final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
            return new ModulusOpeatorMethodVisitor(this, methodInfo, context, methodVisitor);
        }

        @Override
        public String getGloballyUniqueId() {
            return this.getClass().getName() + " ModulusMutator";
        }

        @Override
        public String getName() {
            return ArithmeticOperatorReplacementMutator.MODULUS_MUTATOR.name();
        }
    }

}

// Changing all arithmetic operators to addition sign
class AdditionOpeatorMethodVisitor extends AbstractInsnMutator {

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<>();
    static {
        String description = "AOR: Changed - to +";
        MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IADD, description));
        MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LADD, description));
        MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FADD, description));
        MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DADD, description));

        description = "AOR: Changed * to +";
        MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IADD, description));
        MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LADD, description));
        MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FADD, description));
        MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DADD, description));

        description = "AOR: Changed / to +";
        MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IADD, description));
        MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LADD, description));
        MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FADD, description));
        MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DADD, description));

        description = "AOR: Changed % to +";
        MUTATIONS.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IADD, description));
        MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LADD, description));
        MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FADD, description));
        MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DADD, description));

    }

    AdditionOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                 final MethodInfo methodInfo,
                                 final MutationContext context,
                                 final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS;
    }
}

// Changing all arithmetic operators to subtraction sign
class SubtractionOpeatorMethodVisitor extends AbstractInsnMutator {

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<>();
    static {
        String description = "AOR: Changed + to -";
        MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.ISUB, description));
        MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LSUB, description));
        MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FSUB, description));
        MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DSUB, description));

        description = "AOR: Changed * to -";
        MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.ISUB, description));
        MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LSUB, description));
        MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FSUB, description));
        MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DSUB, description));

        description = "AOR: Changed / to -";
        MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.ISUB, description));
        MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LSUB, description));
        MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FSUB, description));
        MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DSUB, description));

        description = "AOR: Changed % to -";
        MUTATIONS.put(Opcodes.IREM, new InsnSubstitution(Opcodes.ISUB, description));
        MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LSUB, description));
        MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FSUB, description));
        MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DSUB, description));

    }

    SubtractionOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                    final MethodInfo methodInfo,
                                    final MutationContext context,
                                    final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS;
    }
}

// Changing all arithmetic operators to multiplication sign
class MultiplicationOpeatorMethodVisitor extends AbstractInsnMutator {

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<>();
    static {
        String description = "AOR: Changed + to *";
        MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IMUL, description));
        MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LMUL, description));
        MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FMUL, description));
        MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DMUL, description));

        description = "AOR: Changed - to *";
        MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IMUL, description));
        MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LMUL, description));
        MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FMUL, description));
        MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DMUL, description));

        description = "AOR: Changed / to *";
        MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IMUL, description));
        MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LMUL, description));
        MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FMUL, description));
        MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DMUL, description));

        description = "AOR: Changed % to *";
        MUTATIONS.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IMUL, description));
        MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LMUL, description));
        MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FMUL, description));
        MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DMUL, description));

    }

    MultiplicationOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                       final MethodInfo methodInfo,
                                       final MutationContext context,
                                       final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS;
    }
}

// Changing all arithmetic operators to division sign
class DivisionOpeatorMethodVisitor extends AbstractInsnMutator {

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<>();
    static {
        String description = "AOR: Changed + to /";
        MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IDIV, description));
        MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LDIV, description));
        MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FDIV, description));
        MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DDIV, description));

        description = "AOR: Changed - to /";
        MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IDIV, description));
        MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LDIV, description));
        MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FDIV, description));
        MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DDIV, description));

        description = "AOR: Changed * to /";
        MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IDIV, description));
        MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LDIV, description));
        MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FDIV, description));
        MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DDIV, description));

        description = "AOR: Changed % to /";
        MUTATIONS.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IDIV, description));
        MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LDIV, description));
        MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FDIV, description));
        MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DDIV, description));

    }

    DivisionOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                 final MethodInfo methodInfo,
                                 final MutationContext context,
                                 final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS;
    }
}

// Changing all arithmetic operators to modulus sign
class ModulusOpeatorMethodVisitor extends AbstractInsnMutator {

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<>();
    static {
        String description = "AOR: Changed + to %";
        MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IREM, description));
        MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LREM, description));
        MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FREM, description));
        MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DREM, description));

        description = "AOR: Changed - to %";
        MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IREM, description));
        MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LREM, description));
        MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FREM, description));
        MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DREM, description));

        description = "AOR: Changed * to %";
        MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IREM, description));
        MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LREM, description));
        MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FREM, description));
        MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DREM, description));

        description = "AOR: Changed / to %";
        MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IREM, description));
        MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LREM, description));
        MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FREM, description));
        MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DREM, description));

    }

    ModulusOpeatorMethodVisitor(final MethodMutatorFactory factory,
                                final MethodInfo methodInfo,
                                final MutationContext context,
                                final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS;
    }
}
