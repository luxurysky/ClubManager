package luxurysky.clubmanager.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by HWANGJIN on 2017-12-21.
 */
open class Player : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
    }

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var name: String = ""
    var position: String = ""
    var squadNumber: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var photoUrl: String = ""

    @LinkingObjects("players")
    val clubs: RealmResults<Club>? = null

    enum class Position {
        Forward, Midfielder, Defender, Goalkeeper
    }
}