package org.pitest.mutationtest.engine.gregor.mutators;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum ArithmeticOperandDeletionMutator implements MethodMutatorFactory {

        DELETE_FIRST_OPERAND {
            @Override
            public MethodVisitor create(final MutationContext context,
                                        final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
                return new DeleteFirstOperandMethodVisitor(this, methodInfo, context, methodVisitor);
            }

            @Override
            public String getGloballyUniqueId() {
                return this.getClass().getName() +  " DeleteFirstOperandMethodVisitor";
            }

            @Override
            public String getName() {
                return ArithmeticOperandDeletionMutator.DELETE_FIRST_OPERAND.name();
            }
        },

        DELETE_SECOND_OPERAND {
            @Override
            public MethodVisitor create(final MutationContext context,
                                        final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
                return new DeleteSecondOperandMethodVisitor(this, methodInfo, context, methodVisitor);
            }

            @Override
            public String getGloballyUniqueId() {
                return this.getClass().getName() + " DeleteSecondOperandMethodVisitor";
            }

            @Override
            public String getName() {
                return ArithmeticOperandDeletionMutator.DELETE_SECOND_OPERAND.name();
            }
        }
}

/* Enum to represent the size each operand takes on the method stack.
    Integer and Float store operands add single value into the stack,
    while Double and Long store operands require double size on the stack.
 */
enum ArithmeticOperandsSize {
    SINGLE,
    DOUBLE;

    static ArithmeticOperandsSize getOperandsSize(final int opcode) {
        switch (opcode) {
            case Opcodes.IADD:
            case Opcodes.ISUB:
            case Opcodes.IMUL:
            case Opcodes.IDIV:
            case Opcodes.IREM:
            case Opcodes.FADD:
            case Opcodes.FSUB:
            case Opcodes.FMUL:
            case Opcodes.FDIV:
            case Opcodes.FREM:
                return ArithmeticOperandsSize.SINGLE;
            case Opcodes.LADD:
            case Opcodes.LSUB:
            case Opcodes.LMUL:
            case Opcodes.LDIV:
            case Opcodes.LREM:
            case Opcodes.DADD:
            case Opcodes.DSUB:
            case Opcodes.DMUL:
            case Opcodes.DDIV:
            case Opcodes.DREM:
                return ArithmeticOperandsSize.DOUBLE;
            default:
                return null;
        }

    }
}

// TODO
abstract class ArithmeticOperandVisitor extends MethodVisitor {

    private MutationContext context;
    private MethodInfo methodInfo;
    private MethodMutatorFactory factory;


    ArithmeticOperandVisitor(final MethodMutatorFactory factory,
                                    final MethodInfo methodInfo,
                                    final MutationContext context,
                                    final MethodVisitor delegateVisitor) {
        super(Opcodes.ASM6, delegateVisitor);
        this.context = context;
        this.methodInfo = methodInfo;
        this.factory = factory;
    }

    @Override
    public void visitInsn(int opcode) {
        ArithmeticOperandsSize operationSize = ArithmeticOperandsSize.getOperandsSize(opcode);

        // Not an arithmetic operation - ignore
        if (operationSize == null) {
            super.visitInsn(opcode);
        } else {
            // Register mutation and check if should be mutated
            boolean shouldMutate = registerAndVerify();

            if (shouldMutate) {
                if (operationSize == ArithmeticOperandsSize.SINGLE) {
                    mutateSingleSizeOperation();
                } else if (operationSize == ArithmeticOperandsSize.DOUBLE) {
                    mutateDoubleSizeOperation();
                }
            } else {
                super.visitInsn(opcode);
            }
        }
    }

    private boolean registerAndVerify() {
        MutationIdentifier mi = this.context.registerMutation(
                this.factory, getMutationDescription());
        return this.context.shouldMutate(mi);
    }

    abstract String getMutationDescription();

    abstract void mutateSingleSizeOperation();

    abstract void mutateDoubleSizeOperation();
}

// TODO
class DeleteFirstOperandMethodVisitor extends ArithmeticOperandVisitor {

    DeleteFirstOperandMethodVisitor(final MethodMutatorFactory factory,
                                           final MethodInfo methodInfo,
                                           final MutationContext context,
                                           final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    String getMutationDescription() {
        return "AOD: Removed the first operand from an arithmetic expression";
    }
    /* Deleting the first operand performed by swapping the top two words on the stack,
    and popping the top one (which is the first word after the swap).
     */
    @Override
    void mutateSingleSizeOperation() {
        super.visitInsn(Opcodes.SWAP);
        super.visitInsn(Opcodes.POP);
    }

    /* Deleting the first double-sized operand performed by duplicating the
    top two words of the stack and inserting beneath the previous for,
    and then twice double-popping to clear the top four workds of the stack.
     */

    @Override
    void mutateDoubleSizeOperation() {
        super.visitInsn(Opcodes.DUP2_X2);
        super.visitInsn(Opcodes.POP2);
        super.visitInsn(Opcodes.POP2);
    }
}

// TODO
class DeleteSecondOperandMethodVisitor extends ArithmeticOperandVisitor {

     DeleteSecondOperandMethodVisitor(final MethodMutatorFactory factory,
                                            final MethodInfo methodInfo,
                                            final MutationContext context,
                                            final MethodVisitor delegateMethodVisitor) {
        super(factory, methodInfo, context, delegateMethodVisitor);
    }

    @Override
    String getMutationDescription() {
        return "AOD: Removed the second operand from an arithmetic expression";
    }

    /* Deleting the second operand performed by popping the last inserted value
    to the stack.
     */
    @Override
    void mutateSingleSizeOperation() {
        super.visitInsn(Opcodes.POP);
    }

    /* Deleting the second double-sized operand performed by popping the last
    two words of the stack (which contain the whole double-size operand)
     */

    @Override
    void mutateDoubleSizeOperation() {
        super.visitInsn(Opcodes.POP2);
    }
}
