package luxurysky.clubmanager.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by HWANGJIN on 2017-12-20.
 */
open class Club : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
    }

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var name: String = ""

    var emblemUrl: String = ""
    var playersUrl: String = ""

    var players: RealmList<Player> = RealmList()
}