package luxurysky.clubmanager.model

import android.content.res.Resources
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import luxurysky.clubmanager.R
import java.util.*

open class Club : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_MAIN_STADIUM = "main_stadium"
        const val FIELD_SUB_STADIUM = "sub_stadium"
        const val FIELD_DUES = "dues"
        const val FIELD_MATCH_TIME = "match_time"
        const val FIELD_AGE_GROUP = "age_group"

        private fun getFieldNameResource(field: String) =
                when (field) {
                    FIELD_NAME -> R.string.prompt_name
                    FIELD_MAIN_STADIUM -> R.string.prompt_main_stadium
                    FIELD_SUB_STADIUM -> R.string.prompt_sub_stadium
                    FIELD_DUES -> R.string.prompt_dues
                    FIELD_MATCH_TIME -> R.string.prompt_match_time
                    FIELD_AGE_GROUP -> R.string.prompt_age_group
                    else -> 0
                }

        fun getFieldName(res: Resources, field: String): CharSequence {
            return res.getText(getFieldNameResource(field))
        }
    }

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var name: String = ""

    var mainStadium: String = ""
    var subStadium: String = ""
    var dues: String = ""
    var matchTime: String = ""
    var ageGroup: String = ""
    var googleCalendarId : String = ""
    var players: RealmList<Player> = RealmList()

    // temporary field
    var emblemUrl: String = ""
    var playersUrl: String = ""
}