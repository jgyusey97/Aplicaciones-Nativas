package com.bg.bancoguayaquilcertificates.messages

object AppMessages {
    object General {
        val messages = mapOf(
            "ErrorGeneral" to "Ocurrió un error de conexión, por favor inténtalo más tarde."
        )
    }

    object RefBancariaCatalogoProductos {
        val messages = mapOf(
            "NO_ID_PRODUCTO" to "No hay un código para la Referencia Bancaria.",
            "SIN_PRODUCTOS" to "Al momento usted no tiene opciones disponibles para esta categoría.",
            "5023" to "Usted no posee el perfil para generar esta solicitud.",
            "ERROR" to "No posee productos del tipo seleccionado.",
        )
    }

    object RefBancariaSolicitar {
        val messages = mapOf(
            "NO_INFO" to "No se pudo procesar debido a falta de información",
            "0710" to "Los datos enviados son incorrectos por favor comunicarse al 3730100 opc. 9",
            "ERROR" to "La solicitud no pudo ser procesada, por favor intente en unos minutos.",
        )
    }
}
