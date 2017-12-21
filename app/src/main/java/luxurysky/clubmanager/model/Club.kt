package luxurysky.clubmanager.model

import io.realm.RealmObject

/**
 * Created by HWANGJIN on 2017-12-20.
 */
open class Club : RealmObject(){
    var name : String = ""
    var emblemUrl : String = ""
}