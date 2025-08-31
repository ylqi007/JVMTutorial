在 macOS 上可以用以下几种方式查看系统里安装的 JDK 版本：

---

### ✅ 1. 查看默认使用的 JDK 版本

```bash
java -version
```

👉 输出示例：

```
java version "17.0.9" 2023-10-17 LTS
Java(TM) SE Runtime Environment (build 17.0.9+9-LTS)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.9+9-LTS, mixed mode, sharing)
```

这表示系统当前默认使用的是 **JDK 17.0.9**。

---

### ✅ 2. 查看系统里安装的所有 JDK

```bash
/usr/libexec/java_home -V
```

👉 输出示例：

```
Matching Java Virtual Machines (3):
    21.0.1 (x86_64) "Oracle Corporation" - "/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home"
    17.0.9 (x86_64) "Oracle Corporation" - "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
    1.8.0_392 (x86_64) "Oracle Corporation" - "/Library/Java/JavaVirtualMachines/jdk1.8.0_392.jdk/Contents/Home"

/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
```

这里列出了系统安装的所有版本，最后一行是当前默认 JDK 路径。

---

### ✅ 3. 查看 `JAVA_HOME`

```bash
echo $JAVA_HOME
```

👉 输出示例：

```
/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
```

这说明当前环境变量里配置的 `JAVA_HOME` 指向 JDK 17。

---

### ✅ 4. 切换 JDK 版本（临时）

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
```

这样可以切换到 JDK 8，新的 shell 会生效。

---

要不要我帮你写一个 **macOS JDK 版本快速切换脚本**（比如 `j8` 切换 JDK 8，`j17` 切换 JDK 17）？这样就不用每次手动改 `JAVA_HOME`。
