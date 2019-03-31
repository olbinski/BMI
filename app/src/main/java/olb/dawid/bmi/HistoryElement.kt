package olb.dawid.bmi

import java.util.*

class HistoryElement(val bmiCount: Double, val mass: Int, val height: Int, val text:String, val color: Int) {
    val date = Calendar.getInstance().time
}
//
//W każdym wpisie w historii powinny być:
//- Policzone BMI w kolorze
//- Masa
//- Wzrost
//- Data pomiaru