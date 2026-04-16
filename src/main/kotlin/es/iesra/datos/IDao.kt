package es.iesra.datos

interface IDao<T> {

    fun crear(reserva: T) : Boolean

    fun leer() : List<T>

    fun actualizar(reserva: T) : Boolean

    fun eliminar(reserva: T) : Boolean

}