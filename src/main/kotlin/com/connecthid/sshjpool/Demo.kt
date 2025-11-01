package com.connecthid.sshjpool

import net.schmizz.sshj.sftp.SFTPClient

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val pool = SSHConnectionPool(
        host = "152.42.223.136",
        username = "root",
        password = "aA1pradeep",
        maxTotalConnections = 5,
        maxChannelsPerConnection = 10
    )

    val clients = mutableListOf<Pair<SSHConnection, SFTPClient>>()

    repeat(25) {
        val (conn, sftp) = pool.borrowSftpClient()
        println("Using connection ${conn.hashCode()}, active channels = ${conn.getActiveChannelCount()}")
        clients.add(conn to sftp)
    }

    clients.forEach { (conn, sftp) ->
        pool.returnSftpClient(conn, sftp)
    }

    pool.shutdown()
}