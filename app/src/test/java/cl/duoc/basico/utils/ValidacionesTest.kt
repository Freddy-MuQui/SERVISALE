package cl.duoc.basico.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionesTest {

    @Test
    fun validarFormatoEmail_emailCorrecto_retornaTrue() {
        assertTrue(validarFormatoEmail("freddy@gmail.com"))
    }

    @Test
    fun validarFormatoEmail_emailIncorrecto_retornaFalse() {
        assertFalse(validarFormatoEmail("freddy@gmail"))
    }

    @Test
    fun validarPassword_menosDe6_retornaFalse() {
        assertFalse(validarPassword("12345"))
    }

    @Test
    fun validarPassword_6OMas_retornaTrue() {
        assertTrue(validarPassword("123456"))
    }

    @Test
    fun noEsVacio_conEspacios_retornaFalse() {
        assertFalse(noEsVacio("   "))
    }

    @Test
    fun validarNombre_menosDe3_retornaFalse() {
        assertFalse(validarNombre("Al"))
    }
}
