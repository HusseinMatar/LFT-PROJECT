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
 ldc 14
 istore 0
L1:
 ldc 15
 istore 1
L2:
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
L3:
 invokestatic Output/read()I
 istore 2
 invokestatic Output/read()I
 istore 3
 invokestatic Output/read()I
 istore 4
L4:
 iload 2
 invokestatic Output/print(I)V
 iload 3
 invokestatic Output/print(I)V
 iload 4
 invokestatic Output/print(I)V
L5:
 ldc 60
 ldc 55
 ldc 2
 ldc 5
 imul 
 iload 3
 imul 
 iload 4
 iadd 
 idiv 
 isub 
 invokestatic Output/print(I)V
L6:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

