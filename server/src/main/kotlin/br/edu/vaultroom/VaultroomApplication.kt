package br.edu.vaultroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VaultroomApplication

fun main(args: Array<String>) {
	runApplication<VaultroomApplication>(*args)
}
