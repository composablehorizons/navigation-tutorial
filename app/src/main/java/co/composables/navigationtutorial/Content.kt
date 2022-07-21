package co.composables.navigationtutorial

import androidx.annotation.DrawableRes

val doctor = Content(id = 0,
    title = "Deciding When to See a Doctor",
    thumbnail = R.drawable.doctor,
    body = "Should I go to the doctor? Most of us have asked that question at one time or another. Whether it’s a bad cold, a funny-looking mole, or a nagging pain that just won’t go away, it can be hard to know when you should see your doctor. There are no set rules that tell you when to go or when to wait. But some general guidelines might help you the next time you’re trying to decide.")

val group = Content(id = 1,
    title = "How to Choose the Best Group Exercise",
    thumbnail = R.drawable.group,
    body = "As life ferries us along and we settle into our routines, many of us realize that exercise has lost its place in our lives somewhere along the way. Whether you lost interest in fitness after graduating from school, getting a job or starting a family, it is a common situation.")

val wearables = Content(id = 2,
    title = "What is a fitness tracker?",
    thumbnail = R.drawable.wearables,
    body = "A fitness tracker is a device (typically a wristband) that monitors statistics such as your heart rate, the number of steps you take each day, and how long you spend working out. Most fitness trackers have several dedicated modes for recording different forms of exercise, and some also have GPS for tracking your location during runs, walks and bike rides. Most fitness trackers also monitor how long you spend sleeping, and in each sleep stage.")

val workout = Content(id = 3,
    title = "30 Moves to Make the Most of Your At-Home Workout",
    thumbnail = R.drawable.workout,
    body = "If the idea of an at-home workout makes you yawn, think again! When executed correctly, using just your body weight can give you a run for your money. So, whether the gym isn’t your thing or you’re short on time, clear out a space in the living room and prepare to sweat.")

data class Content(
    val id: Long,
    val title: String,
    @DrawableRes val thumbnail: Int,
    val body: String,
)

val allArticles = listOf(doctor, group, wearables, workout)