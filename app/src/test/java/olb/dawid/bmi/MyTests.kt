package olb.dawid.bmi

import olb.dawid.bmi.logic.BmiForKgCm
import org.junit.Assert
import org.junit.Test

class MyTests {

    @Test
    fun for_valid_data_should_return_bmi() {
        val bmi = BmiForKgCm(65,170)
        Assert.assertEquals(22.491, bmi.countBmi(),0.001)
    }

    @Test
    fun for_me_data_should_return_bmi() {
        val bmi = BmiForKgCm(65,180)
        Assert.assertEquals(20.06, bmi.countBmi(),0.01)
    }

    @Test
    fun for_invalid_data_should_throw_exeption(){
    }



}