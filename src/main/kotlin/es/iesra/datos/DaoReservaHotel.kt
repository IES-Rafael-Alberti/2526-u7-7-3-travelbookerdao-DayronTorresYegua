package es.iesra.datos

import es.iesra.dominio.ReservaHotel
import java.io.File

class DaoReservaHotel : IDao<ReservaHotel> {

    private val fichero = File("reservas_hotel.csv")
    private val cabecera = "id;descripcion;ubicacion;numeroNoches"

    init {
        if (!fichero.exists()) {
            fichero.createNewFile()
            fichero.writeText("$cabecera\n")
        }
    }

    private fun lineaCsvAReservaHotel(linea: String): ReservaHotel? {
        return try {
            val partes = linea.split(";")
            if (partes.size < 4) return null
            ReservaHotel.creaInstancia(
                descripcion = partes[1],
                ubicacion = partes[2],
                numeroNoches = partes[3].toInt()
            )
        } catch (e: Exception) {
            println("Error al parsear línea CSV de hotel: ${e.message}")
            null
        }
    }

    private fun reservaHotelALinea(reserva: ReservaHotel): String =
        "${reserva.id};${reserva.descripcion};${reserva.ubicacion};${reserva.numeroNoches}"

    override fun crear(reserva: ReservaHotel): Boolean {
        if (leer().any { it.id == reserva.id }) {
            println("Ya existe una reserva de hotel con id=${reserva.id}")
            return false
        }
        return try {
            fichero.appendText("${reservaHotelALinea(reserva)}\n")
            true
        } catch (e: Exception) {
            println("Error al crear reserva de hotel en CSV: ${e.message}")
            false
        }
    }

    override fun leer(): List<ReservaHotel> {
        return try {
            fichero.readLines()
                .drop(1)
                .filter { it.isNotBlank() }
                .mapNotNull { lineaCsvAReservaHotel(it) }
        } catch (e: Exception) {
            println("Error al leer reservas de hotel desde CSV: ${e.message}")
            emptyList()
        }
    }

    override fun actualizar(reserva: ReservaHotel): Boolean {
        val reservas = leer().toMutableList()
        val indice = reservas.indexOfFirst { it.id == reserva.id }
        if (indice == -1) {
            println("No se encontró reserva de hotel con id=${reserva.id} para actualizar")
            return false
        }
        reservas[indice] = reserva
        return try {
            reescribirFichero(reservas)
            true
        } catch (e: Exception) {
            println("Error al actualizar reserva de hotel en CSV: ${e.message}")
            false
        }
    }

    override fun eliminar(reserva: ReservaHotel): Boolean {
        val reservas = leer().toMutableList()
        val eliminado = reservas.removeIf { it.id == reserva.id }
        if (!eliminado) {
            println("No se encontró reserva de hotel con id=${reserva.id} para eliminar")
            return false
        }
        return try {
            reescribirFichero(reservas)
            true
        } catch (e: Exception) {
            println("Error al eliminar reserva de hotel en CSV: ${e.message}")
            false
        }
    }

    private fun reescribirFichero(reservas: List<ReservaHotel>) {
        val contenido = buildString {
            appendLine(cabecera)
            reservas.forEach { appendLine(reservaHotelALinea(it)) }
        }
        fichero.writeText(contenido)
    }
}