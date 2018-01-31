package me.eigenein.smarthome.core

import java.net.DatagramPacket
import java.net.InetAddress

data class DeviceAddress(val address: InetAddress, val port: Int) {
    companion object {
        fun from(packet: DatagramPacket) = DeviceAddress(packet.address, packet.port)
    }
}
