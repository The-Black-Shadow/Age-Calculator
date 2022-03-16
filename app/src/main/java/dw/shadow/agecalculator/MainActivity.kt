/*All Rights reserved by
        Developer World
       & Md Mehedi Hasan
*/
package dw.shadow.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {
    private val todayDate: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Current Date
        val currentDateTextView = findViewById<TextView>(R.id.currentDatetextView)
        val selectDateButton = findViewById<ImageButton>(R.id.selectDateButton)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateTime = simpleDateFormat.format(todayDate.time).toString()
        //Set current Date to textView
        currentDateTextView.text = dateTime

        //Select Date of birth
        selectDateButton.setOnClickListener { view ->
            clickDatePicker(view)
            //Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show()
        }
    }

    private fun clickDatePicker(view: View) {
        val dateOfBirthTextView = findViewById<TextView>(R.id.dateOfBirthTextView)
        val dayTextView = findViewById<TextView>(R.id.dayTextView)
        val minuteTextView = findViewById<TextView>(R.id.minuteTextView)
        val hourTextView = findViewById<TextView>(R.id.hourTextView)
        val weekTextView = findViewById<TextView>(R.id.weekTextView)
        val resultAgeTextView = findViewById<TextView>(R.id.resultAgeTextView)

        val day = todayDate.get(Calendar.DAY_OF_MONTH)
        val month = todayDate.get(Calendar.MONTH)
        val year = todayDate.get(Calendar.YEAR)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedMonth = selectedMonth + 1

                //Select Date fo Birth
                val dateString =
                    String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth, selectedYear)
                dateOfBirthTextView.text = dateString
                val selectedDate = "$selectedDayOfMonth $selectedMonth $selectedYear"
                val dateInPattern = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)


                //Total days in minute and hour
                val theDate = dateInPattern.parse(selectedDate)
                val selectedDateInMinutes = theDate!!.time / 60000
                val currentDate =
                    dateInPattern.parse(dateInPattern.format(System.currentTimeMillis()))
                val currentDateInMinutes = currentDate!!.time / 60000
                val differenceDateInMinutes = currentDateInMinutes - selectedDateInMinutes
                val totalHour = differenceDateInMinutes / 60
                minuteTextView.text = differenceDateInMinutes.toString()
                hourTextView.text = totalHour.toString()

                //Total Days and weeks
                val toDaysTime = todayDate.time
                val date : LocalDate = toDaysTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val birthDate : LocalDate = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val totalDays = ChronoUnit.DAYS.between(birthDate,date)
                val totalWeeks = ChronoUnit.WEEKS.between(birthDate,date)
                dayTextView.text = totalDays.toString()
                weekTextView.text = totalWeeks.toString()

                //Total Age
                val period : Period = Period.between(birthDate,date)
                val totalYear = period.years
                val totalMonth = period.months
                val totalDay = period.days
                val resultAge = "$totalYear Years $totalMonth Months $totalDay Days"
                resultAgeTextView.text = resultAge

            }, year, month, day
        )
        dpd.datePicker.maxDate = Date().time
        dpd.show()

    }
}