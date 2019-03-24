package olb.dawid.bmi.logic

class BmiForKgCm(var mass: Int, var height: Int) : Bmi {

    private val massRange = 20..200
    private val heightRange = 50..250

    override fun countBmi():Double{
        require(mass in massRange ) {"invalid mass (should be:20..200)"}
        require(height in heightRange) {"invalid height (should be:50..250)"}

        val bmi = mass * 10000.0/ (height*height)
        return bmi
    }
}
