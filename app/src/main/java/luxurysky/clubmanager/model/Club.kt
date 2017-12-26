package luxurysky.clubmanager.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by HWANGJIN on 2017-12-20.
 */
open class Club : RealmObject() {

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var name: String = ""
    var emblemUrl: String = ""
    var playersUrl: String = ""
}