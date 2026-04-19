package es.iesra.datos

import es.iesra.dominio.ReservaVuelo
import java.io.File

class DaoReservaVuelo : IDao<ReservaVuelo> {

    private val fichero = File("reservas_vuelo.csv")
    private val cabecera = "id;descripcion;origen;destino;horaVuelo"

    init {
        if (!fichero.exists()) {
            fichero.createNewFile()
            fichero.writeText("$cabecera\n")
        }
    }

    private fun lineaCsvAReservaVuelo(linea: String): ReservaVuelo? {
        return try {
            val partes = linea.split(";")
            if (partes.size < 5) return null
            ReservaVuelo.creaInstancia(
                descripcion = partes[1],
                origen = partes[2],
                destino = partes[3],
                horaVuelo = partes[4]
            )
        } catch (e: Exception) {
            println("Error al parsear línea CSV de vuelo: ${e.message}")
            null
        }
    }

    private fun reservaVueloALinea(reserva: ReservaVuelo): String =
        "${reserva.id};${reserva.descripcion};${reserva.origen};${reserva.destino};${reserva.horaVuelo}"

    override fun crear(reserva: ReservaVuelo): Boolean {
        if (leer().any { it.id == reserva.id }) {
            println("Ya existe una reserva de vuelo con id=${reserva.id}")
            return false
        }
        return try {
            fichero.appendText("${reservaVueloALinea(reserva)}\n")
            true
        } catch (e: Exception) {
            println("Error al crear reserva de vuelo en CSV: ${e.message}")
            false
        }
    }

    override fun leer(): List<ReservaVuelo> {
        return try {
            fichero.readLines()
                .drop(1)                       // Saltamos la cabecera
                .filter { it.isNotBlank() }     // Ignoramos líneas vacías
                .mapNotNull { lineaCsvAReservaVuelo(it) }
        } catch (e: Exception) {
            println("Error al leer reservas de vuelo desde CSV: ${e.message}")
            emptyList()
        }
    }

    override fun actualizar(reserva: ReservaVuelo): Boolean {
        val reservas = leer().toMutableList()
        val indice = reservas.indexOfFirst { it.id == reserva.id }
        if (indice == -1) {
            println("No se encontró reserva de vuelo con id=${reserva.id} para actualizar")
            return false
        }
        reservas[indice] = reserva
        return try {
            reescribirFichero(reservas)
            true
        } catch (e: Exception) {
            println("Error al actualizar reserva de vuelo en CSV: ${e.message}")
            false
        }
    }

    override fun eliminar(reserva: ReservaVuelo): Boolean {
        val reservas = leer().toMutableList()
        val eliminado = reservas.removeIf { it.id == reserva.id }
        if (!eliminado) {
            println("No se encontró reserva de vuelo con id=${reserva.id} para eliminar")
            return false
        }
        return try {
            reescribirFichero(reservas)
            true
        } catch (e: Exception) {
            println("Error al eliminar reserva de vuelo en CSV: ${e.message}")
            false
        }
    }

    private fun reescribirFichero(reservas: List<ReservaVuelo>) {
        val contenido = buildString {
            appendLine(cabecera)
            reservas.forEach { appendLine(reservaVueloALinea(it)) }
        }
        fichero.writeText(contenido)
    }
}