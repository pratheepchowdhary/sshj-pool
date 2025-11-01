
# 🚀 SshJPool

**SshJPool** is a lightweight Kotlin/Java library that provides a **connection and channel pooling layer for SSHJ**, powered by **Apache Commons Pool 2**.

It allows you to efficiently reuse SSH connections and channels (SFTP, SCP, and Exec/Shell) while automatically scaling when all existing connections are full.

---

## 🌟 Features

✅ **Connection pooling** — automatically manages SSH connections using `GenericObjectPool`.
✅ **Channel pooling inside connections** — supports `SFTP`, `SCP`, and `Exec` channels per connection.
✅ **Automatic scaling** — creates new SSH connections when existing ones reach `maxChannels`.
✅ **Thread-safe** — fully synchronized and concurrent-safe for multi-threaded applications.
✅ **Graceful cleanup** — idle connections automatically returned to the pool and closed when unused.

---

## 📦 Installation

Add this dependency to your **Gradle (Kotlin DSL)** project:

```kotlin
implementation("com.connecthid.sshjpool:SshJPool:1.0.0")
```

Or for **Gradle (Groovy DSL):**

```groovy
implementation 'com.connecthid.sshjpool:SshJPool:1.0.0'
```

---

## 🧩 Package Overview

```
com.connecthid.sshjpool
 ├── SSHConnection.kt        # Represents one live SSH connection (with channels)
 ├── SSHConnectionFactory.kt # Commons-pool2 factory for SSH connections
 └── SSHConnectionPool.kt    # High-level pool manager for SSH connections & channels
```

---

## ⚙️ Usage Example

### 🔹 Basic Example

```kotlin
import com.connecthid.sshjpool.SSHConnectionPool
import net.schmizz.sshj.sftp.SFTPClient
import net.schmizz.sshj.connection.channel.direct.Session

fun main() {
    val pool = SSHConnectionPool(
        host = "192.168.1.10",
        username = "user",
        password = "password",
        maxTotalConnections = 5,
        maxChannelsPerConnection = 5
    )

    // Example 1: SFTP Operation
    pool.withSftp { sftp ->
        sftp.ls(".").forEach { println(it.name) }
    }

    // Example 2: Execute remote command
    pool.withExc { session ->
        val cmd = session.exec("uname -a")
        println(cmd.inputStream.reader().readText())
        cmd.close()
    }

    // Example 3: SCP File Transfer
    pool.withScp { scp ->
        scp.upload("local.txt", "/remote/path/")
    }

    pool.shutdown()
}
```

---

## 🔹 Manual Borrow/Return Example

```kotlin
val pool = SSHConnectionPool("192.168.1.10", "user", "password")

val (conn, sftp) = pool.borrowSftpClient()
try {
    sftp.ls(".").forEach { println(it.name) }
} finally {
    pool.returnSftpClient(conn, sftp)
}
```

---

## 🧠 Internals Overview

Each **`SSHConnection`** manages its own **active channel queue**.
When all channels are occupied (`isFull()`), the pool automatically creates a new connection.

```
SSHConnectionPool
 ├── SSHConnection 1
 │    ├── SFTPClient (x3)
 │    ├── Session (x2)
 │    └── SCPFileTransfer (x1)
 ├── SSHConnection 2
 │    ├── SFTPClient (x2)
 │    ├── Session (x3)
 │    └── SCPFileTransfer (x1)
 └── ...
```

Each connection:

* Tracks `activeChannels`
* Closes channels on release
* Returns to pool when idle

---

## 🧾 Configuration Parameters

| Parameter                  | Description                                           | Default |
| -------------------------- | ----------------------------------------------------- | ------- |
| `maxTotalConnections`      | Maximum total SSH connections in pool                 | `10`    |
| `maxChannelsPerConnection` | Max number of channels (SFTP/SCP/Exec) per connection | `10`    |
| `minIdle`                  | Minimum idle SSH connections retained                 | `1`     |
| `maxIdle`                  | Maximum idle SSH connections retained                 | `3`     |

---

## 🧰 Logging Example

To see pool activity logs:

```bash
[main] INFO com.connecthid.sshjpool.SSHConnection - Connected to 192.168.1.10
[main] INFO com.connecthid.sshjpool.SSHConnectionPool - Created new SSH connection: 12293489
[main] DEBUG com.connecthid.sshjpool.SSHConnectionPool - Returned connection to pool: 12293489
```

---

## 🧹 Graceful Shutdown

Always call `pool.shutdown()` on application exit:

```kotlin
Runtime.getRuntime().addShutdownHook(Thread {
    pool.shutdown()
})
```

This ensures all connections and channels are closed cleanly.

---

## 🧩 License

```
MIT License

Copyright (c) 2025 ConnectHID

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction...
```

---

## 💬 Support

* Website: [https://connecthid.com](https://connecthid.com)
* Author: **Pratheep Kanati**
* Email: [support@connecthid.com](mailto:support@connecthid.com)

---


