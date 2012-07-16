/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012, ndim.org
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this 
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or
 *   other materials provided with the distribution.
 * - Neither the name of ndim nor the names of its contributors may
 *   be used to endorse or copy products derived from this software without
 *   specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package javabenchmark.bytecode;

import java.lang.reflect.Method;
import org.objectweb.asm.*;

public class Transform implements Opcodes
{

    public static Algorithm compile(final String className, final Method method) throws Exception
    {
        final DynamicClassLoader loader =
                new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
        return (Algorithm)(loader.define(className, createByteCode(className, method)).newInstance());
    }
    
    /**
     * The dynamic class loader that will load our handcrafted class.
     */
    private static class DynamicClassLoader extends ClassLoader
    {

        public DynamicClassLoader(ClassLoader parent)
        {
            super(parent);
        }

        public Class<?> define(String className, byte[] bytecode)
        {
            return super.defineClass(className, bytecode, 0, bytecode.length);
        }
    }
    
    
    private static byte[] createByteCode(final String className, final Method method) throws Exception
    {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;
        
        if(!org.objectweb.asm.Type.getMethodDescriptor(method).equals("(F)F"))
        {
            throw new java.lang.IllegalArgumentException("Method with wrong signature in argument!");
        }

        cw.visit(V1_6, ACC_PUBLIC + ACC_FINAL + ACC_SUPER,
                className.replace(".", "/"), // "e.g. mypackage.AlgoImpl",
                null,
                "java/lang/Object",
                new String[]
                {
                    "javabenchmark/bytecode/Algorithm"
                });
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_FINAL, "transform", "(III[F[F)[F", null, null);
            // ATTRIBUTE org.netbeans.SourceLevelAnnotations
            mv.visitCode();
            mv.visitInsn(ICONST_0); // 0 to operand stack
            mv.visitVarInsn(ISTORE, 6); // i = 0;
            Label l0 = new Label();
            mv.visitLabel(l0); // l0:
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]
                    {
                        Opcodes.INTEGER
                    }, 0, null);
            mv.visitVarInsn(ILOAD, 6); // i to operand stack
            mv.visitVarInsn(ILOAD, 3); // length to operand stack
            Label l1 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l1);
            mv.visitVarInsn(ALOAD, 4); // a to operand stack
            mv.visitVarInsn(ILOAD, 1); // offa to operand stack
            mv.visitVarInsn(ILOAD, 6); // i to operand stack
            mv.visitInsn(IADD); // offa + i
            mv.visitVarInsn(ALOAD, 5); // b to operand stack
            mv.visitVarInsn(ILOAD, 2); // offb to operand stack
            mv.visitVarInsn(ILOAD, 6); // i to operand stack
            mv.visitInsn(IADD); // offb + i
            mv.visitInsn(FALOAD); // b[offb + i] to operand stack
            mv.visitMethodInsn(INVOKESTATIC,
                    org.objectweb.asm.Type.getInternalName(method.getDeclaringClass()),// "e.g. java/lang/Math",
                    method.getName(), // "e.g. abs",
                    "(F)F");
            mv.visitInsn(FASTORE); // a[offa + i] = method(b[offb + i])
            mv.visitIincInsn(6, 1); // i++
            mv.visitJumpInsn(GOTO, l0); // -> l0
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 7);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}