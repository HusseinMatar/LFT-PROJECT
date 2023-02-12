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
 ldc 3
 ldc 4
 imul 
 ldc 1
 ldc 2
 imul 
 ldc 7
 ldc 6
 isub 
 iadd 
 imul 
 invokestatic Output/print(I)V
 ldc 1
 invokestatic Output/print(I)V
L1:
 invokestatic Output/read()I
 istore 0
 invokestatic Output/read()I
 istore 1
 invokestatic Output/read()I
 istore 2
L2:
 ldc 10
 istore 3
L3:
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 iload 2
 invokestatic Output/print(I)V
L4:
 iload 0
 iload 1
 if_icmpgt L7
 goto L6
L7:
 iload 0
 istore 3
 goto L5
L6:
 iload 1
 iload 3
 if_icmpgt L9
 goto L8
L9:
 iload 1
 istore 3
 goto L5
L8:
 iload 2
 iload 3
 if_icmpgt L11
 goto L10
L11:
 iload 2
 istore 3
L12:
 ldc 30
 iload 2
 if_icmpgt L15
 goto L14
L15:
 iload 2
 invokestatic Output/print(I)V
 goto L13
L14:
 iload 2
 ldc 30
 if_icmplt L17
 goto L16
L17:
 ldc 3
 ldc 3
 idiv 
 istore 3
L18:
 ldc 9
 invokestatic Output/print(I)V
L19:
 goto L13
L16:
 ldc 8
 invokestatic Output/print(I)V
L13:
 goto L5
L10:
 ldc 12
 istore 3
L20:
 iload 3
 invokestatic Output/print(I)V
L21:
L5:
 ldc 6
 invokestatic Output/print(I)V
L22:
 iload 3
 invokestatic Output/print(I)V
L23:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

