package es.iesra.datos

import es.iesra.dominio.Reserva
import es.iesra.dominio.ReservaHotel
import es.iesra.dominio.ReservaVuelo

class ReservaRepository(
    private val daoVuelo: IDao<ReservaVuelo> = DaoReservaVuelo(),
    private val daoHotel: IDao<ReservaHotel> = DaoReservaHotel()
) : IReservaRepository {

    override fun agregar(reserva: Reserva): Boolean {
        return when (reserva) {
            is ReservaVuelo -> daoVuelo.crear(reserva)
            is ReservaHotel -> daoHotel.crear(reserva)
            else -> {
                println("Tipo de reserva desconocido: ${reserva::class.simpleName}")
                false
            }
        }
    }

    override fun obtenerTodas(): List<Reserva> {
        val vuelos: List<Reserva> = daoVuelo.leer()
        val hoteles: List<Reserva> = daoHotel.leer()
        return vuelos + hoteles
    }
}