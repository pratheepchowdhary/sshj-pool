package com.connecthid.sshjpool

import org.apache.commons.pool2.BasePooledObjectFactory
import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.impl.DefaultPooledObject

class SSHConnectionFactory(
    private val host: String,
    private val username: String,
    private val password: String,
    private val maxChannelsPerConnection: Int = 10
) : BasePooledObjectFactory<SSHConnection>() {

    override fun create(): SSHConnection {
        return SSHConnection(host, username, password, maxChannelsPerConnection)
    }

    override fun wrap(obj: SSHConnection): PooledObject<SSHConnection> {
        return DefaultPooledObject(obj)
    }

    override fun destroyObject(p: PooledObject<SSHConnection>) {
        p.`object`.close()
    }

    override fun validateObject(p: PooledObject<SSHConnection>): Boolean {
        return p.`object`.isAlive()
    }
}
