.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 invokestatic Output/read()I
 istore 0
 iload 0
 invokestatic Output/read()I
 istore 1
 iload 1
 invokestatic Output/read()I
 istore 2
L1:
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 ldc 3
 ldc 2
 iadd 
 invokestatic Output/print(I)V
L2:
 iload 0
 istore 3
L3:
 iload 0
 ldc 4
 if_icmpgt L6
 goto L5
L6:
 iload 0
 istore 1
 iload 1
 istore 3
L7:
 ldc 2
 istore 0
L8:
 goto L4
L5:
 iload 1
 ldc 11
 if_icmpne L10
 goto L9
L10:
 iload 1
 invokestatic Output/print(I)V
 goto L4
L9:
 ldc 3
 iload 3
 if_icmpeq L12
 goto L11
L12:
 ldc 12
 iload 2
 if_icmpne L15
 goto L14
L15:
 invokestatic Output/read()I
 istore 2
 goto L13
L14:
 iload 1
 ldc 3
 if_icmple L17
 goto L16
L17:
 ldc 30
 ldc 3
 idiv 
 istore 0
 iload 0
 istore 1
 iload 1
 istore 2
 goto L13
L16:
 iload 3
 invokestatic Output/print(I)V
L13:
 goto L4
L11:
 ldc 999
 invokestatic Output/print(I)V
L4:
 iload 3
 invokestatic Output/print(I)V
 iload 2
 invokestatic Output/print(I)V
L18:
 ldc 10
 ldc 9
 if_icmpge L21
 goto L20
L21:
 ldc 20
 istore 0
 iload 0
 istore 1
 iload 1
 istore 2
 iload 2
 istore 3
 goto L19
L20:
L19:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

