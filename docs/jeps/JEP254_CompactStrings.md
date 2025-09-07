# [JEP 254: Compact Strings](https://openjdk.org/jeps/254)

好的 👍 你给的链接是 **[JEP 254: Compact Strings](https://openjdk.org/jeps/254)**，这是 JDK 9 引入的重要优化。下面我帮你详细解析。

---

## 📌 JEP 254: Compact Strings

### 1. 背景

在 JDK 8 以及更早版本中：

* `java.lang.String` 内部使用 **`char[]`** 存储数据。
* 每个 `char` 占 **2 个字节（UTF-16）**。
* 即使字符串只包含 ASCII 字符（1 个字节就够），也依然会用 2 个字节存储 → 浪费内存。

👉 举例：

```java
String s = "Hello";
```

* JDK 8 里：存储是一个 `char[]`，每个元素 2 个字节，总共 10 字节（含 header 等对象开销更多）。
* 实际只需要 5 字节。

---

### 2. Compact Strings 的改进

在 JDK 9 之后：

* `String` 内部不再用 `char[]`，而是用 **`byte[]`** + 一个 **`coder` 标志位**。
* `coder` 表示使用哪种编码：

    * `LATIN1`（单字节编码，适合 ISO-8859-1/ASCII）
    * `UTF16`（双字节编码，兼容所有字符）

👉 内部结构大致如下：

```java
public final class String implements java.io.Serializable {
    private final byte[] value;
    private final byte coder; // LATIN1 (0) or UTF16 (1)
}
```

这样：

* 只包含 Latin-1 的字符串会用 1 个字节存储。
* 包含其他 Unicode 字符的字符串才会用 UTF-16 存储。

---

### 3. 带来的好处

* **内存占用显著减少**
  ASCII-heavy 应用（如英文日志、JSON、配置）内存占用减少约 25%\~50%。
* **CPU 缓存利用率提高**
  更小的对象能让 CPU 缓存效率更高。
* **性能提升**
  部分字符串操作（如 `equals`, `hashCode`）在 Latin-1 下更快。

---

### 4. 向后兼容

* 对开发者来说，`String` API 完全没变。
* 内部实现从 `char[]` 换成 `byte[]` + `coder`。
* **大部分程序无需修改**，只获得性能/内存收益。

---

### 5. 实际效果（官方数据）

JEP 提供的测试结果：

* 内存使用：减少 ~~10~~15%。
* CPU 性能：轻微提升（尤其是处理 ASCII-heavy workload）。

---

## 📌 总结

* **之前 (JDK ≤ 8)**: `String` = `char[]` (2 bytes per char)。
* **之后 (JDK ≥ 9)**: `String` = `byte[]` + `coder` (1 byte if possible)。
* **意义**: 内存占用更少、性能更优，且对开发者透明。

---

👉 要不要我帮你用一个 **简单的 JMH 基准测试**，比较 JDK 8 vs JDK 17 的 `String` 内存占用和性能差异？这样你能直观看到 Compact Strings 的实际收益。
