package es.iesra.datos

import es.iesra.dominio.Reserva
import java.io.File

class DaoFicheros : IDao<Reserva> {


    val fichero = File("reservas.txt").readLines()

    override fun crear(reserva: Reserva): Boolean {
        TODO("Not yet implemented")
    }

    override fun leer(): List<Reserva> {
        TODO("Not yet implemented")
    }

    override fun actualizar(reserva: Reserva) : Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminar(reserva: Reserva): Boolean {
        TODO("Not yet implemented")
    }
}