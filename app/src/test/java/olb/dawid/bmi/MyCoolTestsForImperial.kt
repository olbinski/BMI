package olb.dawid.bmi

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import olb.dawid.bmi.logic.BmiForKgCm
import olb.dawid.bmi.logic.BmiForLbIn
import org.junit.Assert

class MyCoolTestsForImperial : StringSpec() {

    init{

        "for valid data " {
            val bmi = BmiForLbIn(222,66)
            bmi.countBmi() shouldBeAround 35.827
        }
        "for valid data2 " {
            val bmi = BmiForLbIn(44,20)
            bmi.countBmi() shouldBeAround 77.330
        }



        "when data are invalid"{
            val bmi = BmiForLbIn(-1,0)
            shouldThrow<IllegalArgumentException> {bmi.countBmi() }
        }


        "for invalid mass and valid data"{
            val bmi = BmiForLbIn(-1, 60)
            shouldThrow<java.lang.IllegalArgumentException> {bmi.countBmi()  }
        }

        "for valid mass and invalid data"{
            val bmi = BmiForLbIn(60, -8)
            shouldThrow<java.lang.IllegalArgumentException> {bmi.countBmi()  }
        }

    }

    infix fun Double.shouldBeAround(value: Double){
        Assert.assertEquals(value, this, 0.001)
    }

}