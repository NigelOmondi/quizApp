

package com.example.compose.jetsurvey.quiz

import android.os.Build
import com.example.compose.jetsurvey.R

import com.example.compose.jetsurvey.quiz.PossibleAnswer.Action
import com.example.compose.jetsurvey.quiz.PossibleAnswer.MultipleChoice
import com.example.compose.jetsurvey.quiz.PossibleAnswer.SingleChoice
import com.example.compose.jetsurvey.quiz.SurveyActionType.PICK_DATE
import com.example.compose.jetsurvey.quiz.SurveyActionType.TAKE_PHOTO

// Static data of questions
private val jetpackQuestions = mutableListOf(
    Question(
        id = 1,
        questionText = R.string.geography_question ,
        answer = MultipleChoice(
            optionsStringRes = listOf(
                R.string.germany,
                R.string.qatar,
                R.string.london,
                R.string.israel,
                R.string.rwanda,
                R.string.lagos
            )
        ),
        description = R.string.select_all
    ),

    Question(
        id = 2,
        questionText = R.string.science_question,
        answer = SingleChoice(
            listOf(
                R.string.inches,
                R.string.newton,
                R.string.light_years,
                R.string.gallons
            )
        ),
        description = R.string.select_one
    ),

    Question(
        id = 3,
        questionText = R.string.history_question,
        answer = Action(label = R.string.pick_date, actionType = PICK_DATE),
        description = R.string.select_date
    ),


        Question(
        id = 4,
        questionText = R.string.computer_question,
        answer = PossibleAnswer.SingleChoiceIcon(
            optionsStringIconRes = listOf(
                Pair(R.drawable.java, R.string.java),
                Pair(R.drawable.css, R.string.css),
                Pair(R.drawable.javascript, R.string.javascript),
                Pair(R.drawable.python, R.string.python)
            )
        ),
        description = R.string.select_one
    ),



    Question(
        id = 4,
        questionText = R.string.selfies,
        answer = PossibleAnswer.Slider(
            range = 1f..10f,
            steps = 3,
            startText = R.string.strongly_dislike,
            endText = R.string.strongly_like,
            neutralText = R.string.neutral
        )
    ),
).apply {
    // TODO: FIX! After taking the selfie, the picture doesn't appear in API 22 and lower.
    if (Build.VERSION.SDK_INT >= 23) {
        add(
            Question(
                id = 975,
                questionText = R.string.selfie_skills,
                answer = Action(label = R.string.add_photo, actionType = TAKE_PHOTO),
                permissionsRequired =
                when (Build.VERSION.SDK_INT) {
                    in 23..28 -> listOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    else -> emptyList()
                },
                permissionsRationaleText = R.string.selfie_permissions
            )
        )
    }
}.toList()

private val jetpackSurvey = Survey(
    title = R.string.which_jetpack_library,
    questions = jetpackQuestions
)

object JetpackSurveyRepository : SurveyRepository {

    override fun getSurvey() = jetpackSurvey

    @Suppress("UNUSED_PARAMETER")
    override fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        return SurveyResult(
            library = "Compose",
            result = R.string.survey_result,
            description = R.string.survey_result_description
        )
    }
}

interface SurveyRepository {
    fun getSurvey(): Survey

    fun getSurveyResult(answers: List<Answer<*>>): SurveyResult
}
